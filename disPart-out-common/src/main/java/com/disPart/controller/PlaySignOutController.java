package com.disPart.controller;

import com.disPart.Service.PlaySignOutService;
import com.disPart.Service.SmsService;
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
public class PlaySignOutController {
    @Autowired
    private PlaySignOutService playSignOutService;

    @PostMapping("/securityCenter/PlaySignOut")
    @ApiOperation(value = "发送短信验证码")
    public Result sendCode(@RequestBody  String path ) {
        return playSignOutService.getPlaySoundSign(path);
    }
}