package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 出场退费一卡通任务表
    */
@ApiModel(value="com-dispart-model-TCardReturnTask")
@Data
public class TCardReturnTask {
    /**
    * 任务ID
    */
    @ApiModelProperty(value="任务ID")
    private Integer taskId;

    /**
    * 任务日期
    */
    @ApiModelProperty(value="任务日期")
    private Date taskDt;

    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
    * 卡号
    */
    @ApiModelProperty(value="卡号")
    private String cardNo;

    /**
    * 账户号
    */
    @ApiModelProperty(value="账户号")
    private String account;

    /**
    * 退费金额
    */
    @ApiModelProperty(value="退费金额")
    private BigDecimal amount;

    /**
    * 用途
    */
    @ApiModelProperty(value="用途")
    private String useOf;

    /**
    * 流水表流水号
    */
    @ApiModelProperty(value="流水表流水号")
    private String jrnlNum;

    /**
    * 退费状态 0-待退费 1-退费中 2-待查询 9-已退费
    */
    @ApiModelProperty(value="退费状态 0-待退费 1-退费中 2-待查询 9-已退费")
    private String status;

    /**
    * 银企直连请求序列号，查询交易使用
    */
    @ApiModelProperty(value="银企直连请求序列号，查询交易使用")
    private String requestSn;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

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