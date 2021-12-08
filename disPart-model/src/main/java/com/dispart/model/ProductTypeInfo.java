package com.dispart.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ProductTypeInfo
 * @creat 2021/6/10 16:56
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "产品类型表")
@TableName("t_product_type_info")
public class ProductTypeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类ID")
    @TableField("prodct_type_id")
    private String prdctTypeId;

    @ApiModelProperty(value = "分类名称")
    @TableField("prdct_type")
    private String prdctType;

    @ApiModelProperty(value = "父级分类名称")
    @TableField("parent_type_id")
    private String parentTypeId;

    @ApiModelProperty(value = "分类状态")
    @TableField("prdct_st")
    private String prdctSt;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_dt")
    private Date updateDt;

}