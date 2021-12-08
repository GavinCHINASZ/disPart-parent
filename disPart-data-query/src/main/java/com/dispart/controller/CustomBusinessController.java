package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210353InDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CustomBusinessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityCenter")
public class CustomBusinessController {

    @Resource
    private CustomBusinessService service;

    @PostMapping("/DISP20210353")
    @ApiOperation("获取用户经营报表")
    public Result getBusinessReport(@RequestBody Request<Disp20210353InDto> param){
        return service.getBusinessReport(param.getBody());
    }
}
