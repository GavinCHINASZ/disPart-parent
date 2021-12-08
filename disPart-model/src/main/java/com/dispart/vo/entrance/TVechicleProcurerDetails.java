package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 采购商进场货品明细
    */
@ApiModel(value="com-dispart-model-base-TVechicleProcurerDetails")
@Data
public class TVechicleProcurerDetails {
    /**
    * 进出场ID
    */
    @ApiModelProperty(value="进出场ID")
    private String inId;

    /**
    * 品种ID
    */
    @ApiModelProperty(value="品种ID")
    private String varietyId;

    /**
    * 车牌号
    */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
    * 费用类型 0-进场品种收费 1-摊位费
    */
    @ApiModelProperty(value="费用类型 0-进场品种收费 1-临时摊位费")
    private String expTp;

    /**
    * 品种名称
    */
    @ApiModelProperty(value="品种名称")
    private String prdctNm;

    /**
     * 品种费率
     */
    @ApiModelProperty(value="品种费率")
    private BigDecimal rate;

    /**
    * 重量
    */
    @ApiModelProperty(value="数量/重量")
    private BigDecimal num;

    /**
    * 单价
    */
    @ApiModelProperty(value="单价")
    private BigDecimal unitPrice;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal amt;

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
    * 状态 0-未检测 5-待检测 默认0
    */
    @ApiModelProperty(value="状态 0-未检测 5-待检测 默认0")
    private String status;

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

    /**
     * 品种ID
     */
    private String categoryId;

    /**
     *品种名称 品种父名称(品类名称)
     */
    private String categoryNm;

    // 顶级品类
    private String oneCategoryNm;

}
