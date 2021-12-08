package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class DISP20210293RespVo implements Serializable {

    private static final long serialVersionUID = 7030116616092909444L;
    //交易状态
    @JSONField(name = "Svc_Rsp_St")
    private String txnSt;
    //错误码
    @JSONField(name = "Svc_Rsp_Cd")
    private String errCode;
    //错误描述
    @JSONField(name = "Rsp_Inf")
    private String errMsg;

    //接收时间
    @JSONField(name = "Rcv_Tm")
    private String rcvDtTm;
    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;

}
