package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title EmpController
 * @Description TODO
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "员工管理")
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class EmployeeController {

    @Autowired
    private  EmployeeService employeeService;

    @PostMapping("/DISP20210010")
    @ApiOperation(value = "新增员工")
    public Result<ResultOutDto> addEmployee(@RequestBody Request<AddEmpInDto> param){
        Result<ResultOutDto> result = employeeService.insertEmployeeInfo(param.getBody(),param.getHead().getOperator());
        return result;
    }

    @PostMapping("/DISP20210012")
    @ApiOperation(value = "员工修改")
    public Result<ResultOutDto>  updateEmployee(@RequestBody Request<UpdateEmpInDto> param){
        Result<ResultOutDto> result = employeeService.updateEmployeeInfo(param);
        return result;
    }

    @PostMapping("/DISP20210013")
    @ApiOperation(value = "员工锁定解锁")
    public Result<ResultOutDto>  updateEmployeeLock(@RequestBody Request<LockEmpInDto> param){
        Result<ResultOutDto> result = employeeService.updateEmployeeEmpSt(param);
        return result;
    }

    @PostMapping("/DISP20210014")
    @ApiOperation(value = "员工注销")
    public Result<ResultOutDto>  updateEmployeeLoff(@RequestBody Request<LockEmpInDto> param){
        Result<ResultOutDto> result = employeeService.updateEmployeeLogOff(param);
        return result;
    }

    @PostMapping("/DISP20210308")
    @ApiOperation(value = "查询用户信息(模糊查询)")
    public Result<QuryEmpOutDto> quryEmployee(@RequestBody Request<QuryEmpInDto>  param){
        return employeeService.quryEmployeeInfo(param.getBody());
    }


}
