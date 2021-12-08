package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 冲正申请表
    */
@ApiModel(value="com-dispart-model-AccnoReverseApply")
@Data
public class AccnoReverseApply {
    /**
    * 主键ID
    */
    @ApiModelProperty(value="主键ID")
    private Long id;

    /**
    * 流水号
    */
    @ApiModelProperty(value="流水号")
    private String jrnlNum;

    /**
    * 申请人ID
    */
    @ApiModelProperty(value="申请人ID")
    private String operId;

    /**
    * 申请时间
    */
    @ApiModelProperty(value="申请时间")
    private Date operTm;

    /**
    * 复核人ID
    */
    @ApiModelProperty(value="复核人ID")
    private String checkId;

    /**
    * 复核时间
    */
    @ApiModelProperty(value="复核时间")
    private Date checkTm;

    /**
    * 复核状态 0-待复核 1-已驳回 2-冲正完成
    */
    @ApiModelProperty(value="复核状态 0-待复核 1-已驳回 2-冲正完成")
    private String checkSt;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 所属机构
    */
    @ApiModelProperty(value="所属机构")
    private String subOrg;

    /**
    * 部门编号
    */
    @ApiModelProperty(value="部门编号")
    private String depId;

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