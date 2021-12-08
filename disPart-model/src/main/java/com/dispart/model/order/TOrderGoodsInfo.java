package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 订单商品明细
 */
@ApiModel(value="com-dispart-tmp-TOrderGoodsInfo")
@Data
@TableName(value = "logistics.t_order_goods_info")
public class TOrderGoodsInfo {
    /**
     * 订单编号
     */
    @TableId(value = "ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="订单编号")
    private String orderId;

    @TableId(value = "PRDCT_ID", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String prdctId;

    /**
     * 商品类型
     */
    @TableField(value = "PRDCT_TYPE")
    @ApiModelProperty(value="商品类型")
    private String prdctType;

    /**
     * 商品名称
     */
    @TableField(value = "PRDCT_NM")
    @ApiModelProperty(value="商品名称")
    private String prdctNm;

    /**
     * 商品单价
     */
    @TableField(value = "PRDCT_PRICE")
    @ApiModelProperty(value="商品单价")
    private BigDecimal prdctPrice;

    /**
     * 商品重量
     */
    @TableField(value = "PRDCT_WEIGHT")
    @ApiModelProperty(value="商品重量")
    private BigDecimal prdctWeight;

    /**
     * 商品数量
     */
    @TableField(value = "PRDCT_NUM")
    @ApiModelProperty(value="商品数量")
    private BigDecimal prdctNum;

    /**
     * 单位
     */
    @TableField(value = "PRDCT_UNIT")
    @ApiModelProperty(value="单位")
    private String prdctUnit;

    /**
     * 商品总价
     */
    @TableField(value = "PRDCT_AMT")
    @ApiModelProperty(value="商品总价")
    private BigDecimal prdctAmt;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "CREAT_TIME")
    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @TableField(value = "UP_TIME")
    @ApiModelProperty(value="更新时间")
    private Date upTime;

    public static final String COL_ORDER_ID = "ORDER_ID";

    public static final String COL_PRDCT_ID = "PRDCT_ID";

    public static final String COL_PRDCT_TYPE = "PRDCT_TYPE";

    public static final String COL_PRDCT_NM = "PRDCT_NM";

    public static final String COL_PRDCT_PRICE = "PRDCT_PRICE";

    public static final String COL_PRDCT_NUM = "PRDCT_NUM";

    public static final String COL_PRDCT_UNIT = "PRDCT_UNIT";

    public static final String COL_PRDCT_AMT = "PRDCT_AMT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}