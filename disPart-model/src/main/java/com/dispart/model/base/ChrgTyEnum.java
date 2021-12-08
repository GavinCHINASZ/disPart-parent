package com.dispart.model.base;
import lombok.Getter;

@Getter
public enum ChrgTyEnum {
    EMPTY_CAR("0","空车"),
    SUPPLY_CAR("1","供货车"),
    FREE_MON_CAR("2","免费月卡");
    private final String code;
    private final String carType;
    ChrgTyEnum(String code,String carType){
        this.code=code;
        this.carType=carType;
    }
}
