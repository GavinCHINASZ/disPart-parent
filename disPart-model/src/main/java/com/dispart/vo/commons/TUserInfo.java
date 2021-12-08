package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (TUserInfo)实体类
 *
 * @author makejava
 * @since 2021-06-20 13:42:00
 */
public class TUserInfo implements Serializable {
    private static final long serialVersionUID = -68793691589586745L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 客户设备ID
     */
    private String clientId;
    /**
     * 用户姓名
     */
    private String userNm;
    /**
     * 用户昵称
     */
    private String userNickNm;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 用户密码
     */
    private String userPasswd;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 注册时间
     */
    private String regDt;
    /**
     * 用户状态
     */
    private String userSt;
    /**
     * 首次登陆
     */
    private String isFirstLand;
    /**
     * 登陆状态
     */
    private String loginSt;
    /**
     * 微信小程序ID
     */
    private String wxpayId;
    /**
     * 支付宝小程序ID
     */
    private String alipayId;
    /**
     * 更新时间戳
     */
    private String updateDt;

    /**
     * 商户编号
     */
    private String provId;

    /**
     * 渠道号
     */
    private String chanlNo;

    /**
     * 客户信息
     */
    private TCustomManagerInfo customInfo;

    public TCustomManagerInfo getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(TCustomManagerInfo customInfo) {
        this.customInfo = customInfo;
    }

    public String getChanlNo() {
        return chanlNo;
    }

    public void setChanlNo(String chanlNo) {
        this.chanlNo = chanlNo;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserNickNm() {
        return userNickNm;
    }

    public void setUserNickNm(String userNickNm) {
        this.userNickNm = userNickNm;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public String getUserSt() {
        return userSt;
    }

    public void setUserSt(String userSt) {
        this.userSt = userSt;
    }

    public String getIsFirstLand() {
        return isFirstLand;
    }

    public void setIsFirstLand(String isFirstLand) {
        this.isFirstLand = isFirstLand;
    }

    public String getLoginSt() {
        return loginSt;
    }

    public void setLoginSt(String loginSt) {
        this.loginSt = loginSt;
    }

    public String getWxpayId() {
        return wxpayId;
    }

    public void setWxpayId(String wxpayId) {
        this.wxpayId = wxpayId;
    }

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}