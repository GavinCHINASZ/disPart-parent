package com.dispart.controller;


import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EntranceVeCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "车辆进出场货物核验")
@Slf4j
@CrossOrigin
public class EntranceVeCheckController {

    @Autowired
    EntranceVeCheckService entranceVeCheckService;


    @PostMapping("/DISP20210287")
    @ApiOperation(value = "进场货物核验-进场信息查询")
    public Result<QuryEntranceVeCheckOutDto> quryVeCheckInfo(@RequestBody Request<QuryEntranceVeCheckInDto> param) {
        return entranceVeCheckService.quryVeCheckInfo(param);
    }

    @PostMapping("/DISP20210245")
    @ApiOperation(value = "进场货物核验-进场货物信息修改")
    public Result<ResultOutDto> upDateVeCheckInfo(@RequestBody Request<UpdateEntranceVeCheckInDto> param) {
        return entranceVeCheckService.upDateVeCheckInfo(param);
    }

}
