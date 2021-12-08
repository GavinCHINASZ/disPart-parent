package com.dispart.dto.orderdto;

import lombok.Data;

@Data
public class ReturnOrderResultDTO {

    private String mainOrderId;
    private String mainOrderNo;
    private String orderCrtDtTm;
    private String orderSt;
    private String orderTimeOut;
    private String paymentAmt;
    private String paymentTraceId;
    private String txnSt;
    private String errCode;
    private String errMsg;
}
