package com.dispart.dto.entrance;


import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 进出场管理-进出场停车费查询
 */
@Data
public class D_0503FindDto extends PageInfo {
    /**
     * 进出场状态 0-未付款 1-已进场 2-退款中 3-已出场 4-作废
     */
    @ApiModelProperty(value="进出场状态 0-未付款 1-已进场 2-退款中 3-已出场 4-作废 ")
    private String status;

    /**
     * 车牌号
     */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
     * 进出场ID
     */
    @ApiModelProperty(value="进出场ID")
    private String inId;

    /**
     * 进场支付状态 0-待支付 1-支付失败 2-支付成功
     */
    @ApiModelProperty(value="进场支付状态 0-待支付 1-支付失败 2-支付成功 ")
    private String inPayStatus;

    /**
     * 出场支付状态 0-待支付 1-支付失败 2-支付成功
     */
    @ApiModelProperty(value="出场支付状态 0-待支付 1-支付失败 2-支付成功 ")
    private String outPayStatus;

    /**
     * 进场支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @ApiModelProperty(value="支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码")
    private String paymentMode;

    /**
     * 出场支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @ApiModelProperty(value="支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码")
    private String outPaymentMode;

    /**
     * 进场查询开始时间
     */
    @ApiModelProperty(value="查询开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:sss")
    private Date inTimeStart;

    /**
     * 进场查询结束时间
     */
    @ApiModelProperty(value="查询结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:sss")
    private Date inTimeEnd;

    /**
     * 出场查询开始时间
     */
    @ApiModelProperty(value="查询开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:sss")
    private Date outTimeStart;

    /**
     * 出场查询结束时间
     */
    @ApiModelProperty(value="查询结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:sss")
    private Date outTimeEnd;


}
