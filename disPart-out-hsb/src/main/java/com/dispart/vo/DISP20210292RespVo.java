package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class DISP20210292RespVo implements Serializable {

    private static final long serialVersionUID = -8125199602914971563L;
    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;

    //发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;

    //退款流水号
    @JSONField(name = "Refund_JrnNo")
    private String refundTraceId;

    //客户方退款流水号
    @JSONField(name = "Cust_Rfnd_Trcno")
    private String customRefundTraceId;

    //退款结果
    @JSONField(name = "Refund_Rsp_St")
    private String refundSt;

    //退款响应信息
    @JSONField(name = "Refund_Rsp_Inf")
    private String refundMsg;

    // 交易状态
    @JSONField(name = "Svc_Rsp_St")
    private String txnSt;

    // 错误码
    @JSONField(name = "Svc_Rsp_Cd")
    private String errCode;

    // 错误描述
    @JSONField(name = "Rsp_Inf")
    private String errMsg;

}
