package com.dispart.dto.dataquery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DISP20210354OutDto {

    @ApiModelProperty(value="主键")
    private String id;

    @ApiModelProperty(value="客户编号")
    private String provId;

    @ApiModelProperty(value="客户名称")
    private String provNm;

    @ApiModelProperty(value="卡号")
    private String cardNo;

    @ApiModelProperty(value="交易类型 0-开户 1-冻结 2-调账 3-提现 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂")
    private String txnType;

    @ApiModelProperty(value="账号")
    private String account;

    @ApiModelProperty(value="操作前金额")
    private BigDecimal beforeAmt;

    @ApiModelProperty(value="操作后金额")
    private BigDecimal afterAmt;

    @ApiModelProperty(value="交易金额")
    private BigDecimal txnAmt;

    @ApiModelProperty(value="交易日期")
    private Date txnDt;

    @ApiModelProperty(value="交易渠道")
    private String transChnl;

    @ApiModelProperty(value="摘要(操作说明)")
    private String summary;

    @ApiModelProperty(value="操作类型 0-加余额 1-减余额")
    private String operTp;

    @ApiModelProperty(value="操作员")
    private String operId;

    @ApiModelProperty(value="复核员")
    private String auditor;

    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    @ApiModelProperty(value="更新时间")
    private Date upTime;

    @ApiModelProperty(value="银行卡号")
    private String bankNo;

    @ApiModelProperty(value="银行联行号")
    private String bankPayNo;

    @ApiModelProperty(value="银行名称")
    private String openAccuName;

    @ApiModelProperty(value="状态 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过")
    private String status;

    @ApiModelProperty(value="备注")
    private String remark;

}
