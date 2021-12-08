package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class D_0233Dto {
    /**
     * 操作类型 0-进场 1-出场
     */
    @ApiModelProperty(value="操作类型")
    private String operateTp;

    /**
     * 车牌号
     */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
     * 进出场重量
     */
    @ApiModelProperty(value="进出场重量")
    private BigDecimal inOutWght;

    /**
     * 车辆图片url
     */
    @ApiModelProperty(value="车辆图片url")
    private String vehicleUrl;

    /**
     * 到达口
     */
    @ApiModelProperty(value="道闸口")
    private String inExportNum;
}
