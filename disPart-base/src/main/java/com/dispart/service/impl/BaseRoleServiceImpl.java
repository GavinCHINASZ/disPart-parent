package com.dispart.service.impl;

import com.dispart.dao.mapper.BaseOrganizationMapper;
import com.dispart.dto.roledto.DISP20210033FindRoleInDto;
import com.dispart.dto.roledto.DISP20210033FindRoleOutDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.mapper.BaseRoleMapper;
import com.dispart.vo.basevo.RoleVo;
import com.dispart.service.BaseRoleService;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, RoleVo> implements BaseRoleService {
    @Autowired
    BaseRoleMapper baseRoleMapper;

    @Autowired
    BaseOrganizationMapper baseOrganizationMapper;

    /**
     * 查询角色列表
     * @param disp20210033FindRoleInDto
     * @return
     */
    @Override
    public Result<DISP20210033FindRoleOutDto> findRoleByParam(DISP20210033FindRoleInDto disp20210033FindRoleInDto) {
        DISP20210033FindRoleOutDto d33Out = new DISP20210033FindRoleOutDto();

        if(!(disp20210033FindRoleInDto.getPageNum()==null || disp20210033FindRoleInDto.getPageSize()==null) ){//分页判断
            try {
                Integer roleNum=baseRoleMapper.roleCount(disp20210033FindRoleInDto);
                d33Out.setTolPageNum(roleNum);
                int pageNum=(disp20210033FindRoleInDto.getPageNum()-1)*disp20210033FindRoleInDto.getPageSize();
                disp20210033FindRoleInDto.setPageNum(pageNum);
                d33Out.setRoleArray(baseRoleMapper.findRoleByParam(disp20210033FindRoleInDto));
                return Result.build(d33Out, ResultCodeBaseEnum.SUCCESS);
            }catch (Exception e){
                log.info("查询角色列表-获取角色信息失败： "+e);
                throw new RuntimeException("系统错误，数据库查询异常");
            }
        }
        disp20210033FindRoleInDto.setPageSize(0);
        d33Out.setRoleArray(baseRoleMapper.findRoleByParam(disp20210033FindRoleInDto));
        return Result.build(d33Out, ResultCodeBaseEnum.SUCCESS);
    }


    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    @Override
    public Result inRole(RoleVo roleVo) {
        if(roleVo==null || roleVo.getOrgId()==null || "".equals(roleVo.getOrgId())){
            return Result.build(1,"角色所属机构号为空");
        }
        Date newTime=new Date();
        roleVo.setUpdateDt(newTime);
        int roleId=100000;
        int addNum = 0;
        try {
            roleId+=baseOrganizationMapper.findNextval("roleId");//取角色序列号
            roleVo.setRoleId(roleId+"");
            addNum = baseRoleMapper.inRole(roleVo);
        }catch (Exception e){
            log.info("添加角色-添加角色失败： "+e);
            throw new RuntimeException("系统错误，数据库插入异常");
        }
        if(addNum>0){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除角色
     * @param roleVo
     * @return
     */
    @Override
    public Result deRole(RoleVo roleVo) {
        if(roleVo==null || roleVo.getRoleId()==null || "".equals(roleVo.getRoleId())){
            return Result.build(1,"角色编号为空");
        }

        //删除角色前检查当前角色存在绑定的用户，全部解绑后才能删除角色
        List<String> empIds = new ArrayList<>();
        try {
            empIds = baseRoleMapper.findEmpRoleByRoleId(roleVo.getRoleId());
        }catch (Exception e){
            log.info("删除角色-获取角色绑定用户失败： "+e);
            throw new RuntimeException("删除角色，数据库查询异常");
        }
        if(empIds.size()>0){
            return Result.build(null,ResultCodeBaseEnum.ROLE_HAVE_EMP);//存在关联用户
        }

        int deNum = 0;
        try {
            deNum = baseRoleMapper.delRoleById(roleVo.getRoleId());
        }catch (Exception e){
            log.info("删除角色-删除失败： "+e);
            throw new RuntimeException("删除角色，数据库删除异常");
        }
        if(deNum>0){
            return Result.ok();
        }
        return Result.fail();
    }


}
