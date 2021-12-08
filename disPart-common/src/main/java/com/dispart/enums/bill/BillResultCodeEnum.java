package com.dispart.enums.bill;

import lombok.Getter;

@Getter
public enum BillResultCodeEnum {

    PARAM_NULL(306,"参数不能为空"),
    BILL_ABOLISHED(311,"账单已作废"),
    BILL_NOT_PAYED(312,"账单未支付，不能申请退款"),
    BILL_IS_ABOLISHED(314,"账单已作废"),
    INVALID_BILLNUM(315,"不正确的账单编号"),
    UPDATE_FAIL(316,"更新数据库失败"),
    INVALID_PARAM(317,"不合法的参数"),
    INVALID_AMT(317,"金额不正确"),
    BILL_IS_PAYED(313,"账单已支付，不能作废");

    private Integer code;
    private String desc;

    private BillResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
