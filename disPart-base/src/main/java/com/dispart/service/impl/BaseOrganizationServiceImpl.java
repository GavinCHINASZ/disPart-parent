package com.dispart.service.impl;

import com.dispart.dao.mapper.BaseDepartmentMapper;
import com.dispart.dao.mapper.BaseRoleMapper;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamInDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamOutDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamPOutDto;
import com.dispart.dto.orgdto.DISP20210026UpOrgInDto;
import com.dispart.dto.roledto.DISP20210033FindRoleOutDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import com.dispart.vo.basevo.DepartmentVo;
import com.dispart.vo.basevo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.mapper.BaseOrganizationMapper;
import com.dispart.vo.basevo.OrganizationVo;
import com.dispart.service.BaseOrganizationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BaseOrganizationServiceImpl extends ServiceImpl<BaseOrganizationMapper, DISP20210026UpOrgInDto> implements BaseOrganizationService {
    @Autowired
    BaseOrganizationMapper baseOrganizationMapper;

    @Autowired
    BaseDepartmentMapper baseDepartmentMapper;

    @Autowired
    BaseRoleMapper baseRoleMapper;

    /**
     * 添加机构
     * @param organizationVo
     * @return
     */
    @Override
    public Result addOrg(OrganizationVo organizationVo) {
        //机构号赋值:主机构--地区行政编码+两位顺号，分支机构--上级机构号+两位顺号
        String orgNum="";
        if(!(organizationVo.getParentOrgId()==null || "".equals(organizationVo.getParentOrgId()) || "00000000".equals(organizationVo.getParentOrgId()))){
            orgNum=organizationVo.getParentOrgId();//分支机构:取上级机构号
            log.info("添加机构-上级机构号：   "+orgNum);
        }else {
            log.info("添加机构-上级机构编号为空");
            return Result.build(null,1,"上级机构编号为空,若无主体机构请在数据库添加主体机构");
        }

        //查询最大机构
        try {
            String getSqlMaxNum=baseOrganizationMapper.seMaxOrgId(orgNum+"00",orgNum+"99");
            log.info("最大机构号：   "+getSqlMaxNum);
            if(getSqlMaxNum==null){//无最大机构号
                orgNum=orgNum+"01";
            }else {//有最大机构号则+1
                orgNum=(Long.parseLong(getSqlMaxNum)+1)+"";
            }
            organizationVo.setOrgType("2");
            organizationVo.setOrgId(orgNum);
            organizationVo.setOrgSt("0");
            organizationVo.setCreatDt(new Date());
        }catch (Exception e){
            log.info("添加机构-查询最大机构号失败： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        log.info("添加机构-查询最大机构号失败1111： ");
        Integer addNum = baseOrganizationMapper.addOrg(organizationVo);
        if(addNum>0){
            return  Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除机构
     * @param organizationVo
     * @return
     */
    @Override
    public Result upOrgSt(OrganizationVo organizationVo) {
        //删除机构时要检查 下级机构、下级部门和角色是否存在，有一个存在都不能删除

        OrganizationVo findNextOrg = new OrganizationVo();
        findNextOrg.setParentOrgId(organizationVo.getOrgId());
        List<OrganizationVo> orgBranList = new ArrayList<>();
        try {
            orgBranList = baseOrganizationMapper.findNextOrgId(findNextOrg);
        }catch (Exception e){
            log.info("删除机构-查询子机构信息失败： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        if(orgBranList.size()>0){
            return Result.build(null,ResultCodeBaseEnum.ORG_HAVE_NEXTORG);//存在子机构
        }

        DepartmentVo findDep = new DepartmentVo();
        findDep.setSubOrg(organizationVo.getOrgId());
        List<DepartmentVo> dep = new ArrayList<>();
        try {
            dep = baseDepartmentMapper.findDepData(findDep);
        }catch (Exception e){
            log.info("删除机构-查询部门信息失败： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        if(dep.size()>0){
            return Result.build(null,ResultCodeBaseEnum.ORG_HAVE_NEXTDEP);//存在部门
        }

        RoleVo findRole = new RoleVo();
        findRole.setOrgId(organizationVo.getOrgId());
        List<RoleVo> role = new ArrayList<>();
        try {
            role = baseRoleMapper.findRoleData(findRole);
        }catch (Exception e){
            log.info("删除机构-查询角色信息失败： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        if(role.size()>0){
            return Result.build(null,ResultCodeBaseEnum.ORG_HAVE_Role);//存在角色
        }

        log.info("删除机构-机构号: " + organizationVo.getOrgId());
        int ups = baseOrganizationMapper.upOrgSt(organizationVo);
        log.info("删除机构-删除结果： "+ups);
        if(ups>0){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 获取机构信息
     * @param disp20210025OrgFindByParamInDto
     * @return
     */
    @Override
    public Result<DISP20210025OrgFindByParamOutDto> seOrgList(DISP20210025OrgFindByParamInDto disp20210025OrgFindByParamInDto) {
        DISP20210025OrgFindByParamOutDto reOrgList = new DISP20210025OrgFindByParamOutDto();//返回参数
        if("0".equals(disp20210025OrgFindByParamInDto.getGetTree())){//需要机构树操作
            disp20210025OrgFindByParamInDto.setPageSize(0);//获取机构数时把分页参数置0，在限制条件下查全部机构
            String parentOrgId = disp20210025OrgFindByParamInDto.getParentOrgId();
            disp20210025OrgFindByParamInDto.setParentOrgId("");
            log.info("查询机构信息-参数："+disp20210025OrgFindByParamInDto);
            List<DISP20210025OrgFindByParamPOutDto> orgArray = new ArrayList<>();
            try{
                orgArray = baseOrganizationMapper.seOrgList(disp20210025OrgFindByParamInDto);
            }catch (Exception e){
                log.info("查询机构信息-获取机构列表失败： "+e);
                throw new RuntimeException("系统错误，数据库查询异常");
            }
            reOrgList.setOrgArrayList(getOrgBranchTree(orgArray,parentOrgId));
            return Result.build(reOrgList, ResultCodeBaseEnum.SUCCESS);
        }
        if(!(disp20210025OrgFindByParamInDto.getPageNum()==null || disp20210025OrgFindByParamInDto.getPageSize()==null) ){//分页判断
            try {
                Integer roleNum=baseOrganizationMapper.orgNum(disp20210025OrgFindByParamInDto);//获取总数
                reOrgList.setTolPageNum(roleNum);
                int pageNum=(disp20210025OrgFindByParamInDto.getPageNum()-1)*disp20210025OrgFindByParamInDto.getPageSize();//设置起始条数
                disp20210025OrgFindByParamInDto.setPageNum(pageNum);
                reOrgList.setOrgArrayList(baseOrganizationMapper.seOrgList(disp20210025OrgFindByParamInDto));
                return Result.build(reOrgList, ResultCodeBaseEnum.SUCCESS);
            }catch (Exception e){
                log.info("查询机构信息-获取机构信息失败： "+e);
                throw new RuntimeException("系统错误，数据库查询异常");
            }
        }else {
            reOrgList.setOrgArrayList(baseOrganizationMapper.seOrgList(disp20210025OrgFindByParamInDto));
        }

        log.info("查询机构信息-返回结果： "+reOrgList);
        return Result.build(reOrgList, ResultCodeBaseEnum.SUCCESS);
    }


    /**
     * 获取机构最上级机构
     * @param seList 机构列表
     * @param maxOrg 最上级机构
     * @return
     */
    public List<DISP20210025OrgFindByParamPOutDto> getOrgBranchTree(List<DISP20210025OrgFindByParamPOutDto> seList,String maxOrg){
        //最上级机构若为空，默认取上级机构为00000000的机构为最上级机构
        List<DISP20210025OrgFindByParamPOutDto> orgBranchList = new ArrayList<>();//要返回的参数
        if(maxOrg==null || "".equals(maxOrg)){
            for(DISP20210025OrgFindByParamPOutDto se : seList){
                if("00000000".equals(se.getParentOrgId())){
                    orgBranchList.add(se); //添加最上级机构
                }
            }
        }else {
            for(DISP20210025OrgFindByParamPOutDto se : seList){
                if(maxOrg.equals(se.getOrgId())){
                    orgBranchList.add(se); //添加最上级机构
                    break;
                }
            }
        }

        for(DISP20210025OrgFindByParamPOutDto jdOrg : orgBranchList){
            jdOrg=getAllOrgBranchList(seList,jdOrg);

        }
        return orgBranchList;
    }

    /**
     * 根据最上级机构获取机构树
     * @param seList 机构列表
     * @param jdOrg 上级机构节点
     * @return
     */
    public DISP20210025OrgFindByParamPOutDto getAllOrgBranchList(List<DISP20210025OrgFindByParamPOutDto> seList,DISP20210025OrgFindByParamPOutDto jdOrg){
        for(DISP20210025OrgFindByParamPOutDto se : seList){
            if(Objects.equals(jdOrg.getOrgId(),se.getParentOrgId())){
                jdOrg.getOrgBranchList().add(se);
                se=getAllOrgBranchList(seList,se);
            }
        }
        return jdOrg;
    }


}
