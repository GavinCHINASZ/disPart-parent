package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 来货报备主表
    */
@ApiModel(value="com-dispart-model-base-TCargoInfoReport")
@Data
public class TCargoInfoReport {
    /**
    * 来货报备主表ID
    */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;

    /**
    * 报备日期
    */
    @ApiModelProperty(value="报备日期")
    private Date reportDt;

    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
    * 客户名称
    */
    @ApiModelProperty(value="客户名称")
    private String provNm;

    /**
    * 客户类型
    */
    @ApiModelProperty(value="客户类型")
    private String customTp;

    /**
    * 手机号码
    */
    @ApiModelProperty(value="手机号码")
    private String phone;

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
     * 车辆类别ID
     */
    @ApiModelProperty(value="车辆类别ID")
    private String vehicleTpId;

    /**
    * 车辆净重(驾驶证上的车重)
    */
    @ApiModelProperty(value="车辆净重(驾驶证上的车重)")
    private BigDecimal vehicleWeight;

    /**
    * 车辆总重量
    */
    @ApiModelProperty(value="车辆总重量")
    private BigDecimal vehicleTtlWght;

    /**
    * 品种总重
    */
    @ApiModelProperty(value="品种总重")
    private BigDecimal prdctWght;

    /**
    * 产地图片
    */
    @ApiModelProperty(value="产地图片")
    private String placeUrl;

    /**
    * 是否免进场费
    */
    @ApiModelProperty(value="是否免进场费")
    private String isFree;

    /**
    * 记录状态 0-正常  1-作废
    */
    @ApiModelProperty(value="记录状态 0-正常  1-作废")
    private String recordSt;

    /**
    * 审核状态 0-未审核 1-审核通过 2-审核未通过 3-已进场
    */
    @ApiModelProperty(value="审核状态 0-未审核 1-审核通过 2-审核未通过 3-已进场")
    private String auditorSt;

    /**
    * 审核人
    */
    @ApiModelProperty(value="审核人")
    private String creator;

    /**
    * 审核时间
    */
    @ApiModelProperty(value="审核时间")
    private Date auditorTm;

    /**
    * 修改人
    */
    @ApiModelProperty(value="修改人")
    private String modifier;

    /**
    * 修改时间
    */
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 操作员/创建人
    */
    @ApiModelProperty(value="操作员/创建人")
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