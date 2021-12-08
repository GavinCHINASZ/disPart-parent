package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 货物统计查询请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QueryVechicleInDto {
    /**
     * 车牌号
     */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
     * 车辆类型
     */
    @ApiModelProperty(value="车辆类型ID")
    private String vehicleId;

    /**
     * 车辆净重(驾驶证上的车重)
     */
    @ApiModelProperty(value="车辆净重(驾驶证上的车重)")
    private BigDecimal vehicleWeight;

}
