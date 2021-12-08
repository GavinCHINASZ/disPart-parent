package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TMenuInfo)实体类
 *
 * @author makejava
 * @since 2021-06-19 20:00:16
 */
public class TMenuInfo implements Serializable {
    private static final long serialVersionUID = -76456863228436613L;
    /**
    * 菜单ID
    */
    private String menuId;
    /**
    * 上级菜单ID
    */
    private String parentMenuId;
    /**
    * 图标ID
    */
    private String iconId;
    /**
    * 菜单名称
    */
    private String menuNm;
    /**
    * 菜单类型
    */
    private String menuType;
    /**
    * 备注
    */
    private String remark;
    /**
    * 更新时间戳
    */
    private Object updateDt;
    /**
    * 菜单URL
    */
    private String menuUrl;
    /**
    * 接口URL
    */
    private String url;

    private String chanlNoType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getChanlNoType() {
        return chanlNoType;
    }

    public void setChanlNoType(String chanlNoType) {
        this.chanlNoType = chanlNoType;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
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

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}