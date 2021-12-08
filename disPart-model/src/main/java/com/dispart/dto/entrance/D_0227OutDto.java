package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:李兆辉
 * @date:Created in 2021/8/07 11:25
 * @description 进场信息查询dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0227OutDto {
    @ApiModelProperty(value="信息客户信息")
    private Object customInfo;
    @ApiModelProperty(value="车辆")
    private Object vechicleInfo;
    @ApiModelProperty(value="报备信息")
    private Object cargoInfo;
    @ApiModelProperty(value="车辆黑名单")
    private Object vehicleBlacklist;

}
