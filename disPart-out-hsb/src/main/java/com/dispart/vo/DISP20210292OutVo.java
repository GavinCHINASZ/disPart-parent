package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DISP20210292OutVo implements Serializable {

    private static final long serialVersionUID = 8301001876736436165L;
    //发起方时间戳
    private String sndDtTm;

    //发起方流水号
    private String sndTraceId;

    //退款流水号
    private String refundTraceId;

    //客户方退款流水号
    private String customRefundTraceId;

    //退款结果
    private String refundSt;

    //退款响应信息
    private String refundMsg;

    // 交易状态
    private String txnSt;

    // 错误码
    private String errCode;

    // 错误描述
    private String errMsg;
}
