package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0231upInDto {
    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    @ApiModelProperty(value="进出场ID YYMMDD + 8位序列号")
    private String inId;

    /**
     * 状态 0-未付款 1-已进场 2-退款中 3-已出场
     */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String status;

    /**
     * 车牌号
     */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
     * 车辆类型
     */
    @ApiModelProperty(value="车辆类型")
    private String vehicle;

    @ApiModelProperty(value="车辆类型")
    private String vehicleId;

    @ApiModelProperty(value="车辆类别")
    private String vehicleTp;

    @ApiModelProperty(value="车辆类别ID")
    private String vehicleTpId;
    /**
     * 车辆皮重
     */
    @ApiModelProperty(value="车辆皮重")
    private BigDecimal tareWght;
    /**
     * 出场总重
     */
    @ApiModelProperty(value="出场总重")
    private BigDecimal outTtlWght;



}
