package com.dispart.vo.dataQuery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Disp20210353OutVo {

    private String txnCount;  //交易单数

    private BigDecimal txnAmt;  //交易总金额

    private BigDecimal commission;  //手续费

    private BigDecimal finalAmt;  //结算金额

    private String paymentMode;  //支付方式 03-银行卡支付，05-微信支付，07-惠市宝支付

}
