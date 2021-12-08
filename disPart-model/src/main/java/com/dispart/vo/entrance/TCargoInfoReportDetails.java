package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 来货报备明细表
    */
@ApiModel(value="com-dispart-model-base-TCargoInfoReportDetails")
@Data
public class TCargoInfoReportDetails {
    /**
    * 来货报备主表ID
    */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;

    /**
    * 品种ID
    */
    @ApiModelProperty(value="品种ID")
    private String varietyId;

    /**
    * 品种名称
    */
    @ApiModelProperty(value="品种名称")
    private String prdctNm;

    /**
    * 品类ID
    */
    @ApiModelProperty(value="品类ID")
    private String categoryId;

    /**
    * 品类名称
    */
    @ApiModelProperty(value="品类名称")
    private String categoryNm;

    /**
     * 品种费率
     */
    @ApiModelProperty(value="品种费率")
    private BigDecimal rate;

    /**
    * 数量/重量
    */
    @ApiModelProperty(value="数量/重量")
    private BigDecimal num;

    /**
    * 单位
    */
    @ApiModelProperty(value="单位")
    private String unit;

    /**
    * 产地
    */
    @ApiModelProperty(value="产地")
    private String prodPlace;
    /**
     * 产地图片Url
     */
    @ApiModelProperty(value="产地图片Url")
    private String placeUrl;

    /**
    * 生产企业
    */
    @ApiModelProperty(value="生产企业")
    private String manufactEnter;

    /**
    * 单价
    */
    @ApiModelProperty(value="单价")
    private BigDecimal unitPrice;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal amt ;

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

    /**
     * 状态 0-未检测 1-合格  2-不合格 5-待检测 默认0
     */
    @ApiModelProperty(value="状态 0-未检测 1-合格  2-不合格 5-待检测 默认0")
    private String status;

}