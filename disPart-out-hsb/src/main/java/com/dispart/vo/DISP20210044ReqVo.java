package com.dispart.vo;

import java.io.Serializable;

public class DISP20210044ReqVo implements Serializable {
    private static final long serialVersionUID = 1682489154188330714L;

    // 参数类型
    private String paramType;
    // 参数名称
    private String paramNm;

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamNm() {
        return paramNm;
    }

    public void setParamNm(String paramNm) {
        this.paramNm = paramNm;
    }
}
