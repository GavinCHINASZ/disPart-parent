package com.dispart.enums;

import lombok.Getter;

@Getter
public enum EmpStatEnum {
    NORMAR("0","正常"),
    LOCK("1","锁定"),
    DETEED("2","注销");
    private String code;
    private String desc;

    private EmpStatEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
