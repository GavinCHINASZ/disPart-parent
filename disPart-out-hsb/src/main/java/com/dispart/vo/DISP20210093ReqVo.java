package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 签约客户信息查询 请求Vo
 */
public class DISP20210093ReqVo implements Serializable {
    private static final long serialVersionUID = -5398008408848472289L;

    // 发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    // 发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;
    // 开始日期
    @JSONField(name = "Stdt")
    private String startDt;
    // 结束日期
    @JSONField(name = "Eddt")
    private String endDt;
    // 市场编号
    @JSONField(name = "Mkt_Id")
    private String marketId;
    // 删除标识
    @JSONField(name = "Del_Idr")
    private String delFlag;
    // 商家自定义编号
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
