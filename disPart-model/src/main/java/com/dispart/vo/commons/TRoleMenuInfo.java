package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TRoleMenuInfo)实体类
 *
 * @author makejava
 * @since 2021-06-19 19:46:03
 */
public class TRoleMenuInfo implements Serializable {
    private static final long serialVersionUID = -65377702018283395L;
    
    private String roleId;
    
    private String menuId;
    
    private String remark;
    
    private String dataParm;
    
    private Object updateDt;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataParm() {
        return dataParm;
    }

    public void setDataParm(String dataParm) {
        this.dataParm = dataParm;
    }

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}