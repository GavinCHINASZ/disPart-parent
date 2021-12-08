package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 车辆进出管理
    */
@ApiModel(value="com-dispart-model-base-TVechicleProcurer")
@Data
public class D_0503OutVO {
    /**
    * 进出场ID YYMMDD + 8位序列号
    */
    @ApiModelProperty(value="进出场ID YYMMDD + 8位序列号")
    private String inId;

    /**
    * 车牌号
    */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;


    /**
    * 进场地点
    */
    @ApiModelProperty(value="进场地点")
    private String inDoor;

    /**
     * 进场点
     */
    @ApiModelProperty(value="进场点")
    private String inDoorNm;

    /**
    * 进场时间
    */
    @ApiModelProperty(value="进场时间")
    private Date inTime;

    /**
    * 进场操作人
    */
    @ApiModelProperty(value="进场操作人")
    private String inOperId;

    /**
     * 进场操作人名称
     */
    @ApiModelProperty(value="进场操作人名称")
    private String inOperNm;


    /**
    * 出场地点
    */
    @ApiModelProperty(value="出场地点")
    private String outDoor;

    /**
     * 出场点
     */
    @ApiModelProperty(value="出场点")
    private String outDoorNm;

    /**
    * 出场时间
    */
    @ApiModelProperty(value="出场时间")
    private Date outTime;


    /**
    * 出场操作人
    */
    @ApiModelProperty(value="出场操作人")
    private String outOperId;

    /**
     * 出场操作人名称
     */
    @ApiModelProperty(value="出场操作人名称")
    private String outOperNm;


    /**
    * 停车时长
    */
    @ApiModelProperty(value="停车时长")
    private Integer parkTime;

    /**
    * 停车收费金额
    */
    @ApiModelProperty(value="停车收费金额")
    private BigDecimal parkAmt;

    /**
    * 停车优惠金额
    */
    @ApiModelProperty(value="停车优惠金额")
    private BigDecimal parkCpn;

    /**
    * 停车实收金额
    */
    @ApiModelProperty(value="停车实收金额")
    private BigDecimal parkActAmt;


    /**
    * 状态 0-未付款 1-已进场 2-退款中 3-已出场
    */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String status;


    /**
     * 进场支付状态 0-待支付 1-支付失败 2-支付成功
     */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String inPayStatus;

    /**
     * 出场支付状态 0-待支付 1-支付失败 2-支付成功
     */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String outPayStatus;


    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @ApiModelProperty(value="支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码")
    private String paymentMode;

    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @ApiModelProperty(value="支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码")
    private String outPaymentMode;

    /**
     * 进出场类型
     */
    @ApiModelProperty(value="进出场类型")
    private String type;


}