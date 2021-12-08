package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 车辆档案
    */
@ApiModel(value="com-dispart-model-base-TVechicleRecord")
@Data
public class TVechicleRecord {
    /**
    * 车牌号
    */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
    * 空车自重
    */
    @ApiModelProperty(value="空车自重")
    private BigDecimal emptyWght;

    /**
    * 车主名称
    */
    @ApiModelProperty(value="车主名称")
    private String driverNm;
    /**
     * 车主名称
     */
    @ApiModelProperty(value="车主电话")
    private String driverPhone;

    /**
    * 状态
    */
    @ApiModelProperty(value="状态")
    private String status;

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
     * 车辆车型ID
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
     * 客户编号
     */
    @ApiModelProperty(value="客户编号")
    private String provId;

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