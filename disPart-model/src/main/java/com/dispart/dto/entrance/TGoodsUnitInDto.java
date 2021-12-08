package com.dispart.dto.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 商品计量单位表
    */
@Data
public class TGoodsUnitInDto {

    /**
     * 单位配置id
     */
    private Integer unitId;

    /**
    * 客户编号
    */
    private String provId;

    /**
    * 商品编号
    */
    private String prdctId;

    /**
    * 计量单位 箱/件/个
    */
    private String unit;

    /**
    * 备注
    */
    private String remark;
    /**
     * 每个单位对应的重量
     */
    private BigDecimal weight;

}