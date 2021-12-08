package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 子订单信息表
 */
@ApiModel(value="com-dispart-test-TOrderDetailInfo")
@Data
@TableName(value = "logistics.t_order_detail_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TOrderDetailInfo {

    /**
     * 供货商ID（客户会员表）
     */
    @TableId(value = "ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="供货商ID（客户会员表）")
    private String orderId;

    /**
     * 采购商ID（用户注册表)
     */
    @TableField(value = "PURCH_ID")
    @ApiModelProperty(value="采购商ID（用户注册表)")
    private String purchId;

    /**
     * 供货商ID（客户会员表）
     */
    @TableField(value = "PROV_ID")
    @ApiModelProperty(value="供货商ID（客户会员表）")
    private String provId;

    /**
     * 交易总价
     */
    @TableField(value = "TXN_AMT")
    @ApiModelProperty(value="交易总价")
    private BigDecimal txnAmt;

    /**
     * 供货商名称
     */
    @TableField(value = "PROV_NM")
    @ApiModelProperty(value="供货商名称")
    private String provNm;

    /**
     * 优惠金额
     */
    @TableField(value = "PREFER_PRICE")
    @ApiModelProperty(value="优惠金额")
    private BigDecimal preferPrice;

    /**
     * 优惠金额
     */
    @TableField(value = "PRDCT_AMT")
    @ApiModelProperty(value="优惠金额")
    private BigDecimal prdctAmt;

    /**
     * 附加金额
     */
    @TableField(value = "ADDIT_AMT")
    @ApiModelProperty(value="附加金额")
    private BigDecimal additAmt;

    /**
     * 供应商分账收款金额
     */
    @TableField(value = "PART_AMT")
    @ApiModelProperty(value="供应商分账收款金额")
    private BigDecimal partAmt;

    /**
     * 市场方分账收款手续费金额
     */
    @TableField(value = "SERV_CHRG")
    @ApiModelProperty(value="市场方分账收款手续费金额")
    private BigDecimal servChrg;

    /**
     * 订单状态(  0-未支付 处理中1 已取消2 已支付3 )
     */
    @TableField(value = "ORDER_ST")
    @ApiModelProperty(value="订单状态(  0-未支付 处理中1 已取消2 已支付3 )")
    private String orderSt;

    /**
     * 下单时间
     */
    @TableField(value = "ORDER_TM")
    @ApiModelProperty(value="下单时间")
    private Date orderTm;

    /**
     * 更新时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="更新时间戳")
    private Date updateDt;

    /**
     * 订单模式 0-简易模式 1-明细模式
     */
    @TableField(value = "ORDER_MODEL")
    @ApiModelProperty(value="订单模式 0-简易模式 1-明细模式")
    private String orderModel;

    /**
     * 主订单ID
     */
    @TableField(value = "MAIN_ORDER_ID")
    @ApiModelProperty(value="主订单ID")
    private String mainOrderId;


    /**
     * 交易类型
     */
    @TableField(value = "ORDER_TP")
    @ApiModelProperty(value="交易类型")
    private String orderTp;

    /**
     * 交易日期
     */
    @TableField(value = "TXN_DT")
    @ApiModelProperty(value="交易日期")
    private Date txnDt;
    
    

    public static final String COL_ORDER_ID = "ORDER_ID";

    public static final String COL_PRDCT_ID = "PRDCT_ID";

    public static final String COL_PURCH_ID = "PURCH_ID";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_PRDCT_TYPE = "PRDCT_TYPE";

    public static final String COL_PRDCT_NM = "PRDCT_NM";

    public static final String COL_PRDCT_PRICE = "PRDCT_PRICE";

    public static final String COL_PRDCT_NUM = "PRDCT_NUM";

    public static final String COL_PRDCT_UNIT = "PRDCT_UNIT";

    public static final String COL_PRDCT_AMT = "PRDCT_AMT";

    public static final String COL_PROV_NM = "PROV_NM";

    public static final String COL_PREFER_PRICE = "PREFER_PRICE";

    public static final String COL_ADDIT_AMT = "ADDIT_AMT";

    public static final String COL_PART_AMT = "PART_AMT";

    public static final String COL_SERV_CHRG = "SERV_CHRG";

    public static final String COL_ORDER_ST = "ORDER_ST";

    public static final String COL_ORDER_TM = "ORDER_TM";

    public static final String COL_UPDATE_DT = "UPDATE_DT";

    public static final String COL_ORDER_MODEL = "ORDER_MODEL";

    public static final String COL_TXN_DT = "TXN_DT";

    public static final String COL_PRDCT_UNIT_KEY = "PRDCT_UNIT_KEY";
}