package com.dispart.vo.horn;

import java.util.UUID;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 11:54
 * @ Description:
 */
public class BaseModel {
    private String accessId;
    private String timestamp;
    private String signatureVer;
    private String signatureRand;

    public BaseModel(){
        signatureVer = "1";
        timestamp = String.valueOf(System.currentTimeMillis());
        signatureRand = UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignatureVer() {
        return signatureVer;
    }

    public void setSignatureVer(String signatureVer) {
        this.signatureVer = signatureVer;
    }

    public String getSignatureRand() {
        return signatureRand;
    }

    public void setSignatureRand(String signatureRand) {
        this.signatureRand = signatureRand;
    }
}
