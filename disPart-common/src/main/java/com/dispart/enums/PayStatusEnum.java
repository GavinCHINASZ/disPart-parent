package com.dispart.enums;


import lombok.Getter;

@Getter
public enum PayStatusEnum {
    DEALING("0","支付处理中"),
    FAIL("1","支付失败"),
    SUCCESS("2","支付成功"),
    UNKNOWN("3","支付结果未知"),
    REFUNDING("4","退款处理中"),
    REFUNDED("5","已退款"),
    NOT_PAY("9","未支付")
    ;

    private String code;
    private String msg;

    PayStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getDesc(String type) {
        PayStatusEnum[] values = PayStatusEnum.values();
        for (PayStatusEnum payStatusEnum:values) {

            if (payStatusEnum.getCode().equals(type)) {
                return payStatusEnum.getMsg();
            }
        }
        return null;
    }
}
