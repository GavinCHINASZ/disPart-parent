package com.dispart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.mapper.BaseDepartmentMapper;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamOutDto;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamPOutDto;
import com.dispart.dto.departmentdto.DISP20210020UpDepInDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import com.dispart.service.BaseDepartmentService;
import com.dispart.vo.basevo.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BaseDepartmentServiceImpl extends ServiceImpl<BaseDepartmentMapper, DepartmentVo> implements BaseDepartmentService {

    @Autowired
    private BaseDepartmentMapper baseDepartmentMapper;

    @Override
    public Result insert(DepartmentVo record) {
        String depNum = "";
        //部门编号：上级机构号+3位顺数；子部门：上级部门编号+3位顺数
        if(!(record.getParentDepId()==null || "".equals(record.getParentDepId()))){//判断是部门还是子部门
            //子部门
            depNum=record.getParentDepId();//取上级部门编号
            log.info("添加部门-上级部门编号：   "+depNum);
        }else if(!(record.getSubOrg()==null || "".equals(record.getSubOrg()))){//判断是否上送上级机构号
            depNum=record.getSubOrg();//取所属机构号
            record.setParentDepId("000000");//上级部门的上级部门编号默认000000
            log.info("添加部门-所属机构号：   "+depNum);
        }
        if(record.getSubOrg()==null || "".equals(record.getSubOrg())){
            log.info("添加部门-未上送所属机构号");
            return Result.build(null,1,"新增上级部门时所属机构号为空");
        }

        try{
            String seMaxDepID = baseDepartmentMapper.seMaxDepID(depNum+"000",depNum+"999");
            if(seMaxDepID == null || "".equals(seMaxDepID)){
                depNum=depNum+"001";//无最大编号，取001
                record.setDepId(depNum);
            }else {
                depNum= (Long.parseLong(seMaxDepID)+1)+"";//最大编号+1
            }
        }catch (Exception e){
            log.info("添加部门-生成部门编号失败： "+e);
            throw new RuntimeException("系统错误，查询最大部门编号异常");
        }

        record.setDepId(depNum);
        record.setCreatDt(new Date());
        record.setDepSt("0");
        int inserNum = 0;
        try {
            inserNum = baseDepartmentMapper.insert(record);
        }catch (Exception e){
            log.info("添加部门-插入部门信息失败： "+e);
            throw new RuntimeException("系统错误，插入部门信息异常");
        }
        if(inserNum>0){
            log.info("添加部门-成功");
            return Result.ok();
        }
        log.info("添加部门-SQL插入失败");
        return Result.fail();
    }

    /**
     * 删除部门
     * @param record
     * @return
     */
    @Override
    public Result upDepSt(DepartmentVo record) {


        if(record.getDepId()==null || "".equals(record.getDepId())){
            Result.build(null,1,"部门ID为空");
        }
        //存在子部门的时候不允许删除当前部门,存在正常员工时也不允许删除当前部门
        DepartmentVo findDep = new DepartmentVo();
        findDep.setParentDepId(record.getDepId());
        List<DepartmentVo> depBran = new ArrayList<>();
        try {
            depBran = baseDepartmentMapper.findDepData(findDep);
        }catch (Exception e){
            log.info("删除部门-查询子部门信息异常： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        if(depBran.size()>0){
            return Result.build(null,ResultCodeBaseEnum.DEP_HAVE_NEXTDEP);//存在子部门，不允许删除
        }

        List<String> empIds = new ArrayList<>();
        try{
            empIds = baseDepartmentMapper.findEmpByDepId(record.getDepId());
        }catch (Exception e){
            log.info("删除部门-查询员工信息异常： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        if(empIds.size()>0){
            return Result.build(null,ResultCodeBaseEnum.DEP_HAVE_EMP);//部门名下存在正常员工，不允许删除
        }

        int upNum = 0;
        try{
            upNum = baseDepartmentMapper.upDepSt(record);
        }catch (Exception e){
            log.info("删除部门-删除部门信息异常： "+e);
            throw new RuntimeException("系统错误，数据库删除异常");
        }
        if(upNum>0){
            return Result.ok();
        }
       return Result.fail();
    }

    /**
     * 根据条件查询部门信息
     * @param record
     * @return
     */
    @Override
    public Result<DISP20210019DepFindByParamOutDto> findDepByParam(DISP20210019DepFindByParamInDto record) {
        DISP20210019DepFindByParamOutDto disp20210019DepFindByParamOutDto = new DISP20210019DepFindByParamOutDto();
        if("0".equals(record.getGetTree())){//需要部门树操作
            record.setPageSize(0);//不需要分页
            try{
                List<DISP20210019DepFindByParamPOutDto> orgArray = baseDepartmentMapper.findDepByParam(record);
                if(orgArray.size()>0){
                    disp20210019DepFindByParamOutDto.setDepArrayList(getDepBranchTree(orgArray,record.getParentDepId()));
                }
                return Result.build(disp20210019DepFindByParamOutDto,ResultCodeBaseEnum.SUCCESS);
            }catch (Exception e){
                log.info("查询部门信息-获取部门树信息失败");
                e.printStackTrace();
            }

        }

        log.info("查询部门信息-查询参数： "+record);
        if(record.getPageNum()!=null && record.getPageNum()>0 && record.getPageSize()>0){//有分页参数才做分页查询
            int pageNum = (record.getPageNum()-1)*record.getPageSize();
            record.setPageNum(new Integer(pageNum));
            int depNum=0;
            try{
                depNum=baseDepartmentMapper.findDepNum(record);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(depNum>0){
                disp20210019DepFindByParamOutDto.setTolPageNum(depNum);
                disp20210019DepFindByParamOutDto.setDepArrayList(baseDepartmentMapper.findDepByParam(record));
            }else {
                return Result.build(null,ResultCodeBaseEnum.PARTORGID_ORG_NULL);
            }
        }else {
            disp20210019DepFindByParamOutDto.setDepArrayList(baseDepartmentMapper.findDepByParam(record));
        }
        return Result.build(disp20210019DepFindByParamOutDto,ResultCodeBaseEnum.SUCCESS);

    }

    /**
     * 修改部门信息
     * @param record
     * @return
     */
    @Override
    public Result<Object> upDep(DISP20210020UpDepInDto record) {
        if(record.getDepId()==null || "".equals(record.getDepId())){
           return Result.build(null,1,"部门ID为空");
        }
        try{
            int upDepNum=baseDepartmentMapper.upDep(record);
            if (upDepNum>0) {
                log.info("修改部门-成功");
                return Result.ok();
            }else {
                log.error("修改部门-失败");
                return Result.fail();
            }
        }catch (Exception e){
            log.info("修改部门信息-修改部门信息失败： "+e);
            throw new RuntimeException("系统错误，数据库修改异常");
        }

    }





    /**
     * 获取机构最上级机构
     * @param seList 机构列表
     * @param maxDep 最上级机构编号
     * @return
     */
    public List<DISP20210019DepFindByParamPOutDto> getDepBranchTree(List<DISP20210019DepFindByParamPOutDto> seList,String maxDep){
        //最上级机构若为空，默认取上级机构为000000的机构为最上级部门
        List<DISP20210019DepFindByParamPOutDto> depBranchList = new ArrayList<>();//要返回的参数
        if(maxDep==null || "".equals(maxDep)){
            for(DISP20210019DepFindByParamPOutDto se : seList){
                if("000000".equals(se.getParentDepId())){//数据库中上级部门空了的数据是异常数据。
                    depBranchList.add(se); //添加最上级部门
                }
            }
        }else {
            for(DISP20210019DepFindByParamPOutDto se : seList){
                if(maxDep.equals(se.getDepId())){
                    depBranchList.add(se); //添加最上级机构
                    break;
                }
            }
        }

        for(DISP20210019DepFindByParamPOutDto jdOrg : depBranchList){
            jdOrg=getAllOrgBranchList(seList,jdOrg);

        }
        return depBranchList;
    }

    /**
     * 根据最上级机构获取机构树
     * @param seList 机构列表
     * @param jdDep 上级部门节点
     * @return
     */
    public DISP20210019DepFindByParamPOutDto getAllOrgBranchList(List<DISP20210019DepFindByParamPOutDto> seList,DISP20210019DepFindByParamPOutDto jdDep){
        for(DISP20210019DepFindByParamPOutDto se : seList){
            if(Objects.equals(jdDep.getDepId(),se.getParentDepId())){
                jdDep.getDepBranchList().add(se);
                se=getAllOrgBranchList(seList,se);
            }
        }
        return jdDep;
    }
}
