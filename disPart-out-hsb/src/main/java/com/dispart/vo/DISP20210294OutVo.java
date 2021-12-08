package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DISP20210294OutVo implements Serializable {
    private static final long serialVersionUID = 7185021294990707391L;

    //交易状态
    private String txnSt;

    //错误码
    private String errCode;

    //错误描述
    private String errMsg;

    //发起方时间戳
    private String sndDtTm;

    //发起方流水号
    private String sndTraceId;

    //客户方退款流水号
    private String customRefundTraceId;

    //退款流水号
    private String refundTraceId;

    //退款金额
    private BigDecimal refundAmt;

    //退款结果
    private String refundSt;

}
