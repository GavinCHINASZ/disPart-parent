package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 物流园设备进出场关联表
    */
@ApiModel(value="com-dispart-model-TDeviceRelevance")
@Data
public class TDeviceRelevance {
    /**
    * 设备编号
    */
    @ApiModelProperty(value="设备编号")
    private String deviceNo;

    /**
    * 进出场口编号
    */
    @ApiModelProperty(value="进出场口编号")
    private String inOutId;

    /**
    * 进出口类型  0-进口 1-出口
    */
    @ApiModelProperty(value="进出口类型  0-进口 1-出口")
    private String ioType;

    /**
    * 进出场名称
    */
    @ApiModelProperty(value="进出场名称")
    private String inOut;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String reamrk;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date upTime;
}