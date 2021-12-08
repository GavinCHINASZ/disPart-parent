package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * 主子订单关联信息表
 */
@ApiModel(value="com-dispart-test-TOrderRelevancyInfo")
@Data
@TableName(value = "logistics.t_order_relevancy_info")
public class TOrderRelevancyInfo {
    /**
     * 主订单号
     */
    @TableId(value = "MAIN_ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="主订单号")
    private String mainOrderId;

    /**
     * 子订单号
     */
    @TableId(value = "ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="子订单号")
    private String orderId;

    /**
     * 客户子订单号,报文中送给惠市宝的(包含供应商客户收款金额和市场客户收款金额订单)
     */
    @TableId(value = "CUST_ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="客户子订单号,报文中送给惠市宝的(包含供应商客户收款金额和市场客户收款金额订单)")
    private String custOrderId;

    /**
     * 惠市宝生成主订单号
     */
    @TableField(value = "CCB_ORDER_ID")
    @ApiModelProperty(value="惠市宝生成主订单号")
    private String ccbOrderId;

    /**
     * 惠市宝生成子订单号
     */
    @TableField(value = "CCB_ORDER_DETAIL_ID")
    @ApiModelProperty(value="惠市宝生成子订单号")
    private String ccbOrderDetailId;

    /**
     * 时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="时间戳")
    private Date updateDt;

    /**
     * 订单类型（0-商户收款 1-市场收款）
     */
    @TableField(value = "TYPE")
    @ApiModelProperty(value="订单类型（0-商户收款 1-市场收款）")
    private String type;

    public static final String COL_MAIN_ORDER_ID = "MAIN_ORDER_ID";

    public static final String COL_ORDER_ID = "ORDER_ID";

    public static final String COL_CUST_ORDER_ID = "CUST_ORDER_ID";

    public static final String COL_CCB_ORDER_ID = "CCB_ORDER_ID";

    public static final String COL_CCB_ORDER_DETAIL_ID = "CCB_ORDER_DETAIL_ID";

    public static final String COL_UPDATE_DT = "UPDATE_DT";

    public static final String COL_TYPE = "TYPE";

}