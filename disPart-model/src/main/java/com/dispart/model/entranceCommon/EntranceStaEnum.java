package com.dispart.model.entranceCommon;

import lombok.Getter;

@Getter
public enum EntranceStaEnum {
    NO_PAY("0","未付款"),
    IN("1","已进场"),
    REFUNFDING("2","退款中"),
    OUT("3","已出场"),
    INVALID("4","作废");

    private String entranceStatus;
    private String desc;

    EntranceStaEnum(String entranceStatus, String desc) {
        this.entranceStatus = entranceStatus;
        this.desc = desc;
    }

    public static String getDesc(String type) {
        EntranceStaEnum[] values = EntranceStaEnum.values();
        for (EntranceStaEnum entranceStaEnum:values) {
            if (entranceStaEnum.getEntranceStatus().equals(type)) {
                return entranceStaEnum.getDesc();
            }
        }
        return null;
    }
}
