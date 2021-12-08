package com.dispart.dto.dataquery;

import lombok.Data;

@Data
public class Disp20210074InDto {

    /* 客户类型 */ private String merchantType;  //0,采购商; 1，供应商
    /* 客户编码 */ private String merchantCode;
    /* 客户名称 */ private String cname;
    /* 证件类型 */ private String certType;
    /* 证件号码 */ private String certNo;
    /* 法人电话 */ private String legalTel;
    /* 联系电话 */ private String telNo;
    /* 是否实名 */ private String isRealName;
    /* 状态 */ private String status;

    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;

}
