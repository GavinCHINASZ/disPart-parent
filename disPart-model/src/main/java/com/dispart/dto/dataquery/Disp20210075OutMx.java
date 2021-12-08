package com.dispart.dto.dataquery;

import lombok.Data;

@Data
public class Disp20210075OutMx {

    /* 商品ID */ private String prdctId;
    /* 货品种类名称 */ private String prdctNm;
    /* 货品库存量 */ private Integer stock;
    /* 进货时间 */ private String updateDt;
    /* 客户编号 */ private String provId;
    /* 客户名称 */ private String provNm;
    /* 单位 */ private String unit;
}
