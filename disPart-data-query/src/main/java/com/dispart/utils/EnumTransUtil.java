package com.dispart.utils;

public class EnumTransUtil {

    public static String convertPaymentSt(String value){
        if ("9".equals(value)){ return "未支付"; }
        if ("0".equals(value)){ return "支付处理中"; }
        if ("1".equals(value)){ return "支付失败"; }
        if ("2".equals(value)){ return "支付成功"; }
        if ("3".equals(value)){ return "支付结果未知"; }
        if ("4".equals(value)){ return "退款处理中"; }
        if ("5".equals(value)){ return "退款成功"; }
        return value;
    }

    public static String convertPaymentMode(String value){
        if ("1".equals(value)){ return "惠市宝"; }
        if ("2".equals(value)){ return "现金"; }
        if ("3".equals(value)){ return "pos机银行卡"; }
        if ("4".equals(value)){ return "一卡通"; }
        if ("5".equals(value)){ return "银行卡"; }
        if ("6".equals(value)){ return "pos机二维码"; }
        if ("7".equals(value)){ return "冲正"; }
        if ("8".equals(value)){ return "其他充值"; }
        return value;
    }

    public static String convertTxnType(String value){
        if ("0".equals(value)){ return "充值"; }
        if ("1".equals(value)){ return "提现"; }
        if ("2".equals(value)){ return "进场费"; }
        if ("3".equals(value)){ return "出场费"; }
        if ("4".equals(value)){ return "账单退款"; }
        if ("5".equals(value)){ return "交易"; }
        if ("6".equals(value)){ return "账单缴费"; }
        if ("7".equals(value)){ return "月卡缴费"; }
        if ("8".equals(value)){ return "供应商补贴发放"; }
        if ("9".equals(value)){ return "供应商补贴撤回"; }
        if ("10".equals(value)){ return "出场退费"; }
        if ("12".equals(value)){ return "出场退费代充值"; }
        if ("13".equals(value)){ return "月卡退费"; }
        if ("14".equals(value)){ return "采购商补贴发放"; }
        if ("15".equals(value)){ return "采购商补贴撤回"; }
        if ("16".equals(value)){ return "进场退费"; }
        if ("17".equals(value)){ return "冻结"; }
        if ("18".equals(value)){ return "解冻"; }
        if ("19".equals(value)){ return "调账"; }
        return value;
    }

    public static String convertCheckStatus(String value){
        if ("0".equals(value)){ return "待复核"; }
        if ("1".equals(value)){ return "已驳回"; }
        if ("2".equals(value)){ return "冲正完成"; }
        return value;
    }

    public static String convertFrazeStatus(String value){
        if ("1".equals(value)){ return "冻结"; }
        if ("10".equals(value)){ return "解冻"; }
        return value;
    }

    public static String convertReconRslt(String value){
        if ("0".equals(value)){ return "对账一致"; }
        if ("1".equals(value)){ return "对账不一致"; }
        return value;
    }

    public static String convertPayMethod(String value){
        if ("03".equals(value)){ return "银行卡支付"; }
        if ("05".equals(value)){ return "微信支付"; }
        if ("07".equals(value)){ return "惠氏宝支付"; }
        if ("08".equals(value)){ return "龙支付"; }
        return value;
    }

}
