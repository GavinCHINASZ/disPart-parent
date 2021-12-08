package com.dispart.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 惠市宝支付下单响应报文Vo
 * DISP20210096RespVo
 */
public class DISP20210061RespVo implements Serializable {
    private static final long serialVersionUID = -6640144937415813082L;
    // 交易状态
    private String txnSt;
    // 错误码
    private String errCode;
    // 错误描述
    private String errMsg;
    // 发起方时间戳
    private String sndDtTm;
    // 发起方流水号
    private String sndTraceId;
    // 主订单编号
    private String mainOrderNo;
    // 支付流水号
    private String paymentTraceId;
    // 订单编号
    private String ccbMainOrderNo;
    // 订单生成时间
    private String orderCrtDtTm;
    // 订单超时时间
    private String orderTimeout;
    // 收银台URL
    private String cashDeskURL;
    // 支付URL
    private String paymentURL;
    // 支付二维码串
    private String payQrCode;
    // 返回参数数据
    private String returnParmData;
    // 等待时间
    private String waitTm;
    // 订单状态
    private String orderSt;
    // 子订单列表
    private ArrayList<OrderList> orderLists;

    public static class OrderList {
        // 客户方子订单编号
        private String subOrderId;
        // 子订单编号
        private String ccbSubOrderNo;

        public String getSubOrderId() {
            return subOrderId;
        }

        public void setSubOrderId(String subOrderId) {
            this.subOrderId = subOrderId;
        }

        public String getCcbSubOrderNo() {
            return ccbSubOrderNo;
        }

        public void setCcbSubOrderNo(String ccbSubOrderNo) {
            this.ccbSubOrderNo = ccbSubOrderNo;
        }
    }

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

    public String getSndDtTm() {
        return sndDtTm;
    }

    public void setSndDtTm(String sndDtTm) {
        this.sndDtTm = sndDtTm;
    }

    public String getSndTraceId() {
        return sndTraceId;
    }

    public void setSndTraceId(String sndTraceId) {
        this.sndTraceId = sndTraceId;
    }

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }

    public String getPaymentTraceId() {
        return paymentTraceId;
    }

    public void setPaymentTraceId(String paymentTraceId) {
        this.paymentTraceId = paymentTraceId;
    }

    public String getCcbMainOrderNo() {
        return ccbMainOrderNo;
    }

    public void setCcbMainOrderNo(String ccbMainOrderNo) {
        this.ccbMainOrderNo = ccbMainOrderNo;
    }

    public String getOrderCrtDtTm() {
        return orderCrtDtTm;
    }

    public void setOrderCrtDtTm(String orderCrtDtTm) {
        this.orderCrtDtTm = orderCrtDtTm;
    }

    public String getOrderTimeout() {
        return orderTimeout;
    }

    public void setOrderTimeout(String orderTimeout) {
        this.orderTimeout = orderTimeout;
    }

    public String getCashDeskURL() {
        return cashDeskURL;
    }

    public void setCashDeskURL(String cashDeskURL) {
        this.cashDeskURL = cashDeskURL;
    }

    public String getPaymentURL() {
        return paymentURL;
    }

    public void setPaymentURL(String paymentURL) {
        this.paymentURL = paymentURL;
    }

    public String getPayQrCode() {
        return payQrCode;
    }

    public void setPayQrCode(String payQrCode) {
        this.payQrCode = payQrCode;
    }

    public String getReturnParmData() {
        return returnParmData;
    }

    public void setReturnParmData(String returnParmData) {
        this.returnParmData = returnParmData;
    }

    public String getWaitTm() {
        return waitTm;
    }

    public void setWaitTm(String waitTm) {
        this.waitTm = waitTm;
    }

    public String getOrderSt() {
        return orderSt;
    }

    public void setOrderSt(String orderSt) {
        this.orderSt = orderSt;
    }

    public ArrayList<OrderList> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(ArrayList<OrderList> orderLists) {
        this.orderLists = orderLists;
    }
}
