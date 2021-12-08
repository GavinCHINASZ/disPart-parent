package com.disPart.controller;

import com.disPart.Service.LoginAuthService;
import com.dispart.dto.auth.LoginDto;
import com.dispart.dto.auth.RoleDto;
import com.dispart.dto.auth.WxCheck;
import com.dispart.request.Request;
import com.dispart.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:xts
 * @date:Created in 2021/6/19 14:27
 * @description 安全中心权限同步接口
 * @modified by:
 * @version: 1.0
 */
@RestController
@RequestMapping("/outCommon")
public class LoginAuthController {
    @Autowired
    private LoginAuthService loginAuthService;
    /**
     * 微信或支付宝登录授权，获取openid
     *  String param="code="+request.getBody().getWxCode()+"&chanlNo="+request.getHead().getChanlNo();
     * @return
     */
    @PostMapping("/wx_zfb/login/auth")
    Result loginAuthCheck(@RequestBody  String paramStr ){
        return loginAuthService.loginAuthCheck(paramStr);
    }


}