package com.dispart.enums;

import lombok.Getter;

@Getter
public enum DataMerchantEnum {
    VARIETY_TYPE_VEGETABLE("1","蔬菜"),
    VARIETY_TYPE_FRUIT("2","水果"),
    VARIETY_TYPE_MUSHROOM("3","食用菌"),
    VARIETY_TYPE_OTHER("0","其他"),
    SEASON_TYPE_YES("1","季节性"),
    SEASON_TYPE_NO("0","非季节性"),
    STATUS_NORMAL("1","正常"),
    STATUS_NOT_NORMAL("0","不正常"),
    IS_CCB("1","是"),
    IS_NOT_CCB("0","否"),
    ;

    private String code;
    private String value;

    private DataMerchantEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
