package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 车辆管理
    */
@ApiModel(value="com-dispart-model-base-TVehicleManager")
@Data
public class TVehicleManager {
    /**
    * 编号
    */
    @ApiModelProperty(value="编号")
    private Integer num;

    /**
    * 车辆类别ID 01-一类车、02-二类车、03-三类车、04-四类车
    */
    @ApiModelProperty(value="车辆类别ID 01-一类车、02-二类车、03-三类车、04-四类车")
    private String vehicleTpId;

    /**
    * 车辆类别
    */
    @ApiModelProperty(value="车辆类别")
    private String vehicleTp;

    /**
    * 车辆车型ID
    */
    @ApiModelProperty(value="车辆车型ID")
    private String vehicleId;

    /**
    * 车辆车型
    */
    @ApiModelProperty(value="车辆车型")
    private String vehicle;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 操作员
    */
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date upTime;
}