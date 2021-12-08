package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TRoleInfo)实体类
 *
 * @author makejava
 * @since 2021-06-19 19:30:43
 */
public class TRoleInfo implements Serializable {
    private static final long serialVersionUID = -18508312269514339L;
    
    private String roleId;
    
    private String orgId;
    
    private String roleNm;
    
    private String remark;
    
    private Object updateDt;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleNm() {
        return roleNm;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}