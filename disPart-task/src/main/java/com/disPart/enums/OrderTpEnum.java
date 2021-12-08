package com.disPart.enums;

import lombok.Getter;

@Getter
public enum OrderTpEnum {
    EASY("01","简易模式"),
    DATIL("02","明细模式"),

    EFFE("1","启用"),
    AVAIL("0","停用");

    private final String code;
    private final String desc;

    private OrderTpEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
