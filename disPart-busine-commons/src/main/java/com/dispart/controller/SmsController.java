package com.dispart.controller;

/**
 * @author:xts
 * @date:Created in 2021/6/13 1:47
 * @description 短信接口
 * @modified by:
 * @version: 1.0
 */

import com.dispart.dto.busineCommon.SmsDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.SmsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {
    @Autowired
    private SmsService smsService;
    @PostMapping("/securityCenter/DISP20210078")
    @ApiOperation(value = "发送短信验证码")
    public Result sendCode(@RequestBody Request<SmsDto> dtoRequest) {
        return smsService.sendCode(dtoRequest.getBody().getType(),dtoRequest.getBody().getTel(),dtoRequest.getBody().getParam1(),dtoRequest.getBody().getCost());
    }
}