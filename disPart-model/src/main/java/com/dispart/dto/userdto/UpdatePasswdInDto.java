package com.dispart.dto.userdto;

import lombok.Data;

@Data
public class UpdatePasswdInDto {
    //用户手机号
    private String userPhone;
    //原密码
    private String oldUsPaWd;
    //新密码
    private String newUsPaWd;
    //确认密码
    private String verifyUsPaWd;
    //验证码
    private String regCode;
}
