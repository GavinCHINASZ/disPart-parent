package com.dispart.dto.entrance;

import com.dispart.vo.entrance.TCargoInfoReportDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0227FindOutDto {


    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;
    @ApiModelProperty(value="车辆净重")
    private String vehicleWeight;

//    @ApiModelProperty(value="车辆类别")
//    private String vehicleTp;
//    @ApiModelProperty(value="车辆类别ID")
//    private String vehicleTpId;
//    @ApiModelProperty(value="车主名称")
//    private String driverNm;
//    @ApiModelProperty(value="车主电话")
//    private String driverPhone;
//
//    @ApiModelProperty(value="车辆类型")
//    private String vehicle;
    @ApiModelProperty(value="车辆类型ID")
    private String vehicleId;

    @ApiModelProperty(value="报备明细")
    private List<TCargoInfoReportDetails> prdList;
}
