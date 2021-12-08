package com.dispart.controller;


import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamOutDto;
import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMa;
import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMaOut;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.DeviceManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wujie
 * @version 1.0.0:
 * @title UserController
 * @Description TODO
 * @dateTime 2021/6/3 15:24
 * @Copyright 2020-2021
 */
@RestController
@RequestMapping("/securityCenter")
@Api(tags = "部门管理")
@Slf4j
@CrossOrigin
public class DeviceMaController {
    @Autowired
    DeviceManagerService deviceManagerService;

    @PostMapping("DISP20210116")
    @ApiOperation(value = "查询客户设备信息")
    public Result findDeMa(@RequestBody Request<DISP20210116FindDeMa> deMaRequest){
        Result<DISP20210116FindDeMaOut> ok = deviceManagerService.findDeMa(deMaRequest.getBody());
        return ok;
    }

    @PostMapping("DISP20210117")
    @ApiOperation(value = "客户绑定设备")
    public Result addDeMa(@RequestBody Request<DISP20210116FindDeMa> deMaRequest){
        Result<DISP20210116FindDeMaOut> ok = deviceManagerService.addDeMa(deMaRequest.getBody());
        return ok;
    }

    @PostMapping("DISP20210118")
    @ApiOperation(value = "修改客户绑定设备")
    public Result upDeMa(@RequestBody Request<DISP20210116FindDeMa> deMaRequest){
        Result<DISP20210116FindDeMaOut> ok = deviceManagerService.uPDeMa(deMaRequest.getBody());
        return ok;
    }

    @PostMapping("DISP20210119")
    @ApiOperation(value = "删除客户绑定设备")
    public Result deDeMa(@RequestBody Request<DISP20210116FindDeMa> deMaRequest){
        Result<DISP20210116FindDeMaOut> ok = deviceManagerService.deDeMa(deMaRequest.getBody());
        return ok;
    }

}
