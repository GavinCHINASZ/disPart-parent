package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.BundingRoleInDto;
import com.dispart.dto.empdto.QuryBindRoleInDto;
import com.dispart.dto.empdto.QuryBindRoleOutDto;
import com.dispart.dto.empdto.UnBundingRoleByInDto;
import com.dispart.dto.userdto.EmpFindMenuInDto;
import com.dispart.dto.userdto.EmpRoleFindMenuOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EmployeeRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title EmployeeRoleCounter
 * @Description TODO
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "员工管理")
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class EmployeeRoleCounter {

    @Autowired
    EmployeeRoleService employeeRoleService;

    @PostMapping("/DISP20210015")
    @ApiOperation(value = "用户绑定角色查询")
    public Result<QuryBindRoleOutDto> QuryBindRoleInDto(@RequestBody Request<QuryBindRoleInDto> param) {
        return employeeRoleService.quryBindRole(param.getBody());
    }

    @PostMapping("/DISP20210017")
    @ApiOperation(value = "用户绑定角色")
    public Result<ResultOutDto> bindRole(@RequestBody Request<BundingRoleInDto> param) {
        return  employeeRoleService.bindRole(param.getBody());
    }

    @PostMapping("/DISP20210016")
    @ApiOperation(value = "用户解绑角色")
    public Result<ResultOutDto> unBindRole(@RequestBody Request<UnBundingRoleByInDto> param) {
        return  employeeRoleService.unBindRole(param.getBody());
    }

    @PostMapping("/DISP20210018")
    @ApiOperation(value = "查询员工权限菜单")
    public Result<List<EmpRoleFindMenuOutDto>> qryEmpAuthMenu(@RequestBody Request<EmpFindMenuInDto> param) {
        return employeeRoleService.qryEmpAuthMenu(param.getBody());
    }

}
