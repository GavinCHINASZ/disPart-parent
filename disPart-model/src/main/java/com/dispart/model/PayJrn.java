package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 流水表
 */
@ApiModel(value = "com-dispart-model-PayJrn")
@Data
public class PayJrn {
    /**
     * 流水号/自生成
     */
    @ApiModelProperty(value = "流水号/自生成")
    private String jrnlNum;

    /**
     * 业务号
     */
    @ApiModelProperty(value = "业务号")
    private String businessNo;

    /**
     * 交易类型 0-充值 1-提现 2-进场费 3-出场费 4-账单退款 5-批发交易 6-账单缴费 7-月卡缴费 8-补贴发放 9-补贴撤销 10-进场退费
     */
    @ApiModelProperty(value = "交易类型 0-充值 1-提现 2-进场费 3-出场费 4-账单退款 5-批发交易 6-账单缴费 7-月卡缴费 8-补贴发放 9-补贴撤销 10-进场退费")
    private String txnType;

    /**
     * 交易方式 1-惠市宝 2-现金 3-pos银行卡 4-一卡通 5-银行卡 6-pos机二维码
     */
    @ApiModelProperty(value = "交易方式 1-惠市宝 2-现金 3-pos银行卡 4-一卡通 5-银行卡 6-pos机二维码")
    private String transMd;

    /**
     * 交易时间
     */
    @ApiModelProperty(value = "交易时间")
    private Date txnTm;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额")
    private BigDecimal txnAmt;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal charges;

    /**
     * 手续费率
     */
    @ApiModelProperty(value = "手续费率")
    private BigDecimal rate;

    /**
     * 付款人编号
     */
    @ApiModelProperty(value = "付款人编号")
    private String payerNo;

    /**
     * 付款人名称
     */
    @ApiModelProperty(value = "付款人名称")
    private String payName;

    /**
     * 付款人卡号
     */
    @ApiModelProperty(value = "付款人卡号")
    private String payCard;

    /**
     * 付款人账号
     */
    @ApiModelProperty(value = "付款人账号")
    private String payAcct;

    /**
     * 收款人编号
     */
    @ApiModelProperty(value = "收款人编号")
    private String payeeNum;

    /**
     * 收款人名称
     */
    @ApiModelProperty(value = "收款人名称")
    private String payee;

    /**
     * 收款人卡号
     */
    @ApiModelProperty(value = "收款人卡号")
    private String payeeCard;

    /**
     * 收款人账号
     */
    @ApiModelProperty(value = "收款人账号")
    private String payeeAcct;

    /**
     * 主订单号
     */
    @ApiModelProperty(value = "主订单号")
    private String mainOrderId;

    /**
     * pos机支付的订单号
     */
    @ApiModelProperty(value = "pos机支付的订单号")
    private String posOrderId;

    /**
     * 摘要
     */
    @ApiModelProperty(value = "摘要")
    private String summary;

    /**
     * 凭证号
     */
    @ApiModelProperty(value = "凭证号")
    private String creditNo;

    /**
     * 优惠券ID
     */
    @ApiModelProperty(value = "优惠券ID")
    private String discountId;

    /**
     * 状态 0-待支付 1-支付失败 2-支付成功 4-退款中 5-退款成功
     */
    @ApiModelProperty(value = "状态 0-待支付 1-支付失败 2-支付成功 4-退款中 5-退款成功")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作员
     */
    @ApiModelProperty(value = "操作员")
    private String operId;

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

    @ApiModelProperty(value = "")
    private String chanlNo;
}