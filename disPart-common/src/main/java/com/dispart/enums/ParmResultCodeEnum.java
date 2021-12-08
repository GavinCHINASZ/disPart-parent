package com.dispart.enums;

import lombok.Getter;

@Getter
public enum ParmResultCodeEnum {
    PARAM_NULL(310,"参数为空"),
    UPDATE_FAIL(311,"更新失败，数据库没有匹配的记录");

    private int code;
    private String message;

    ParmResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
