package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TMenuAuthInfo)实体类
 *
 * @author makejava
 * @since 2021-06-19 19:52:47
 */
public class TMenuAuthInfo implements Serializable {
    private static final long serialVersionUID = -19114316339177524L;
    /**
    * 权限ID
    */
    private Integer authId;
    /**
    * 菜单ID
    */
    private String menuId;
    /**
    * 备注
    */
    private String remark;
    /**
    * 更新时间戳
    */
    private Object updateDt;
    /**
    * 接口URL
    */
    private String url;


    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}