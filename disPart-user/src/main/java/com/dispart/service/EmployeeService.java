package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.*;
import com.dispart.dto.userdto.EmpFindMenuInDto;
import com.dispart.dto.userdto.EmpRoleFindMenuOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface EmployeeService {
    /**
     * 新增用户
     * @param param
     * @return
     */
    public Result<ResultOutDto> insertEmployeeInfo(AddEmpInDto param,String operator);

    /**
     * 修改用户
     * @param param
     * @return
     */
    public Result<ResultOutDto>  updateEmployeeInfo(Request<UpdateEmpInDto> param);

    /**
     * 用户锁定解锁
     * @param param
     * @return
     */
    public Result<ResultOutDto>  updateEmployeeEmpSt(Request<LockEmpInDto> param);

    /**
     * 用户注销
     * @param param
     * @return
     */
    public Result<ResultOutDto>  updateEmployeeLogOff(Request<LockEmpInDto> param);

    /**
     * 查询员工信息
     * @param param
     * @return
     */
    public Result<QuryEmpOutDto> quryEmployeeInfo(QuryEmpInDto param);

}
