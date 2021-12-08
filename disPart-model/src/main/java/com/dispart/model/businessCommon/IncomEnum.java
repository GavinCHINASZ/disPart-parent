package com.dispart.model.businessCommon;

/**
 *
 */
public enum IncomEnum {

    IN("0", "进账"),
    OUT("1", "出账");

    private String value;
    private String desc;

    IncomEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }
}
