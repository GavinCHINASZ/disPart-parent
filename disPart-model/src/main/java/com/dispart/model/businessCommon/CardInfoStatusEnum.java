package com.dispart.model.businessCommon;

public enum CardInfoStatusEnum {
    UNPAY("0","待支付"),
    FAIL("1","支付失败"),
    SUCCESS("2","支付成功"),
    UNKNOWN("3","未知交易"),
    WIHDRAW_WAIT("4","待退款"),
    WIHDRAW_SUCCESS("5","退款成功"),
    WIHDRAW_FAIL("6","退款失败"),
    NOT_PAY("9","未支付"),
    CHONGZHENG("10","已冲正")
    ;

    private String cardInfoStatus;
    private String desc;

    CardInfoStatusEnum(String cardInfoStatus, String desc) {
        this.cardInfoStatus = cardInfoStatus;
        this.desc = desc;
    }

    public String getCardInfoStatus() {
        return cardInfoStatus;
    }
}
