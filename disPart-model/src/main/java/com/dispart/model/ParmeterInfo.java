package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-dispart-model-ParmeterInfo")
@Data
public class ParmeterInfo implements Serializable {
    /**
     * 参数类别
     */
    @ApiModelProperty(value = "参数类别")
    private String paramType;

    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    private String paramNm;

    /**
     * 参数值
     */
    @ApiModelProperty(value = "参数值")
    private String paramVal;

    /**
     * 参数描述
     */
    @ApiModelProperty(value = "参数描述")
    private String paramDesc;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 查询权限 0-不允许界面查询 1-允许界面查询
     */
    @ApiModelProperty(value = "查询权限 0-不允许界面查询 1-允许界面查询")
    private String permission;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private Date updateDt;

    private static final long serialVersionUID = 1L;
}