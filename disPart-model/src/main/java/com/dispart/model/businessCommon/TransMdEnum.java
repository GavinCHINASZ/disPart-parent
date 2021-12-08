package com.dispart.model.businessCommon;

import lombok.Getter;

/**
 * 交易方式
 */
@Getter
public enum TransMdEnum {
    HSB("1","惠市宝"),
    CASH("2","现金"),
    POS_CARD("3","pos机银行卡"),
    POS_ERWEIMA("6","pos机二维码"),
    CARD("4","一卡通"),
    BANK("5","银行卡"),
    CHONGZHENG("7","冲正"),
    OTHER("8","其他充值");

    private String transMDStatus;
    private String desc;

    TransMdEnum(String transMDStatus, String desc) {
        this.transMDStatus = transMDStatus;
        this.desc = desc;
    }

    public static String getDesc(String type) {
        TransMdEnum[] values = TransMdEnum.values();
        for (TransMdEnum transMdEnum:values) {
            if (transMdEnum.getTransMDStatus().equals(type)) {
                return transMdEnum.getDesc();
            }
        }
        return "";
    }

}
