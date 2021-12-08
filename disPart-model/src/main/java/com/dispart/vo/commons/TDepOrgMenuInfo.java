package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TDepOrgMenuInfo)实体类
 *
 * @author makejava
 * @since 2021-06-19 20:23:22
 */
public class TDepOrgMenuInfo implements Serializable {
    private static final long serialVersionUID = -56289396424771890L;
    /**
    * 部门/机构ID
    */
    private String id;
    /**
    * 菜单ID
    */
    private String menuId;
    /**
    * 备注
    */
    private String remark;
    
    private Object updateDt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Object getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Object updateDt) {
        this.updateDt = updateDt;
    }

}