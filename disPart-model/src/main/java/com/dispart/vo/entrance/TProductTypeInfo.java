package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 商品信息表
    */
@ApiModel(value="com-dispart-model-base-TProductTypeInfo")
@Data
public class TProductTypeInfo {
    /**
    * 品种编号
    */
    @ApiModelProperty(value="品种编号")
    private String varietyNo;

    /**
    * 品种名称
    */
    @ApiModelProperty(value="品种名称")
    private String prdctNm;

    /**
    * 父品种编号
    */
    @ApiModelProperty(value="父品种编号")
    private String parentPrdtId;

    /**
    * 父品种名称
    */
    @ApiModelProperty(value="父品种名称")
    private String parentPrdt;

    /**
    * 品种状态
    */
    @ApiModelProperty(value="品种状态")
    private String prdctSt;

    /**
    * 等级
    */
    @ApiModelProperty(value="等级")
    private String level;

    /**
    * 品种简称
    */
    @ApiModelProperty(value="品种简称")
    private String prdctAbbr;

    /**
    * 品种费率
    */
    @ApiModelProperty(value="品种费率")
    private BigDecimal rate;

    /**
    * 品种价格
    */
    @ApiModelProperty(value="品种价格")
    private BigDecimal price;

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