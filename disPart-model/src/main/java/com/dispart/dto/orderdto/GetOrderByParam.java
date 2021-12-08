package com.dispart.dto.orderdto;

import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOrderByParam extends InsertPayJrnDTO {

    private String sndDtTm;
    private String sndTraceId;
    private String version;
    private String marketId;
    private String mainOrderId;
    private String paymentMode;
    private String orderType;
    private String ccyCd;
    private String appId;
    private String userIdent;
    private String confirmDt;
    private BigDecimal orderTotAmt;
    private String bankCd;
    private Integer periodsNum;
    private BigDecimal txnTotAmt;
    private List list;

    private String subOrderNo;
    private String paymentTraceId;
//    private BigDecimal txnTotAmt;
    private String paymentSt;

    private String refNum;
    private String refundTraceId;
    private String refundSt;

    private String orderSt;



}
