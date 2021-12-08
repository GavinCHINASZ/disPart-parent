package com.dispart.dto.dataquery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Disp20210071OutMx {

    /* 供应商编号 */ private String provId;
    /* 供应商名称(分账客户名称) */ private String provNm;
    /* 支付流水号 */ private String paymentTraceId;
    /* 结算日期(分账日期) */ private String txnDate;
    /* 交易金额 */ private BigDecimal txnAmt;
    /* 结算方式(分账方式) */ private String payMethod;
    /* 分账状态代码 */ private String respSt;
    /* 惠市宝生成的子订单ID(分账订单号) */ private String subOrderId;
    /* 业务订单号 */ private String orderId;
    /* 业务订单额 */ private String prdctAmt;
    /* 分账金额 */ private BigDecimal partAmt;
    /* 手续费 */ private BigDecimal servChrg;
    /* 分账方式 */ private String partModeNm;
}
