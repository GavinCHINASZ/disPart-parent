package com.dispart.vo.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderListReturn {

    /**
     * 商品UNITKEY值
     */
    @ApiModelProperty(value="商品UNITkey值")
    private String prdctUnitKey;

    /**
     * 供货商ID（客户会员表）
     */
    @ApiModelProperty(value="供货商ID（客户会员表）")
    private String orderId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value="商品ID")
    private String prdctId;

    /**
     * 采购商ID（用户注册表)
     */
    @ApiModelProperty(value="采购商ID（用户注册表)")
    private String purchId;

    /**
     * 供货商ID（客户会员表）
     */
    @ApiModelProperty(value="供货商ID（客户会员表）")
    private String provId;

    /**
     * 商品类型
     */
    @ApiModelProperty(value="商品类型")
    private String prdctType;

    /**
     * 商品名称
     */
    @ApiModelProperty(value="商品名称")
    private String prdctNm;

    /**
     * 商品单价
     */
    @ApiModelProperty(value="商品单价")
    private BigDecimal prdctPrice;

    /**
     * 商品数量
     */
    @ApiModelProperty(value="商品数量")
    private BigDecimal prdctNum;

    /**
     * 单位
     */
    @ApiModelProperty(value="单位")
    private String prdctUnit;

    /**
     * 商品总价
     */
    @ApiModelProperty(value="商品总价")
    private BigDecimal prdctAmt;

    /**
     * 交易总价
     */
    @ApiModelProperty(value="交易总价")
    private BigDecimal txnAmt;

    /**
     * 供货商名称
     */
    @ApiModelProperty(value="供货商名称")
    private String provNm;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value="优惠金额")
    private BigDecimal preferPrice;

    /**
     * 附加金额
     */
    @ApiModelProperty(value="附加金额")
    private BigDecimal additAmt;

    /**
     * 供应商分账收款金额
     */
    @ApiModelProperty(value="供应商分账收款金额")
    private BigDecimal partAmt;

    /**
     * 市场方分账收款手续费金额
     */
    @ApiModelProperty(value="市场方分账收款手续费金额")
    private BigDecimal servChrg;

    /**
     * 订单状态(  0-未支付 处理中1 已取消2 已支付3 )
     */
    @ApiModelProperty(value="订单状态(  0-未支付 处理中1 已取消2 已支付3 )")
    private String orderSt;

    /**
     * 下单时间
     */
    @ApiModelProperty(value="下单时间")
    private Date orderTm;

    /**
     * 更新时间戳
     */
    @ApiModelProperty(value="更新时间戳")
    private Date updateDt;

    /**
     * 订单模式 0-简易模式 1-明细模式
     */
    @ApiModelProperty(value="订单模式 0-简易模式 1-明细模式")
    private String orderModel;

    /**
     * 主订单ID
     */
    @ApiModelProperty(value="主订单ID")
    private String mainOrderId;

    /**
     * 交易类型
     */
    @ApiModelProperty(value="交易类型")
    private String orderTp;

    /**
     * 交易日期
     */
    @ApiModelProperty(value="交易日期")
    private Date txnDt;

    private List goodList;
}
