package com.dispart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamOutDto;
import com.dispart.dto.departmentdto.DISP20210020UpDepInDto;
import com.dispart.result.Result;
import com.dispart.vo.basevo.DepartmentVo;


public interface BaseDepartmentService extends IService<DepartmentVo> {
    //int deleteByPrimaryKey(String depId);
    //int insertSelective(DepartmentVo record);
    //DepartmentVo selectByPrimaryKey(String depId);
    //int updateByPrimaryKey(DepartmentVo record);

    /**
     * 添加部门，并返回该部门信息
     * @param record
     * @return
     */
    Result insert(DepartmentVo record);

    /**
     * 修改部门状态为删除
     * @param record
     * @return
     */
    Result upDepSt(DepartmentVo record);

    /**
     * 查询部门信息
     * @param record
     * @return
     */
    Result<DISP20210019DepFindByParamOutDto> findDepByParam(DISP20210019DepFindByParamInDto record);

    /**
     * 修改部门信息
     * @param record
     * @return
     */
    Result upDep(DISP20210020UpDepInDto record);

}
