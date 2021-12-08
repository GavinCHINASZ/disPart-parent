package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DISP20210293OutVo implements Serializable {
    private static final long serialVersionUID = 1406000433480810675L;

    //交易状态
    private String txnSt;
    //错误码
    private String errCode;
    //错误描述
    private String errMsg;
    //接收时间
    private String rcvDtTm;
    //发起方时间戳
    private String sndDtTm;

}
