package com.dispart.result;

import lombok.Getter;

/**
 * @author:xts
 * @date:Created in 2021/6/19 19:35
 * @description 公共服务定义相关返回码
 * @modified by:
 * @version: 1.0
 */
@Getter
public enum BusineConmonCodeEnum {
    /****************/
    NOT_SELECT_INFO(-501, "未查询到相关信息"),
    PARAM_NULL(-502,"请求参数为空"),
    PHONETEL_NOT_REGIST(-503,"该手机号未注册，请先注册后再进行登录！"),
    SMSCODE_CHECK_ERROR(-504,"输入的验证码错误，请确认后重新输入！"),
    ACCOUNT_NOT_EXIT(-505,"该账号不存在，请确认后重新输入！"),
    PASSWORD_NOT_ERROR(-506,"输入的密码错误，请确认后重新输入！"),
    CHECK_CUSTOM_ACOUNT_EXC(-559,"登录异常，请稍后再试！"),
    ACCOUNT_LOCK(-507,"该账号已被锁定，请联系管理人员！"),
    ACCOUNT_ZHUXIAO(-508,"该账号已被注销，不能再进行登录！"),
    NOT_AUTH(-509,"该用户没有还未分配权限，请联系管理员！"),
    GET_WEIXIN_LOGIN_FAIL(-510,"微信登录失败！"),
    GET_ZHIFUBAO_LOGIN_FAIL(-512,"支付宝登录失败！"),
    WEIXIN_NOT_REG(-511,"微信号未在平台注册，请求先注册！"),

    PARAMS_ERROR(-550,"请求参数不正确"),
    JNML_INSERT_ERROR(-551,"新增流水失败"),
    JNML_SELECT_ERROR(-552,"查询流水失败"),
    JNML_UPDATE_ERROR(-553,"更新流水失败"),
    MEMBERSHIP_SELECT_ERROR(-554,"会员卡查找失败"),
    MEMBERSHIP_STATUS_ERROR(-555,"会员卡状态错误"),
    ACCOUNT_SELECT_ERROR(-556,"账户查找失败"),
    ACCOUNT_AVAIL_ERROR(-557,"账户余额不足"),
    ACCOUNT_UPDATE_ERROR(-558,"账户余额更新"),
//    JNML_UPDATE_ERROR(-553,"更新流水失败"),
    SYS_ERROR(-203,"系统异常"),

    ;

    private Integer code;
    private String message;

    private BusineConmonCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}