package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TTraceId)实体类
 *
 * @author makejava
 * @since 2021-06-20 18:32:13
 */
public class TTraceId implements Serializable {
    private static final long serialVersionUID = 715889632186022966L;
    /**
    * 交易日期
    */
    private Object txnDt;
    /**
    * 交易码
    */
    private String txnCode;
    /**
    * 交易流水号
    */
    private String txnTraceId;
    /**
    * 交易渠道类型
    */
    private String chanlType;
    /**
    * 操作员
    */
    private String operId;
    /**
    * 请求报文
    */
    private String reqJson;
    /**
    * 备注
    */
    private String remakr;
    /**
    * 更新时间戳
    */
    private Object updateDt;


    public Object getTxnDt() {
        return txnDt;
    }

    public void setTxnDt(Object txnDt) {
        this.txnDt = txnDt;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getTxnTraceId() {
        return txnTraceId;
    }

    public void setTxnTraceId(String txnTraceId) {
        this.txnTraceId = txnTraceId;
    }

    public String getChanlType() {
        return chanlType;
    }

    public void setChanlType(String chanlType) {
        this.chanlType = chanlType;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getReqJson() {
        return reqJson;
    }

    public void setReqJson(String reqJson) {
        this.reqJson = reqJson;
    }

    public String getRemakr() {
        return remakr;
    }

    public void setRemakr(String remakr) {
        this.remakr = remakr;
    }

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}