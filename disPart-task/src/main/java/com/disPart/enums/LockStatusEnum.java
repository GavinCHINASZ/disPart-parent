package com.disPart.enums;

public enum LockStatusEnum {

    NOT_RUNNING("0", "任务未运行"),
    RUNNING("1", "任务运行中");

    private String value;
    private String desc;

    LockStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
