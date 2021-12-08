package com.dispart.vo.horn;



/**
 * @ Author     : zj
 * @ Date       : 2019/1/7 11:38
 * @ Description: 绑定参数模型
 */
public class BindModel  extends BaseModel{
    /**操作类型： bind*/
    private String action;
    /**设备ID*/
    private String deviceId;
    /**设备信息*/
    private String deviceInfo;
    /**操作人ID*/
    private String userId;
    /**绑定人描述信息*/
    private String userInfo;
    /** 绑定类型： 0 为解绑  1 为绑定*/
    private int bindType;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        String temp = new String(Base64.encodeBase64(deviceInfo.getBytes()));
        this.deviceInfo = temp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        String temp = new String(Base64.encodeBase64(userInfo.getBytes()));
        this.userInfo = temp;
    }

    public int getBindType() {
        return bindType;
    }

    public void setBindType(int bindType) {
        this.bindType = bindType;
    }

    @Override
    public String toString() {
        return "BindModel{" +
                "action='" + action + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", userId='" + userId + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", bindType=" + bindType +
                '}';
    }
}
