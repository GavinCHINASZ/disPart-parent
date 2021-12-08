package com.dispart.vo;

import java.io.Serializable;

public class DISP20210044RespVo implements Serializable {
    private static final long serialVersionUID = -5118732182199542828L;

    // 参数类型
    private String paramType;
    // 参数名称
    private String paramNm;
    // 参数值
    private String paramVal;
    // 参数描述
    private String paramDesc;

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

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }
}
