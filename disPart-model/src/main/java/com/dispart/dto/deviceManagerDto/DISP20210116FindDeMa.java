package com.dispart.dto.deviceManagerDto;

import lombok.Data;

@Data
public class DISP20210116FindDeMa {

    /* 客户类型 */ private String merchantType;
    /* 客户编码-客户表用 */ private String merchantCode;
    /* 客户名称 */ private String cname;
    /* 证件类型 */ private String certType;
    /* 证件号码 */ private String certNo;
    /* 联系电话 */ private String legalTel;

    /* 客户编码-设备表用 */ private String customerId;

    /* 设备类型 */ private String deviceType;
    /* 产品KEY */ private String deviceKey;
    /* 设备编号 */ private String deviceId;
    private String remark;
    private Integer pageSize;
    private Integer curPage;
}
