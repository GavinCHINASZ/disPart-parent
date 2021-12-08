package com.dispart.vo;

import java.io.Serializable;

/**
 * 惠市宝签约客户信息变更  接受物流園响应报文Vo
 * DISP20210094RespVo
 */
public class DISP20210077RespVo implements Serializable {

    private static final long serialVersionUID = -6366251732472320146L;
    //服务状态码
    private String txnSt;
    //服务响应码
    private String errCode;
    //接受时间
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
