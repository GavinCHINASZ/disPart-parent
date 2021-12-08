package com.dispart.vo.horn;

//import com.yun.api.sdk.utils.base.Base64;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/7 11:42
 * @ Description:
 */
public class TerminalPlayModel extends BaseModel{
    /**操作类型： play*/
    private String action;
    /**设备ID*/
    private String deviceId;
    /**音量大小 范围： 0~100*/
    private int volume;
    /**播报金额*/
    private String amount;
    /**播报类型*/
    private String template;
    /**播报流水号*/
    private String traceId;
    /**播报交易信息*/
    private String traceInfo;
    /**超时时间*/
    private int timeout;
    /**产品KEY*/
    private String productKey;

    /**请求标识，表示唯一*/
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        String temp = new String(Base64.encodeBase64(template.getBytes()));
        this.template = temp;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceInfo() {
        return traceInfo;
    }

    public void setTraceInfo(String traceInfo) {
        String temp = new String(Base64.encodeBase64(traceInfo.getBytes()));
        this.traceInfo = temp;
    }

    @Override
    public String toString() {
        return "TerminalPlayModel{" +
                "action='" + action + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", volume=" + volume +
                ", amount='" + amount + '\'' +
                ", template='" + template + '\'' +
                ", traceId='" + traceId + '\'' +
                ", traceInfo='" + traceInfo + '\'' +
                '}';
    }
}
