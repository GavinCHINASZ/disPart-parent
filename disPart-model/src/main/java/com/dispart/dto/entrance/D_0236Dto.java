package com.dispart.dto.entrance;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出场缴费
 */
@Data
public class D_0236Dto {
    /**
     * 出场口编号
     */
    @ApiModelProperty(value="出场口编号")
    private String outId;

    /**
     * 支付模式 0-微信 1-支付宝
     */
    @ApiModelProperty(value="支付模式")
    private String paymentMode;

    /**
     * 付款人编号
     */
    @ApiModelProperty(value="payerNo")
    private String payerNo;

    /**
     * 微信appId
     */
    @ApiModelProperty(value="微信appId")
    private String appId;

    /**
     * 微信userIdent
     */
    @ApiModelProperty(value="微信userIdent")
    private String userIdent;

}