package com.dispart.request;

public class RequestHead {
    private String versionNo;
    private String reqStamp;
    private String reqDate;
    private String reqSeqNo;
    private String chanlNo; //渠道号 01-贵农APP 02-微信小程序 03-支付宝小程序 04-农皮系统 05-智慧贵农
    private String operator; //操作人编号
    private String operName;  //操作人名称
    private String depId;    //部门编号
    private String orgCode;  //机构编号
    private String roleType; //角色编号
    private String userNo;

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getReqStamp() {
        return reqStamp;
    }

    public void setReqStamp(String reqStamp) {
        this.reqStamp = reqStamp;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqSeqNo() {
        return reqSeqNo;
    }

    public void setReqSeqNo(String reqSeqNo) {
        this.reqSeqNo = reqSeqNo;
    }

    public String getChanlNo() {
        return chanlNo;
    }

    public void setChanlNo(String chanlNo) {
        this.chanlNo = chanlNo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
