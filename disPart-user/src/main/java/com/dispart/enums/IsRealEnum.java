package com.dispart.enums;

import lombok.Getter;

/**
 * 是否实名枚举
 */
@Getter
public enum IsRealEnum {
    ISREAL("0","实名"),
    NOREAL("1","未实名");
    private String code;
    private String desc;

    private IsRealEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static String getValue(String code){
        IsRealEnum[] isRealEnums = values();
        for(IsRealEnum isRealEnum : isRealEnums){
            if(isRealEnum.code.equals(code)){
                return isRealEnum.desc;
            }
        }
        return null;
    }
}
