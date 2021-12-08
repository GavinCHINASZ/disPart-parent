package com.dispart.enums;

import lombok.Getter;

@Getter
public enum BaseEnum {

    PARAM_NULL(306,"参数不能为空"),
    INVALID_PARAM(307,"参数不合法"),
    ;

    private Integer code;
    private String msg;

    private BaseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
