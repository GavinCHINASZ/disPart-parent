package com.dispart.vo;

import java.io.Serializable;

/**
 * 支付结果查询 请求Vo
 * DISP20210098ReqVo
 */
public class DISP20210078ReqVo implements Serializable {
    private static final long serialVersionUID = 8207049806056347486L;
    //发起方时间戳
    private String sndDtTm;
    //发起发流水号
    private String sndTraceId;

    // 市场编号
    private String marketId;

    // 主订单ID
    private String mainOrderId;

    // 支付流水号
    private String paymentTraceId;

    // 版本号
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
