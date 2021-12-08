package com.dispart.vo;

import java.io.Serializable;

public class DISP20210065RespVo implements Serializable {

    private static final long serialVersionUID = -4576870607344189642L;
    // 交易状态
    private String txnSt;
    // 错误码
    private String errCode;
    // 错误描述
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
