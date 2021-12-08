package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DISP20210293InVo implements Serializable {
    private static final long serialVersionUID = 1551416102736333327L;

    //发起方时间戳
    private String sndDtTm;
    //发起方流水号
    private String sndTraceId;
    //支付流水号
    private String paymentTraceId;
    //客户方退款流水号
    private String customRefundTraceId;
    //退款流水号
    private String refundTraceId;
    //退款金额
    private BigDecimal refundAmt;
    //退款结果
    private String refundSt;
    //退款响应信息
    private String refundMsg;

}
