package com.dispart.enums;

import lombok.Getter;

@Getter
public enum MCardEnum {
    PARAM_NULL(306,"参数不能为空"),
    IS_ABOLISHED(308,"月卡账单已作废"),
    IS_PAYED(309,"月卡账单已付费，不能作废"),
    IS_NOT_PAY(311,"月卡已经生成账单"),
    VEHICLE_IS_EXIST(310,"该车牌已办理月卡，请去搜索并续费")
    ;

    private Integer code;
    private String msg;

    private MCardEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
