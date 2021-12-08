package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 惠市宝签约客户信息变更  响应报文Vo
 */
public class DISP20210094RespVo implements Serializable {

    private static final long serialVersionUID = 5443905978314371159L;

    // 交易状态
    @JSONField(name = "Svc_Rsp_St")
    private String txnSt;
    // 错误码
    @JSONField(name = "Svc_Rsp_Cd")
    private String errCode;
    // 错误描述
    @JSONField(name = "Rsp_Inf")
    private String errMsg;

    public String getTxnSt() {
        return txnSt;
    }

    public void setTxnSt(String txnSt) {
        this.txnSt = txnSt;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
