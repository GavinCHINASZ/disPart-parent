package com.disPart.controller;

import com.disPart.Service.SmsService;
import com.dispart.dto.userdto.QueryUserInfoInDto;
import com.dispart.dto.userdto.QueryUserInfoOutDto;
import com.dispart.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:xts
 * @date:Created in 2021/6/12 21:37
 * @description 短信接口
 * @modified by:
 * @version: 1.0
 */
@RestController
@RequestMapping("/outCommon")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping("/securityCenter/DISP20210099")
    @ApiOperation(value = "发送短信验证码")
    public Result sendCode(@RequestBody  String parmStr ) {
        return smsService.sendCode(parmStr);
    }
}