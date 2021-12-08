package com.dispart.result;

import lombok.Getter;

/**
 * @author:xts
 * @date:Created in 2021/6/12 18:37
 * @description 短信接口返回码
 * @modified by:
 * @version: 1.0
 */
@Getter
public enum OutCommonResultCodeEnum {
    PARAM_NULL(0,"系统业务-参数为空"),
    SUCCESS(0,"短信业务-调用接口成功"),
    FAIL(201, "短信业务-账户名为空"),
    USERNAME_NULL(-1,"短信业务-账户名为空"),
    USERNAME_PASSWD_ERROR(-2,"短信业务-账户密码错误"),
    USERNAME_NOT_EXIT(-3,"短信业务-账户不存在"),
    USERNAME_PASSWD_ERROR1(-4,"短信业务-账户密码错误"),
    SEND_TEL_NULL(-5,"短信业务-发送手机号为空"),
    SEND_CONTENT_NULL(-6,"短信业务-发送内容为空"),
    SGIN_NULL(-7,"短信业务-签名为空"),
    TEL_GS_ERROR(-8,"短信业务-手机号格式错误"),
    ACCOUNT_BALANCE(-11,"短信业务-账户余额不足"),
    ACCOUNT_NOT_RECHARGED(-12,"短信业务-账户没有充值"),
    ACCOUNT_FROZEN(-15,"短信业务-账户冻结"),
    TOKEN_NULL(-19,"短信业务-token为空"),
    TEMPLATE_NOT_EXAMINE(-23,"短信业务-模板未审批通过"),
    NOT_GET_TEMPLATE(-24,"短信业务-未获取到模板"),
    SGIN_ERROR(-50,"短信业务-签名错误"),
    REQ_OUT_TIME(-51,"短信业务-访问超时"),
    NOT_EXCEPTION(-99,"短信业务-未知异常"),
    GET_WEIXIN_LOGIN_FAIL(-510,"微信授权失败！"),
    GET_ZHIFUBAO_LOGIN_FAIL(-512,"支付宝授权失败！"),
    WEIXIN_NOT_REG(-511,"微信号未在平台注册，请求先注册！");
    private Integer code;
    private String message;
    private OutCommonResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}