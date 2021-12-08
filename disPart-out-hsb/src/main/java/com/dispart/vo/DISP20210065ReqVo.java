package com.dispart.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 惠市宝支付结果通知请求报文Vo
 * DISP20210097ReqVo
 */
public class DISP20210065ReqVo implements Serializable {

    private static final long serialVersionUID = -179156126955817148L;
    // 主订单编号
    private String mainOrderId;
    // 支付流水号
    private String paymentTraceId;
    // 订单金额
    private BigDecimal orderTotAmt;
    //支付金额
    private BigDecimal paymentAmt;
    // 支付时间
    private String paymentDtTm;
    // 订单状态
    private String orderSt;
    // 支付方式
    private String paymentMode;
    // 支付渠道
    private String payChannel;
    // 借贷标识
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
