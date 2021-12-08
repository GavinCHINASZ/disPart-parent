package com.dispart.dto.userdto;

import lombok.Data;

@Data
public class ReSetPasswdInDto {
    //用户手机号
    private String userPhone;
    //用户密码
    private String newUsPaWd;
    //确认密码
    private String verifyUsPaWd;
    //验证码
    private String regCode;
}
