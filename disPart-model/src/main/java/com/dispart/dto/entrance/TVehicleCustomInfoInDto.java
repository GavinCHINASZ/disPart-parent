package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TVehicleCustomInfoInDto {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 进出场ID
     */
    @ApiModelProperty(value = "进出场ID")
    private String inId;

    /**
     * 原客户编号
     */
    @ApiModelProperty(value = "原客户编号")
    private String provId;

    /**
     * 原客户名称
     */
    @ApiModelProperty(value = "原客户名称")
    private String provNm;

    /**
     * 原客户卡号
     */
    @ApiModelProperty(value = "原客户卡号")
    private String cardNo;

    /**
     * 新客户编号
     */
    @ApiModelProperty(value = "新客户编号")
    private String newProvId;

    /**
     * 新客户名称
     */
    @ApiModelProperty(value = "新客户名称")
    private String newProvNm;

    /**
     * 新客户卡号
     */
    @ApiModelProperty(value = "新客户卡号")
    private String newCardNo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String operId;

    /**
     * 所属机构
     */
    @ApiModelProperty(value = "所属机构")
    private String subOrg;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号")
    private String depId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date upTime;
}

