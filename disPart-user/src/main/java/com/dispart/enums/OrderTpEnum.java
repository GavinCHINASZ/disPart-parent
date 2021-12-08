package com.dispart.enums;

import lombok.Getter;

@Getter
public enum OrderTpEnum {
    EASY("01","简易模式"),
    DATIL("02","明细模式"),
    ALL("03","所有模式"),

    CANCEL("0","取消"),
    SETUP("1","设置"),

    EFFE("1","启用"),
    AVAIL("0","停用");

    private final String code;
    private final String desc;

    private OrderTpEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
