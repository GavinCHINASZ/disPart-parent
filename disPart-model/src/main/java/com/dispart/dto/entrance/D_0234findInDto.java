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
public class D_0234findInDto {
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    @ApiModelProperty(value="出场门口编号")
    private String outNum;

    @ApiModelProperty(value="出场总重")
    private BigDecimal outTtlWght;

    @ApiModelProperty(value="车辆类型ID")
    private String vehicleId;

    @ApiModelProperty(value="车辆车型")
    private String vehicle;

    @ApiModelProperty(value="车辆类别ID")
    private String vehicleTpId;

    @ApiModelProperty(value="车辆类别")
    private String vehicleTp;


}
