package com.dispart.enums;

import lombok.Getter;

/**
 * 客户状态枚举
 */
@Getter
public enum CustomStatusEnum {
    ENABLE("0","启用"),
    DISABLE("1","禁用"),
    NOAUDIT("3","待审核"),
    PASS("4","审核通过"),
    NOPASS("5","审核未通过");
    private String code;
    private String desc;

    private CustomStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static String getValue(String code){
        CustomStatusEnum[] customStatusEnums = values();
        for(CustomStatusEnum customStatusEnum : customStatusEnums){
            if(customStatusEnum.code.equals(code)){
                return customStatusEnum.desc;
            }
        }
        return null;
    }

}
