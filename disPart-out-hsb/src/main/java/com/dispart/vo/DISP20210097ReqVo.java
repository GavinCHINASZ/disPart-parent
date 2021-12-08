package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 惠市宝支付结果通知请求报文Vo
 */
public class DISP20210097ReqVo implements Serializable {
    private static final long serialVersionUID = 8717440104635783172L;

    // 主订单编号
    @JSONField(name = "Main_Ordr_No")
    private String mainOrderId;

    // 支付流水号
    @JSONField(name = "Py_Trn_No")
    private String paymentTraceId;
    // 订单金额
    @JSONField(name = "Ordr_Amt")
    private BigDecimal orderTotAmt;
    //支付金额
    @JSONField(name = "Txnamt")
    private BigDecimal paymentAmt;
    // 支付时间
    @JSONField(name = "Pay_Time")
    private String paymentDtTm;

    // 订单状态
    @JSONField(name = "Ordr_Stcd")
    private String orderSt;

    // 支付方式
    @JSONField(name = "TYPE")
    private String paymentMode;

    // 支付渠道
    @JSONField(name = "PAY_CHANNEL")
    private String payChannel;

    // 借贷标识
    @JSONField(name = "DEBIT_CREDIT_TYPE")
    private String debitCreditType;

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getPaymentTraceId() {
        return paymentTraceId;
    }

    public void setPaymentTraceId(String paymentTraceId) {
        this.paymentTraceId = paymentTraceId;
    }

    public BigDecimal getOrderTotAmt() {
        return orderTotAmt;
    }

    public void setOrderTotAmt(BigDecimal orderTotAmt) {
        this.orderTotAmt = orderTotAmt;
    }

    public BigDecimal getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(BigDecimal paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getPaymentDtTm() {
        return paymentDtTm;
    }

    public void setPaymentDtTm(String paymentDtTm) {
        this.paymentDtTm = paymentDtTm;
    }

    public String getOrderSt() {
        return orderSt;
    }

    public void setOrderSt(String orderSt) {
        this.orderSt = orderSt;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getDebitCreditType() {
        return debitCreditType;
    }

    public void setDebitCreditType(String debitCreditType) {
        this.debitCreditType = debitCreditType;
    }
}
