package com.dispart.dto.MCardInfoDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DISP20210220OutDto {
    /**
     * 月卡单号
     */
    @ApiModelProperty(value = "月卡单号")
    private String mcardNum;

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
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String vehicleNum;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 月卡类型 0-普通月卡 1-超级月卡 2-免费月卡
     */
    @ApiModelProperty(value = "月卡类型 0-普通月卡 1-超级月卡 2-免费月卡")
    private String mcardTp;

    /**
     * 开卡日期
     */
    @ApiModelProperty(value = "开卡日期")
    private Date openCardDt;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDt;

    /**
     * 到期日期
     */
    @ApiModelProperty(value = "到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDt;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 进场门口编号
     */
    @ApiModelProperty(value = "进场门口编号")
    private String inNum;

    /**
     * 出场门口编号
     */
    @ApiModelProperty(value = "出场门口编号")
    private String outNum;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作员ID
     */
    @ApiModelProperty(value = "操作员ID")
    private String operId;

    /**
     * 操作员
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



    @ApiModelProperty(value = "缴费单号")
    private String payOrder;

    /**
     * 应收总金额
     */
    @ApiModelProperty(value = "应收总金额")
    private BigDecimal recvAmt;

    /**
     * 缴费开始日期
     */
    @ApiModelProperty(value = "缴费开始日期")
    private Date payStDt;

    /**
     * 缴费结束日期
     */
    @ApiModelProperty(value = "缴费结束日期")
    private Date payDeadline;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;

}
