package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 惠市宝签约客户信息变更 请求报文Vo
 */
public class DISP20210094ReqVo implements Serializable {

    private static final long serialVersionUID = -3119198922694259370L;

    // 发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    // 操作类型
    @JSONField(name = "Opr_Tp")
    private String operType;
    // 商家编号
    @JSONField(name = "Mkt_Mrch_Id")
    private String provId;

    // 商家名称
    @JSONField(name = "Mkt_Mrch_Nm")
    private String provNm;

    // POS编号
    @JSONField(name = "Pos_No")
    private String posNo;

    // 商家证件类型 01-组织结构代码 02-营业执照 03-其他 04-统一社会信用代码
    @JSONField(name = "Mrch_Crdt_Tp")
    private String provCertId;

    // 商家证件号码
    @JSONField(name = "Mrch_Crdt_No")
    private String provCertNo;

    // 商家柜台代码
    @JSONField(name = "Mrch_Cnter_Cd")
    private String provCntNo;

    // 联系人证件类型
    @JSONField(name = "Crdt_Tp")
    private String certType;

    // 联系人名称
    @JSONField(name = "Ctcpsn_Nm")
    private String contacts;

    // 联系人证件号
    @JSONField(name = "Crdt_No")
    private String certNum;

    // 联系人手机号
    @JSONField(name = "Mblph_No")
    private String telephone;

    // 商家自定义编号
    @JSONField(name = "Udf_Id")
    private String provCustId;

    public String getSndDtTm() {
        return sndDtTm;
    }

    public void setSndDtTm(String sndDtTm) {
        this.sndDtTm = sndDtTm;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
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

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getCertNum() {
        return certNum;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getProvCertId() {
        return provCertId;
    }

    public void setProvCertId(String provCertId) {
        this.provCertId = provCertId;
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

    public String getProvCustId() {
        return provCustId;
    }

    public void setProvCustId(String provCustId) {
        this.provCustId = provCustId;
    }
}
