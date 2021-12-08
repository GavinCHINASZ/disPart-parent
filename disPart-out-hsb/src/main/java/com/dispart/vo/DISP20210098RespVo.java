package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 惠市宝订单查询响应报文Vo
 */
public class DISP20210098RespVo implements Serializable {
    private static final long serialVersionUID = -626472397224366845L;

    // 交易状态
    @JSONField(name = "Svc_Rsp_St")
    private String txnSt;
    // 错误码
    @JSONField(name = "Svc_Rsp_Cd")
    private String errCode;
    // 错误描述
    @JSONField(name = "Rsp_Inf")
    private String errMsg;
    // 主订单ID
    @JSONField(name = "Main_Ordr_No")
    private String mainOrderId;
    // 支付流水号
    @JSONField(name = "Py_Trn_No")
    private String paymentTraceId;
    // 支付金额
    @JSONField(name = "Txnamt")
    private String paymentAmt;
    // 订单生成时间
    @JSONField(name = "Ordr_Gen_Tm")
    private String orderCrtDtTm;
    // 订单超时时间
    @JSONField(name = "Ordr_Ovtm_Tm")
    private String orderTimeOut;
    // 订单状态
    @JSONField(name = "Ordr_Stcd")
    private String orderSt;
    // 主订单编号
    @JSONField(name = "Prim_Ordr_No")
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
