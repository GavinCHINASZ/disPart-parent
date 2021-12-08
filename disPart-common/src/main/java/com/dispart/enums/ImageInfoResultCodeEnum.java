package com.dispart.enums;

import lombok.Getter;

@Getter
public enum ImageInfoResultCodeEnum {

    PARAM_NULL(300,"传入参数为空"),
    INSERT_FAIL(301,"添加数据失败"),
    DELETE_NULL(302,"删除失败，数据不存在或已删除"),
    UPDATE_NULL(303,"更新失败，不存在更新条件的数据"),
    INVALID_PARM(304,"非法的参数格式");

    private int code;
    private String message;

    ImageInfoResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
