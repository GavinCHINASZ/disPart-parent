package com.dispart.dto.dataquery;

import lombok.Data;

@Data
public class Disp20210074OutMx {

    /* 客户类型 */ private String merchantType;
    /* 客户编码 */ private String merchantCode;
    /* 客户名称 */ private String cname;
    /* 证件类型 */ private String certType;
    /* 证件号码 */ private String certNo;
    /* 法人电话 */ private String legalTel;
    /* 联系电话 */ private String telNo;
    /* 是否实名 */ private String isRealName;
    /* 状态 */ private String status;
    /*二维码地址*/private String qrcodeUrl;
}
