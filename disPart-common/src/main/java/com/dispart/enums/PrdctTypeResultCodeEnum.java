package com.dispart.enums;

import lombok.Getter;

@Getter
public enum PrdctTypeResultCodeEnum {
    PARAM_NULL(340,"参数为空"),
    INSERT_FAIL(341,"新增数据失败"),
    UPDATE_NULL(342, "更新失败，不存在符合更新条件的数据"),
    SELECT_NULL(343,"查询失败，数据库找不到符合条件的数据"),
    DELETE_NULL(344,"删除失败，数据库不存在符合删除条件的记录"),
    UPDATE_CONFLICT(342, "更新失败，数据库已有相同的商品类型名"),
    INSERT_CONFLICT(343,"插入失败，数据库已有相同的商品类型名");

    private int code;
    private String message;

    PrdctTypeResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
