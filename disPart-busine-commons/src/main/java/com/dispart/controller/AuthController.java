package com.dispart.controller;

import com.dispart.dto.auth.LoginDto;
import com.dispart.dto.auth.RoleDto;
import com.dispart.dto.auth.WxCheck;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.AuthService;
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
public class AuthController {
    @Autowired
    private AuthService authService;
    /**
     * 回调角色权限给安全中心
     * @return
     */
    @GetMapping("/users/auth/role")
    Result getRoleAuth(){
        return authService.getRoleAuth();
    }

    /**
     * 推送增量角色信息给安全中心
     * @param roleId
     * @return
     */
    @GetMapping("/push/users/auth/role")
    Result pushRoleAuth(String roleId){
        return authService.pushRoleAuth(roleId);
    }
    /**
     * 回调部门权限信息给安全中心
     * @return
     */
    @GetMapping("/users/auth/org")
    Result getDepAuth(){
        return authService.getDepAuth();
    }

    /**
     * 推送部门权限给安全中心
     * @return
     */
    @GetMapping("/push/users/auth/org")
    Result pushDepAuth(String depId){
        return authService.pushDepAuth( depId);
    }
    /**
     * 多渠道登录验证接口
     * @param  loginDto：请求头包含渠道
     *              请求体：userAccount  userPass orgCode phoneTel smsCode loginType(0-账号登录 1-验证码登录)
     * @return
     */
    @PostMapping("/users/login")
    Result checkUserLogin(@RequestBody LoginDto loginDto){
        return authService.checkUserLogin(loginDto.getUserJson());
    }

    /**
     * 检测微信或支付宝是否注册
     * @param request
     * @return
     */
    @PostMapping("/securityCenter/DISP20210111")
    Result wxCheck(@RequestBody Request<WxCheck> request){
        return authService.wxCheck(request);
    }

    /**
     * 获取用户信息
     * @return
     */
    @PostMapping("/securityCenter/DISP20210107")
    Result getUserInfo(@RequestHeader String userNo,@RequestBody Request request){
        return authService.getUserInfo(userNo,request.getHead().getChanlNo());
    }

    /**
     * 获取客户端登录的用户权限菜单
     * @param request
     * @return
     */
    @PostMapping("/securityCenter/DISP20210108")
    Result getUserAuthMenuInfo(@RequestHeader String userNo,@RequestBody Request request){
        return authService.getUserAuthMenuInfo(userNo,request.getHead().getChanlNo());
    }

    /**
     * 获取客户端用户的某个角色权限信息
     * @param request
     * @return
     */
    @PostMapping("/securityCenter/DISP20210109")
    Result getUserAuthMenuInfoByRoleId(@RequestHeader String userNo,@RequestBody Request<RoleDto> request){
        return authService.getUserAuthMenuInfoByRoleId(userNo,request.getHead().getChanlNo(),request.getBody().getRoleId());
    }

    /**
     * 获取客户端登录的用户的权限菜单下的按钮菜单权限信息
     * @param request
     * @return
     */
    @PostMapping("/securityCenter/DISP20210110")
    Result getUserAuthButtonInfo(@RequestHeader String userNo,@RequestBody Request<RoleDto> request){
        return authService.getUserAuthButtonInfo(userNo,request.getHead().getChanlNo()
                ,request.getBody().getMenuId(),request.getBody().getRoleId());
    }
}