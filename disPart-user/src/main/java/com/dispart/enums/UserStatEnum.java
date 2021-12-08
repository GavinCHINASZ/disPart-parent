package com.dispart.enums;

import lombok.Getter;

/**
 * 用户状态
 */
@Getter
public enum UserStatEnum {
    NORMAR("1","正常"),
    LOGOUT("0","注销"),

    WXCNO("01","微信小程序"),
    ZFBCNO("02","支付宝小程序"),
    FARMING_SYS("04","农批系统");

    private String code;
    private String desc;

    private UserStatEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
