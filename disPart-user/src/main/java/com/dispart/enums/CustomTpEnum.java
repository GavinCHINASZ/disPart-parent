package com.dispart.enums;

import lombok.Getter;

/**
 * 客户类型枚举
 */
@Getter
public enum CustomTpEnum {

    PERSONAL("0","个人"),
    BUSINESS("1","企业");
    private String code;
    private String desc;

    private CustomTpEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getValue(String code){
        CustomTpEnum[] customTpEnums = values();
        for(CustomTpEnum customTpEnum : customTpEnums){
            if(customTpEnum.code.equals(code)){
                return customTpEnum.desc;
            }
        }
        return null;
    }
}
