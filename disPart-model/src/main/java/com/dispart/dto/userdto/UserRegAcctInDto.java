package com.dispart.dto.userdto;

import lombok.Data;

@Data
public class UserRegAcctInDto {
    //用户手机号
    private String userPhone;
    //用户密码
    private String usPaWd;
    //确认密码
    private String verifyUsPaWd;
    //验证码
    private String regCode;
    //微信openId
    private String wxOpenId;
    //支付宝openId
    private String zfbOpenId;
    //微信初始向量
    private String wxIv;
    //微信目标密文
    private String encrypted;
    //支付宝报文
    private String response;
    //支付宝签名串
    private String sing;

}
