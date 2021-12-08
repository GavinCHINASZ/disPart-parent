package com.dispart.dto.deviceManagerDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 云喇叭播报信息
    */
@ApiModel(value="com-dispart-model-base-TLoudspeakerInfo")
@Data
public class TLoudspeakerInfo {
    /**
    * 请求唯一标志
    */
    @ApiModelProperty(value="请求唯一标志")
    private String requestId;

    /**
    * 设备编号
    */
    @ApiModelProperty(value="设备编号")
    private String deviceId;

    /**
    * 请求时间戳
    */
    @ApiModelProperty(value="请求时间戳")
    private String requestTime;

    /**
    * 请求类型
    */
    @ApiModelProperty(value="请求类型")
    private String action;

    /**
    * 播报金额
    */
    @ApiModelProperty(value="播报金额")
    private BigDecimal amount;

    /**
    * 超时时间
    */
    @ApiModelProperty(value="超时时间")
    private Integer timeout;

    /**
    * 返回码，200成功
    */
    @ApiModelProperty(value="返回码，200成功")
    private String errCode;

    /**
    * 错误信息
    */
    @ApiModelProperty(value="错误信息")
    private String errMsg;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
}