package com.dispart.controller;

import com.dispart.dto.hsbdto.HsbCustomDto;
import com.dispart.dto.hsbdto.QuryCustomInfoInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.HsbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:xts
 * @date:Created in 2021/6/13 23:16
 * @description 惠市宝交易接口
 * @modified by:
 * @version: 1.0
 */
@RestController
public class HsbController {
    @Autowired
    private HsbService hsbService;
    @PostMapping("/securityCenter/DISP20210077")
    @ApiOperation(value = "惠市宝签约客户信息增量回调接口")
    public String customInfoCallback(@RequestBody String params ) {
        return hsbService.customInfoCallback(params);
    }



}