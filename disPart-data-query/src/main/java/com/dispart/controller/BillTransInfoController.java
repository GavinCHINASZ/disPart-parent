package com.dispart.controller;

import com.dispart.dto.dataquery.DISP20210355InDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.BillTransInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityCenter")
public class BillTransInfoController {
    @Resource
    private BillTransInfoService service;

    @PostMapping("/DISP20210355")
    @ApiOperation("账单缴费明细查询")
    public Result getBillTransInfo(@RequestBody Request<DISP20210355InDto> inDto){
        return service.getBillTransInfo(inDto.getBody());
    }
}
