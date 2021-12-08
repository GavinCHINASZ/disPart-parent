package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="com-dispart-model-order-TProductPriceInfo")
@Data
@TableName(value = "logistics.t_product_price_info")
public class TProductPriceInfo {
    @TableId(value = "DATE", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private Date date;

    @TableId(value = "PRDCT_ID", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String prdctId;

    @TableField(value = "PRDCT_NM")
    @ApiModelProperty(value="")
    private String prdctNm;

    @TableField(value = "MAX_PRICE")
    @ApiModelProperty(value="")
    private BigDecimal maxPrice;

    @TableField(value = "MIN_PRICE")
    @ApiModelProperty(value="")
    private BigDecimal minPrice;

    @TableField(value = "UNIT")
    @ApiModelProperty(value="")
    private String unit;

    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="")
    private Date updateDt;

    public static final String COL_DATE = "DATE";

    public static final String COL_PRDCT_ID = "PRDCT_ID";

    public static final String COL_PRDCT_NM = "PRDCT_NM";

    public static final String COL_MAX_PRICE = "MAX_PRICE";

    public static final String COL_MIN_PRICE = "MIN_PRICE";

    public static final String COL_UNIT = "UNIT";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}