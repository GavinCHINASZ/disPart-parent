package com.dispart.dto.transactionDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ChargeQueryOutDto {

    @ApiModelProperty(value = "流水号/自生成")
    private String jrnlNum;

    @ApiModelProperty(value = "业务号")
    private String businessNo;

    @ApiModelProperty(value = "交易类型 0-充值 1-提现 2-进场费 3-停车费 4-退款")
    private String txnType;

    @ApiModelProperty(value = "交易方式 0-现金 1-惠市宝 2-pos支付 3-一卡通")
    private String transMd;

    @ApiModelProperty(value = "交易时间")
    private Date txnTm;

    @ApiModelProperty(value = "交易金额")
    private BigDecimal txnAmt;

    @ApiModelProperty(value = "付款人编号")
    private String payerNo;

    @ApiModelProperty(value = "付款人名称")
    private String payName;

    @ApiModelProperty(value = "付款人卡号")
    private String payCard;

    @ApiModelProperty(value = "付款人账号")
    private String payAcct;

    @ApiModelProperty(value = "收款人编号")
    private String payeeNum;

    @ApiModelProperty(value = "收款人名称")
    private String payee;

    @ApiModelProperty(value = "收款人卡号")
    private String payeeCard;

    @ApiModelProperty(value = "收款人账号")
    private String payeeAcct;

    @ApiModelProperty(value = "主订单号")
    private String mainOrderId;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @ApiModelProperty(value = "凭证号")
    private String creditNo;

    @ApiModelProperty(value = "状态 0-待支付 1-支付失败 2-支付成功 4-退款中 5-退款成功")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作员")
    private String operId;

    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    @ApiModelProperty(value = "更新时间")
    private Date upTime;

    @ApiModelProperty(value = "pos机支付的订单号")
    private String posOrderId;

    private String charges;

    @ApiModelProperty(value = "惠氏宝单号")
    private String hsbOrderId;

    @ApiModelProperty(value = "冲正表ID")
    private Integer reservId;

    @ApiModelProperty(value = "冲正状态")
    private String checkSt;

    @ApiModelProperty(value = "冲正复核人")
    private String checkNm;

}
