package com.dispart.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ResultOrderCallBackVo {
    private String mainOrderId;
    private String paymentTraceId;
    private BigDecimal orderAmt;
    private BigDecimal paymentAmt;
    private String paymentDtTm;
    private String orderSt;
    private String paymentMode;
    private String paymengChnl;
    private String dbtCrdtTp;

}
