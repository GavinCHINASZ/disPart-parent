package com.dispart.vo.commons;

import java.io.Serializable;
import java.util.Date;

/**
 * (TCustomSignInfo)实体类
 *
 * @author makejava
 * @since 2021-06-13 23:11:13
 */
public class TCustomSignInfo implements Serializable {
    private static final long serialVersionUID = 919939534155114069L;
    private String merchantCode;
    /**
    * 商家编号
    */
    private String provId;
    /**
    * 商家名称
    */
    private String provNm;
    /**
    * pos编号
    */
    private String posNo;
    /**
    * 商家证件类型
    */
    private String provCertTp;
    /**
    * 商家证件号码
    */
    private String provCertNo;
    /**
    * 商家柜台编号
    */
    private String provCntNo;
    /**
    * 证件类型
    */
    private String certTp;
    /**
    * 证件号码
    */
    private String certNum;
    /**
    * 联系人
    */
    private String contacts;
    /**
    * 联系电话
    */
    private String telehone;
    /**
    * 商家自定义编号
    */
    private String provCustId;
    /**
    * 删除标识
    */
    private String delFlag;
    /**
    * 备注
    */
    private String remark;
    /**
    * 更新时间戳
    */
    private Date updateDt;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getProvNm() {
        return provNm;
    }

    public void setProvNm(String provNm) {
        this.provNm = provNm;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getProvCertTp() {
        return provCertTp;
    }

    public void setProvCertTp(String provCertTp) {
        this.provCertTp = provCertTp;
    }

    public String getProvCertNo() {
        return provCertNo;
    }

    public void setProvCertNo(String provCertNo) {
        this.provCertNo = provCertNo;
    }

    public String getProvCntNo() {
        return provCntNo;
    }

    public void setProvCntNo(String provCntNo) {
        this.provCntNo = provCntNo;
    }

    public String getCertTp() {
        return certTp;
    }

    public void setCertTp(String certTp) {
        this.certTp = certTp;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelehone() {
        return telehone;
    }

    public void setTelehone(String telehone) {
        this.telehone = telehone;
    }

    public String getProvCustId() {
        return provCustId;
    }

    public void setProvCustId(String provCustId) {
        this.provCustId = provCustId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

}