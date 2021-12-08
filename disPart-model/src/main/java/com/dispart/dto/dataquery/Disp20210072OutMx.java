package com.dispart.dto.dataquery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Disp20210072OutMx {

    /* 供应商ID */ private String provId;
    /* 供应商名称 */ private String provNm;
    /* 结算订单编号 */ private String paymentTraceId;
    /* 结算日期 */ private String txnDate;
    /* 结算金额 */ private BigDecimal txnAmt;
    /* 结算方式 */ private String payMethod;
    /* 结算状态 */ private String payeeSt;
    /* 对账结果 */ private String reconRslt;
    /* 对账说明 */ private String cause;
    /* 结算主订单编号 */ private String mainOrderId;

}
