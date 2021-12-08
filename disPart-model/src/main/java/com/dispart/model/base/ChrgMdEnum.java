package com.dispart.model.base;

import lombok.Getter;

@Getter
public enum ChrgMdEnum {

    PER_HOUR("0","每多少小时多少钱"),
    FIX_FEE("1","固定收费");
    private final String code;
    private final String chrgType;
    ChrgMdEnum(String code,String chrgType){
        this.code=code;
        this.chrgType=chrgType;
    }
}
