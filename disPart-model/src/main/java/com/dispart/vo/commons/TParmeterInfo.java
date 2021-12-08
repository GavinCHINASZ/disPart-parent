package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TParmeterInfo)实体类
 *
 * @author makejava
 * @since 2021-06-13 20:45:29
 */
public class TParmeterInfo implements Serializable {
    private static final long serialVersionUID = 300144674667181441L;
    /**
    * 参数类别
    */
    private String paramType;
    /**
    * 参数名称
    */
    private String paramNm;
    /**
    * 参数值
    */
    private String paramVal;
    /**
    * 参数描述
    */
    private String paramDesc;
    /**
    * 备注
    */
    private String remark;
    /**
    * 查询权限 0-不允许界面查询 1-允许界面查询
    */
    private String permission;
    /**
    * 时间戳
    */
    private Object updateDt;


    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamNm() {
        return paramNm;
    }

    public void setParamNm(String paramNm) {
        this.paramNm = paramNm;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}