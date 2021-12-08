package com.dispart.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 签约客户信息查询响应 响应给物流园的 Vo
 * DISP20210093RespVo
 */
public class DISP20210076RespVo implements Serializable {

    private static final long serialVersionUID = 7638488251927789048L;
    // 交易状态
    private String txnSt;
    // 错误码
    private String errCode;
    // 错误描述
    private String errMsg;

    //循环记录数
    private Integer recordNum;

    private ArrayList<CustomInfo> list;

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }

    public ArrayList<CustomInfo> getList() {
        return list;
    }

    public void setList(ArrayList<CustomInfo> list) {
        this.list = list;
    }

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

    public static class CustomInfo {
        // 删除标识
        private String delFlag;
        // 市场商家编号
        private String provId;
        // 市场商家名称
        private String provNm;
        // POS编号
        private String posNo;
        // 商家证件类型
        private String provCertId;
        // 商家证件号码
        private String provCertNo;
        // 商家柜台代码
        private String provCntNo;
        // 证件类型
        private String certType;
        // 证件号码
        private String certNum;
        // 联系人名称
        private String contacts;
        // 手机号码
        private String telephone;
        // 商家自定义编号
        private String provCustId;

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
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

        public String getCertType() {
            return certType;
        }

        public void setCertType(String certType) {
            this.certType = certType;
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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getProvCustId() {
            return provCustId;
        }

        public void setProvCustId(String provCustId) {
            this.provCustId = provCustId;
        }
    }
}
