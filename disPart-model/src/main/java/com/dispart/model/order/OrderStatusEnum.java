package com.dispart.model.order;

public enum OrderStatusEnum {
    UNPAYS("1","待支付"),
    SUCCESS("2","成功"),
    FAIL("3","失败"),
    ALL_REFUND("4","全部退款"),
    PART_REFUND("5","部分退款"),
    EXPIRE("6","失效,已超时"),
    FOR_REFUND("7","待退款"),
//    REFUND_FAIL("8","退款失败"),
    FOR_POLLING("9","待轮循"),
    FOR_HADNLE("a","待处理"),

    REFUND_SUCCESS("00","退款成功"),
    REFUND_FAIL("01","退款失败"),
    REFUND_DELAY("02","退款延迟等待"),
    REFUND_NOCOMFIRM("03","网银退款结果不确定"),
    ;

    private String orderStatus;
    private String desc;

    OrderStatusEnum(String orderStatus, String desc) {
        this.orderStatus = orderStatus;
        this.desc = desc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
    public String getDesc(){ return desc; }
}
