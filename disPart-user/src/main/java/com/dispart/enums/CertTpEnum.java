package com.dispart.enums;

import lombok.Getter;

/**
 * 证件类型枚举
 */
@Getter
public enum CertTpEnum {
    PERSONALCT("01","居民身份证"),
    BUSINESSCT("02","企业营业执照号");
    private String code;
    private String desc;

    private CertTpEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static String getValue(String code){
        CertTpEnum[] certTpEnums = values();
        for(CertTpEnum certTpEnum : certTpEnums){
            if(certTpEnum.code.equals(code)){
                return certTpEnum.desc;
            }
        }
        return null;
    }
}
