package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 支付结果查询 请求Vo
 */
public class DISP20210098ReqVo implements Serializable {
    private static final long serialVersionUID = 5900618020390816429L;

    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    //发起发流水号

    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;

    // 市场编号
    @JSONField(name = "Mkt_Id")
    private String marketId;

    // 主订单ID
    @JSONField(name = "Main_Ordr_No")
    private String mainOrderId;

    // 支付流水号
    @JSONField(name = "Py_Trn_No")
    private String paymentTraceId;

    // 版本号
    @JSONField(name = "Vno")
    private String version;

    public String getSndDtTm() {
        return sndDtTm;
    }

    public void setSndDtTm(String sndDtTm) {
        this.sndDtTm = sndDtTm;
    }

    public String getSndTraceId() {
        return sndTraceId;
    }

    public void setSndTraceId(String sndTraceId) {
        this.sndTraceId = sndTraceId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getPaymentTraceId() {
        return paymentTraceId;
    }

    public void setPaymentTraceId(String paymentTraceId) {
        this.paymentTraceId = paymentTraceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
