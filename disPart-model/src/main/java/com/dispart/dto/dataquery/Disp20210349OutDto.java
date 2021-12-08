package com.dispart.dto.dataquery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Disp20210349OutDto {

    @ApiModelProperty(value="任务ID")
    private Integer taskId;

    @ApiModelProperty(value="任务日期")
    private Date taskDt;

    @ApiModelProperty(value="客户编号")
    private String provId;

    @ApiModelProperty(value="客户姓名")
    private String provNm;

    @ApiModelProperty(value="卡号")
    private String cardNo;

    @ApiModelProperty(value="退费金额")
    private BigDecimal amount;

    @ApiModelProperty(value="流水表流水号")
    private String jrnlNum;

    @ApiModelProperty(value="状态 0-待充值 9-充值成功")
    private String status;

    @ApiModelProperty(value="银企直连请求序列号，查询交易使用")
    private String requestSn;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    @ApiModelProperty(value="更新时间")
    private Date upTime;

    private String operId;

    private String operNm;

    private Date outTime;

    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

}
