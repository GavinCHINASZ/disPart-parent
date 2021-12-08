package com.dispart.result;

import lombok.Getter;

@Getter
public enum MCardServiceEnum {

    PARAM_NULL(306,"参数不能为空"),
    DATA_OPTION_FAIL(307,"数据库操作失败"),
    VEHICLE_EXIST(310,"该车牌已办理月卡，请去搜索并续费"),
    INVALID_ST_VAL(309,"不合法的支付状态值"),
    WRONG_MCARD_NUM(308,"月卡单号错误"),
    INVALID_AMT(311,"错误金额"),
    NO_PROV_ID(312,"用户ID为空，考虑为历史数据，请作废该记录并重新录入"),
    ;

    private final Integer code;
    private final String message;

    MCardServiceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
