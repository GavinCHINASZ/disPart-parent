package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 签约客户信息查询响应 Vo
 */
public class DISP20210093RespVo implements Serializable {
    private static final long serialVersionUID = 926084063761769108L;

    // 交易状态
    @JSONField(name = "Svc_Rsp_St")
    private String txnSt;
    // 错误码
    @JSONField(name = "Svc_Rsp_Cd")
    private String errCode;
    // 错误描述
    @JSONField(name = "Rsp_Inf")
    private String errMsg;

    private String Sign_Inf;

    //循环记录数
    @JSONField(name = "Rvl_Rcrd_Num")
    private Integer recordNum;

    @JSONField(name = "List1")
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
        @JSONField(name = "Del_Idr")
        private String delFlag;
        // 市场商家编号
        @JSONField(name = "Mkt_Mrch_Id")
        private String provId;
        // 市场商家名称
        @JSONField(name = "Mkt_Mrch_Nm")
        private String provNm;
        // POS编号
        @JSONField(name = "Pos_No")
        private String posNo;
        // 商家证件类型
        @JSONField(name = "Mrch_Crdt_Tp")
        private String provCertId;
        // 商家证件号码
        @JSONField(name = "Mrch_Crdt_No")
        private String provCertNo;
        // 商家柜台代码
        @JSONField(name = "Mrch_Cnter_Cd")
        private String provCntNo;
        // 证件类型
        @JSONField(name = "Crdt_Tp")
        private String certType;
        // 证件号码
        @JSONField(name = "Crdt_No")
        private String certNum;
        // 联系人名称
        @JSONField(name = "Ctcpsn_Nm")
        private String contacts;
        // 手机号码
        @JSONField(name = "Mblph_No")
        private String telephone;
        // 商家自定义编号
        @JSONField(name = "Udf_Id")
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
