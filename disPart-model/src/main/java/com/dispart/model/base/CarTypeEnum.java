package com.dispart.model.base;

import lombok.Getter;

@Getter
public enum CarTypeEnum {
    VEHICEL_TPF("01","一类车"),
    VEHICEL_TPS("02","二类车"),
    VEHICEL_TPT("03","三类车"),
    VEHICEL_TPFU("04","四类车");
      private final String code;
      private final String type;
      CarTypeEnum(String code,String type){
        this.code=code;
        this.type=type;
        }
}
