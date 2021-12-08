package com.dispart.vo.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 商品库存表
    */
@Data
public class TProductInventoryInfoVo {
    /**
    * 商品编号
    */
    private String prdctId;

    /**
    * 供应商编号
    */
    private String provId;

    /**
    * 商品名称
    */
    private String prdctNm;

    /**
    * 库存数量
    */
    private BigDecimal stock;

    /**
    * 单位
    */
    private String unit;

    /**
    * 备注
    */
    private String remark;

    /**
    * 更新日期
    */
    private Date updateDt;

    /**
    * 货品种类
    */
    private String prdctType;

    /**
    * 货品种类ID
    */
    private String prdctTypeId;
}