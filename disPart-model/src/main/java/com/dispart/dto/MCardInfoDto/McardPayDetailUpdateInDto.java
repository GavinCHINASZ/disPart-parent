package com.dispart.dto.MCardInfoDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class McardPayDetailUpdateInDto {
    /**
     * 缴费单号
     */
    @ApiModelProperty(value = "缴费单号")
    private String payOrder;

    /**
     * 月卡单号
     */
    @ApiModelProperty(value = "月卡单号")
    private String mcardNum;

    /**
     * 缴费金额
     */
    @ApiModelProperty(value = "缴费金额")
    private BigDecimal payAmt;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal preferPrice;

    /**
     * 应收总金额
     */
    @ApiModelProperty(value = "应收总金额")
    private BigDecimal recvAmt;

    /**
     * 缴费开始日期
     */
    @ApiModelProperty(value = "缴费开始日期")
    private String payStDt;

    /**
     * 缴费结束日期
     */
    @ApiModelProperty(value = "缴费结束日期")
    private String payDeadline;

    /**
     * 支付方式 0-现金支付 6-微信支付 7-支付宝
     */
    @ApiModelProperty(value = "支付方式 0-现金支付 6-微信支付 7-支付宝")
    private String paymentMode;

    /**
     * 支付状态 0-未支付 1-已支付 2-已作废
     */
    @ApiModelProperty(value = "支付状态 0-未支付 1-已支付 2-已作废")
    private String paymentSt;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private String paymentTime;

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
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    private String mcardTp;

}
