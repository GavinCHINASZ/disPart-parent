package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DISP20210293ReqVo implements Serializable {

    private static final long serialVersionUID = 4176799161200115948L;
    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    //发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;
    //支付流水号
    @JSONField(name = "Py_Trn_No")
    private String paymentTraceId;
    //客户方退款流水号
    @JSONField(name = "Cust_Rfnd_Trcno")
    private String customRefundTraceId;
    //退款流水号
    @JSONField(name = "Super_Refund_No")
    private String refundTraceId;
    //退款金额
    @JSONField(name = "Rfnd_Amt")
    private BigDecimal refundAmt;
    //退款结果
    @JSONField(name = "Refund_Rsp_St")
    private String refundSt;
    //退款响应信息
    @JSONField(name = "Refund_Rsp_Inf")
    private String refundMsg;

}
