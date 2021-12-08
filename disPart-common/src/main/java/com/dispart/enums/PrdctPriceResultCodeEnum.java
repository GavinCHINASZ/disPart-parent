package com.dispart.enums;

import lombok.Getter;

@Getter
public enum PrdctPriceResultCodeEnum {
    PARAM_NULL(320,"参数为空"),
    EXIST_FAIL_ITEM(348,"数据导入存在失败项");


    private int code;
    private String message;

    PrdctPriceResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
