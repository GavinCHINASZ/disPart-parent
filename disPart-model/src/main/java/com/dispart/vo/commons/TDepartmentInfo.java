package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TDepartmentInfo)实体类
 *
 * @author makejava
 * @since 2021-06-19 20:18:09
 */
public class TDepartmentInfo implements Serializable {
    private static final long serialVersionUID = 206433778976495433L;
    /**
    * 部门ID
    */
    private String depId;
    /**
    * 上级部门ID
    */
    private String parentDepId;
    /**
    * 部门名称
    */
    private String depNm;
    /**
    * 部门简称
    */
    private String depShrtNm;
    /**
    * 部门状态
    */
    private String depSt;
    /**
    * 所属机构
    */
    private String subOrg;
    /**
    * 备注
    */
    private String remark;
    /**
    * 操作员
    */
    private String operId;
    /**
    * 创建日期
    */
    private Object creatDt;
    /**
    * 时间错
    */
    private Object updateDt;


    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getParentDepId() {
        return parentDepId;
    }

    public void setParentDepId(String parentDepId) {
        this.parentDepId = parentDepId;
    }

    public String getDepNm() {
        return depNm;
    }

    public void setDepNm(String depNm) {
        this.depNm = depNm;
    }

    public String getDepShrtNm() {
        return depShrtNm;
    }

    public void setDepShrtNm(String depShrtNm) {
        this.depShrtNm = depShrtNm;
    }

    public String getDepSt() {
        return depSt;
    }

    public void setDepSt(String depSt) {
        this.depSt = depSt;
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

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public Object getCreatDt() {
        return creatDt;
    }

    public void setCreatDt(Object creatDt) {
        this.creatDt = creatDt;
    }

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}