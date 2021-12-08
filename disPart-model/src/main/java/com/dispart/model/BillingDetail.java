package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 账单管理
 */
@ApiModel(value = "com-dispart-model-BillingDetail")
@Data
public class BillingDetail {
    private static final long serialVersionUID = 1L;
    /**
     * 账单编号
     */
    @ApiModelProperty(value = "账单编号")
    private String billNum;

    /**
     * 账单状态 0-已缴费  1-未缴费 2-已作废 3-退费申请 4-已退费
     */
    @ApiModelProperty(value = "账单状态 0-已缴费  1-未缴费 2-已作废 3-退费申请 4-已退费")
    private String billSt;

    /**
     * 支付方式 0-现金支付 6-微信支付 7-支付宝
     */
    @ApiModelProperty(value = "支付方式 0-现金支付 6-微信支付 7-支付宝")
    private String paymentMode;

    /**
     * 支付状态 0-已支付  1-未支付 6-已支付退款 7-已支付换票 8-换票
     */
    @ApiModelProperty(value = "支付状态 0-已支付  1-未支付 6-已支付退款 7-已支付换票 8-换票")
    private String paymentSt;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private String depId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String provId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String provNm;

    /**
     * 客户电话
     */
    @ApiModelProperty(value = "客户电话")
    private String telephone;

    /**
     * 摊位编号
     */
    @ApiModelProperty(value = "摊位编号")
    private String boothNum;

    /**
     * 摊位名称
     */
    @ApiModelProperty(value = "摊位名称")
    private String boothName;

    /**
     * 缴费项目ID
     */
    @ApiModelProperty(value = "缴费项目ID")
    private String payId;

    /**
     * 缴费项目
     */
    @ApiModelProperty(value = "缴费项目")
    private String payItem;

    /**
     * 纸质合同编号
     */
    @ApiModelProperty(value = "纸质合同编号")
    private String contractNum;

    /**
     * 账单金额
     */
    @ApiModelProperty(value = "账单金额")
    private BigDecimal billAmt;

    /**
     * 滞纳金
     */
    @ApiModelProperty(value = "滞纳金")
    private BigDecimal overdueFine;

    /**
     * 应收总金额
     */
    @ApiModelProperty(value = "应收总金额")
    private BigDecimal recvAmt;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal preferPrice;

    /**
     * 实收金额
     */
    @ApiModelProperty(value = "实收金额")
    private BigDecimal actRecevAmt;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    private String summary;

    /**
     * 面积
     */
    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    /**
     * 缴费月数
     */
    @ApiModelProperty(value = "缴费月数")
    private Integer payNum;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private Date startDt;

    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    private Date endDt;

    /**
     * 本次抄表数
     */
    @ApiModelProperty(value = "本次抄表数")
    private BigDecimal currRecNum;

    /**
     * 上次抄表数
     */
    @ApiModelProperty(value = "上次抄表数")
    private BigDecimal lastRecNum;

    /**
     * 使用数
     */
    @ApiModelProperty(value = "使用数")
    private BigDecimal usageQnty;

    /**
     * 付款时间
     */
    @ApiModelProperty(value = "付款时间")
    private Date payTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作员ID
     */
    @ApiModelProperty(value = "操作员")
    private String operId;

    /**
     * 操作员名字
     */
    @ApiModelProperty(value = "操作员")
    private String operNm;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date upTime;
}