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

@ApiModel(value = "com-dispart-test-TProductInventoryInfo")
@Data
@TableName(value = "logistics.t_product_inventory_info")
public class TProductInventoryInfo {
    @TableId(value = "PRDCT_ID", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String prdctId;

    @TableId(value = "PROV_ID", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String provId;

    @TableField(value = "PRDCT_NM")
    @ApiModelProperty(value = "")
    private String prdctNm;

    @TableField(value = "STOCK")
    @ApiModelProperty(value = "")
    private BigDecimal stock;

    @TableField(value = "UNIT")
    @ApiModelProperty(value = "")
    private String unit;

    @TableField(value = "REMARK")
    @ApiModelProperty(value = "")
    private String remark;

    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value = "")
    private Date updateDt;

    /**
     * 货品种类
     */
    @TableField(value = "PRDCT_TYPE")
    @ApiModelProperty(value = "货品种类")
    private String prdctType;

    /**
     * 货品种类ID
     */
    @TableField(value = "PRDCT_TYPE_ID")
    @ApiModelProperty(value = "货品种类ID")
    private String prdctTypeId;

    public static final String COL_PRDCT_ID = "PRDCT_ID";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_PRDCT_NM = "PRDCT_NM";

    public static final String COL_STOCK = "STOCK";

    public static final String COL_UNIT = "UNIT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_UPDATE_DT = "UPDATE_DT";

    public static final String COL_PRDCT_TYPE = "PRDCT_TYPE";

    public static final String COL_PRDCT_TYPE_ID = "PRDCT_TYPE_ID";
}