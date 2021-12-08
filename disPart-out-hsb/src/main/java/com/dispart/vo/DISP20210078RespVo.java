package com.dispart.vo;

import java.io.Serializable;

/**
 * 惠市宝订单查询响应报文Vo
 */
public class DISP20210078RespVo implements Serializable {

    private static final long serialVersionUID = 6360725945067496073L;
    // 交易状态
    private String txnSt;
    // 错误码
    private String errCode;
    // 错误描述
    private String errMsg;
    // 主订单ID
    private String mainOrderId;
    // 支付流水号
    private String paymentTraceId;
    // 支付金额
    private String paymentAmt;
    // 订单生成时间
    private String orderCrtDtTm;
    // 订单超时时间
    private String orderTimeOut;
    // 订单状态
    private String orderSt;
    // 主订单编号
    private String mainOrderNo;

    public String getTxnSt() {
        return txnSt;
    }

    public void setTxnSt(String txnSt) {
        this.txnSt = txnSt;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

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

    public String getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(String paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getOrderCrtDtTm() {
        return orderCrtDtTm;
    }

    public void setOrderCrtDtTm(String orderCrtDtTm) {
        this.orderCrtDtTm = orderCrtDtTm;
    }

    public String getOrderTimeOut() {
        return orderTimeOut;
    }

    public void setOrderTimeOut(String orderTimeOut) {
        this.orderTimeOut = orderTimeOut;
    }

    public String getOrderSt() {
        return orderSt;
    }

    public void setOrderSt(String orderSt) {
        this.orderSt = orderSt;
    }

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }
}
