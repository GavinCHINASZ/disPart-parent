package com.dispart.vo;

import java.io.Serializable;

/**
 * 签约客户信息查询 接受物流園的请求Vo
 * DISP20210093ReqVo
 */
public class DISP20210076ReqVo implements Serializable {

    private static final long serialVersionUID = -9214475710576709826L;
    // 发起方时间戳
    private String sndDtTm;
    // 发起方流水号
    private String sndTraceId;
    // 开始日期
    private String startDt;
    // 结束日期
    private String endDt;
    // 市场编号
    private String marketId;
    // 删除标识
    private String delFlag;
    // 商家自定义编号
    private String provCustId;
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

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getProvCustId() {
        return provCustId;
    }

    public void setProvCustId(String provCustId) {
        this.provCustId = provCustId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
