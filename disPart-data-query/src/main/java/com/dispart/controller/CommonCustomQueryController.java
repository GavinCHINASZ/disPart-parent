package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210209InDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CommonCustomQueryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/securityCenter")
public class CommonCustomQueryController {

    @Autowired
    private CommonCustomQueryService service;

    @PostMapping("/DISP20210209")
    public Result queryCustomInfo(@RequestBody Request<Disp20210209InDto> inDto){
        return service.customInfoCommonQuery(inDto.getBody());
    }
}
