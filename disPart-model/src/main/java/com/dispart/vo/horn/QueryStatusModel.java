package com.dispart.vo.horn;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/7 11:40
 * @ Description:
 */
public class QueryStatusModel  extends BaseModel{
    /** 操作类型： queryStatus*/
    private String action;
    private String deviceId;
    /**产品KEY*/
    private String productKey;

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


}
