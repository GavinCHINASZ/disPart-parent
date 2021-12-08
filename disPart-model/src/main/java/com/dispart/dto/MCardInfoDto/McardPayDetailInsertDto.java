package com.dispart.dto.MCardInfoDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class McardPayDetailInsertDto {
    /**
     * 缴费单号
     */
    @ApiModelProperty(value = "缴费单号")
    private String payOrder;

    @ApiModelProperty(value = "客户编号")
    private String provId;

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
     * 支付状态 0-未支付 1-已支付
     */
    @ApiModelProperty(value = "支付状态 0-未支付 1-已支付")
    private String paymentSt;

    /**
     * 0-正常 1-已到期 2-已作废
     */
    @ApiModelProperty(value = "0-正常 1-已到期 2-已作废")
    private String status;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String creatTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private String upTime;
}
