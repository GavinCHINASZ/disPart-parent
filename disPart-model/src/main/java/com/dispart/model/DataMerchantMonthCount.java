package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 商户月交易统计
 */
@ApiModel(value = "com-dispart-model-DataMerchantMonthCount")
@Data
public class DataMerchantMonthCount {
    @ApiModelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "")
    private String code;

    /**
     * 统计月份
     */
    @ApiModelProperty(value = "统计月份")
    private String countMonth;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal transAmount;
}