package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DISP20210294RespVo implements Serializable {
    private static final long serialVersionUID = -242072091763338270L;
    //交易状态
    @JSONField(name = "Svc_Rsp_St")
    private String txnSt;
    //错误码
    @JSONField(name = "Svc_Rsp_Cd")
    private String errCode;
    //错误描述
    @JSONField(name = "Rsp_Inf")
    private String errMsg;

    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    //发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;
    //客户方退款流水号
    @JSONField(name = "Cust_Rfnd_Trcno")
    private String customRefundTraceId;
    //退款流水号
    @JSONField(name = "Rfnd_Trcno")
    private String refundTraceId;
    //退款金额
    @JSONField(name = "Rfnd_Amt")
    private BigDecimal refundAmt;
    //退款结果
    @JSONField(name = "Refund_Rsp_St")
    private String refundSt;

}
