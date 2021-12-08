package com.dispart.dto.prdctPriceDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DISP20210312InDto {

    /**
     * 日期
     */
    @ApiModelProperty(value="日期")
    private String date;

    /**
     * 商品名称
     */
    @ApiModelProperty(value="商品名称")
    private String prdctNm;

    /**
     * 商品ID
     */
    @ApiModelProperty(value="商品ID")
    private String prdctId;

    /**
     * 最高价
     */
    @ApiModelProperty(value="最高价")
    private BigDecimal maxPrice;

    /**
     * 中间价
     */
    @ApiModelProperty(value="中间价")
    private BigDecimal midPrice;

    /**
     * 最低价
     */
    @ApiModelProperty(value="最低价")
    private BigDecimal minPrice;

    /**
     * 单位
     */
    @ApiModelProperty(value="单位")
    private String unit;

    /**
     * 时间戳
     */
    @ApiModelProperty(value="时间戳")
    private String updateDt;

    /**
     * 操作人ID
     */
    @ApiModelProperty(value="操作人ID")
    private String operId;

}
