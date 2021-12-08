package com.dispart.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class ResultHSBCallBack {

    private BigDecimal additAmt;

    private String errCode;
    private String errMsg;

    private String ccbMainOrderNo;
    private String mainOrderNo;
    private String orderCrtDtTm;

    private ArrayList<ResultHSBZiOrder> orderLists;
    private String orderSt;
    private String orderTimeout;
    private String paymentTraceId;
    private String paymentURL;
    private String sndDtTm;
    private String sndTraceId;
    private String txnSt;

    private String payQrCode;
    private String returnParmData;
    private String cashDeskURL;

    private String appId;
    private String userIdent;

    private String bankCd;
    private Integer periodsNum;

    private String refundTraceId;
    private String customRefundTraceId;

    private String refundSt;
    private String refundMsg;

    private String creditNo;

}
