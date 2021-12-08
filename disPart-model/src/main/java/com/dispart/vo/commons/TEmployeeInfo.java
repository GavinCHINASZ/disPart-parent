package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TEmployeeInfo)实体类
 *
 * @author makejava
 * @since 2021-06-20 15:07:32
 */
public class TEmployeeInfo implements Serializable {
    private static final long serialVersionUID = 601320137693815139L;
    /**
    * 员工ID
    */
    private String empId;
    /**
    * 登陆账号
    */
    private String loginAcct;
    /**
    * 员工姓名
    */
    private String empNm;
    /**
    * 性别
    */
    private String sex;
    /**
    * 证件类型
    */
    private String certType;
    /**
    * 证件号码
    */
    private String certNum;
    /**
    * 电话
    */
    private String telephone;
    /**
    * 电子邮箱
    */
    private String email;
    /**
    * 邮编
    */
    private String postcode;
    /**
    * 所属部门
    */
    private String subDep;
    /**
    * 所属机构
    */
    private String subOrg;
    /**
    * 备注
    */
    private String remark;
    /**
    * 生效日期
    */
    private Object effeDt;
    /**
    * 失效日期
    */
    private Object expDt;
    /**
    * 密码
    */
    private String passwd;
    /**
    * 更新密码时间
    */
    private Object updatePasswdDtTm;
    /**
    * 员工状态
    */
    private String empSt;
    /**
    * 操作员
    */
    private String operId;
    /**
    * 时间戳
    */
    private Object updateDt;


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getSubDep() {
        return subDep;
    }

    public void setSubDep(String subDep) {
        this.subDep = subDep;
    }

    public String getSubOrg() {
        return subOrg;
    }

    public void setSubOrg(String subOrg) {
        this.subOrg = subOrg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getEffeDt() {
        return effeDt;
    }

    public void setEffeDt(Object effeDt) {
        this.effeDt = effeDt;
    }

    public Object getExpDt() {
        return expDt;
    }

    public void setExpDt(Object expDt) {
        this.expDt = expDt;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Object getUpdatePasswdDtTm() {
        return updatePasswdDtTm;
    }

    public void setUpdatePasswdDtTm(Object updatePasswdDtTm) {
        this.updatePasswdDtTm = updatePasswdDtTm;
    }

    public String getEmpSt() {
        return empSt;
    }

    public void setEmpSt(String empSt) {
        this.empSt = empSt;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}