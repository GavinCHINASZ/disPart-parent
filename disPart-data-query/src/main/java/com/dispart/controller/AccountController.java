package com.dispart.controller;

import com.dispart.dto.dataquery.DISP20210354InDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.AccOperationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityCenter")
public class AccountController {

    @Resource
    private AccOperationService service;

    @PostMapping("/DISP20210354")
    @ApiOperation("账户操作记录查询")
    public Result getAccOperationInfo(@RequestBody Request<DISP20210354InDto> inDto){
        return service.getAccOperation(inDto.getBody());
    }
}
