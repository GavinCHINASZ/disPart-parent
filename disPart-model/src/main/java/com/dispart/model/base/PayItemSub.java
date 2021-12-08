package com.dispart.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PayItemSub {
    /**
     * 缴费项目Id
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "缴费项目Id")
    @TableField(value = "PAY_ID")
    private String payId;
    /**
     * 缴费项目
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "缴费项目")
    @TableField(value = "PAY_ITEM")
    private String payItem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "备注")
    @TableField(value = "REMARK")
    private String remark;
    /**
     * 操作员
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "操作员")
    @TableField(value = "OPER_ID")
    private String operId;
    /**
     *创建时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREAT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     *更新时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UP_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;
}
