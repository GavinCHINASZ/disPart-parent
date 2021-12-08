package com.dispart.dto.orderdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WithDrawOrderByParam {

    private String sndDtTm;
    private String sndTraceId;
    private String marketId;
    private String paymentTraceId;
    private BigDecimal refundAmt;
    private String customRefundTraceId;
    private List<SubOrderListParams> list;

    private String subOrderNo;
    private BigDecimal txnTotAmt;
    private String paymentSt;

    private String refNum;

    private String refundTraceId;
    private String refundSt;

}
