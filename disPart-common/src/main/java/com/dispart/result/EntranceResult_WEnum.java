package com.dispart.result;

import lombok.Getter;

@Getter
public enum EntranceResult_WEnum {
    PHONE_IS_NULL(11,"电话号码不能为空"),

    SUCCESS(200,"操作成功"),
    FAIL(201,"操作失败");



    private final Integer code;
    private final String message;

    EntranceResult_WEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
