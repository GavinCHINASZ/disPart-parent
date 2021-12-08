package com.dispart.dto.auth;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/20 13:22
 * @description 登录请求参数
 * @modified by:
 * @version: 1.0
 */
@Data
public class LoginJsonDto {
    private String userAccount;//账号
    private String userPass;//密码
    private String orgCode;//当前机构号
    private String phoneTel;//手机号
    private String smsCode;//短信验证码
    private String loginType;//登录类型 0-账号-密码登录 1-账号-验证码登录
    private String sfCode;//第三方登录请求code
}