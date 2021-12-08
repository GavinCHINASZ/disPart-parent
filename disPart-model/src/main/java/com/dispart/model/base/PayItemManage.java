package com.dispart.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * 缴费项目数据
 */
@ApiModel(value = "com-dispart-model-base-PayItemManage")
@Data
@TableName(value = "t_pay_prj_manager")
public class PayItemManage extends PageInfo {
    /**
     * 缴费项目Id
     */
    @ApiModelProperty(value = "缴费项目Id")
    @TableField(value = "PAY_ID")
    private String payId;
    /**
     * 缴费项目
     */
    @ApiModelProperty(value = "缴费项目")
    @TableField(value = "PAY_ITEM")
    private String payItem;
    /**
     * 部门Id
     */
    @ApiModelProperty(value = "部门Id")
    @TableField(value = "DEP_ID")
    private String depId;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @TableField(value = "DEP_NM")
    private String depNm;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "REMARK")
    private String remark;
    /**
     * 操作员
     */
    @ApiModelProperty(value = "操作员")
    @TableField(value = "OPER_ID")
    private String operId;
    /**
     *创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREAT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     *更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UP_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;
    @ApiModelProperty(value = "部门信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<DISP20210019DepFindByParamInDto> departInfo;
}
