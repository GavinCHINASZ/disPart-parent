package com.dispart.enums.bill;

import lombok.Getter;

@Getter
public enum BillPaymentSt {

    SUCCESS("2","支付成功"),
    NOT_PAY("9","未支付"),
    PAY_WAIT("0","支付处理中"),
    REFUNDING("4","退款处理中"),
    REFUNDED("5","退款成功"),
    ;

    private String code;
    private String desc;

    private BillPaymentSt(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
