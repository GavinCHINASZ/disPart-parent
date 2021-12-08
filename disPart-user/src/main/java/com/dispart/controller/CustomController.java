package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.UserPlaceOrderTypeInDto;
import com.dispart.dto.empdto.UserQuryOrderTypeInDto;
import com.dispart.dto.empdto.UserQuryOrderTypeOutDto;
import com.dispart.dto.userdto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CustomInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title CustomController
 * @Description TODO
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "用户管理")
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class CustomController {

    @Autowired
    CustomInfoService customInfoService;


    @PostMapping("/DISP20210003")
    @ApiOperation(value = "用户注册账号")
    public Result<ResultOutDto>   userRegAcct(@RequestBody Request<UserRegAcctInDto> param){
        return customInfoService.userRegAcct(param);
    };
    @PostMapping("/checkCustomOrAcount")
    @ApiOperation(value = "验证某个手机号用户名下的客户信息以及账户情况")
    public Result<ResultOutDto>   opraerCustomAndOpenAcount(@RequestBody Request<UserPhone> param){
        return customInfoService.opraerCustomAndOpenAcount(param);
    };

    @PostMapping("/DISP20210004")
    @ApiOperation(value = "用户重置密码")
    public Result<ResultOutDto>   userReSetPasswd(@RequestBody Request<ReSetPasswdInDto>  param){
        return customInfoService.userReSetPasswd(param);
    };

    @PostMapping("/DISP20210005")
    @ApiOperation(value = "用户修改密码")
    public Result<ResultOutDto>   userUpdatePasswd(@RequestHeader  String userNo,@RequestBody Request<UpdatePasswdInDto> param){
        return customInfoService.userUpdatePasswd(userNo,param.getBody());
    };

    @PostMapping("/DISP20210006")
    @ApiOperation(value = "客户个人信息查询")
    public Result<QueryUserInfoOutDto> quryCustomInfo(@RequestHeader  String userNo,@RequestBody Request<QueryUserInfoInDto> param) {
        return customInfoService.quryUserInfo(userNo,param.getBody());
    }

    @PostMapping("/DISP20210007")
    @ApiOperation(value = "客户个人名片设置")
    public Result<ResultOutDto> setPersonCard(@RequestHeader  String userNo,@RequestBody Request<UserCardSetInDto> param) {
        Result<ResultOutDto> result = customInfoService.setPersonCard(userNo,param.getBody());
        return result;
    }

    @PostMapping("/DISP20210008")
    @ApiOperation(value = "用户查询下单码")
    public Result<QuryOrderCodeOutDto> quryPlaceOrderCode(@RequestBody Request<QuryOrderCodeInDto> param) {
        Result<QuryOrderCodeOutDto> result = customInfoService.quryOrderCode(param.getBody());
        return result;
    }

    @PostMapping("/DISP20210009")
    @ApiOperation(value = "微信，支付宝小程序校验接口")
    public Result<ResultOutDto> wxZfbappCheck(@RequestBody Request<UserAppCheckInDto> param) {
        Result<ResultOutDto> result = customInfoService.quryAppIdCheck(param);
        return result;
    }

    @PostMapping("/DISP20210100")
    @ApiOperation(value = "客户下单模式配置")
    public Result<ResultOutDto>   setPlaceOrderType(@RequestBody Request<UserPlaceOrderTypeInDto> param) {
        return customInfoService.setPlaceOrderType(param.getBody());
    };

    @PostMapping("/DISP20210111")
    @ApiOperation(value = "查询客户下单模式")
    public Result<UserQuryOrderTypeOutDto>  quryUserPlaceOrderType(@RequestBody Request<UserQuryOrderTypeInDto> param){
        return customInfoService.quryUserPlaceOrderType(param.getBody());
    }

}
