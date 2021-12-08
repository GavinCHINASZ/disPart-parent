package com.dispart.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 账户操作记录
    */
@ApiModel(value="com-dispart-model-AccnoInfoDetails")
@Data
public class AccnoInfoDetails {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private String id;

    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
    * 客户名称
    */
    @ApiModelProperty(value="客户名称")
    private String provNm;

    /**
    * 卡号
    */
    @ApiModelProperty(value="卡号")
    private String cardNo;

    /**
    * 账号
    */
    @ApiModelProperty(value="账号")
    private String account;

    /**
    * 交易类型 0-开户 1-冻结 2-调账 3-提现 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂
    */
    @ApiModelProperty(value="交易类型 0-开户 1-冻结 2-调账 3-提现 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂")
    private String txnType;

    /**
    * 操作前金额
    */
    @ApiModelProperty(value="操作前金额")
    private BigDecimal beforeAmt;

    /**
    * 操作后金额
    */
    @ApiModelProperty(value="操作后金额")
    private BigDecimal afterAmt;

    /**
    * 交易金额
    */
    @ApiModelProperty(value="交易金额")
    private BigDecimal txnAmt;

    /**
    * 交易日期
    */
    @ApiModelProperty(value="交易日期")
    private Date txnDt;

    /**
    * 交易渠道
    */
    @ApiModelProperty(value="交易渠道")
    private String transChnl;

    /**
    * 银行卡号
    */
    @ApiModelProperty(value="银行卡号")
    private String bankNo;

    /**
    * 银行联行号
    */
    @ApiModelProperty(value="银行联行号")
    private String bankPayNo;

    /**
    * 银行名称
    */
    @ApiModelProperty(value="银行名称")
    private String openAccuName;

    /**
    * 状态 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过
    */
    @ApiModelProperty(value="状态 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过")
    private String status;

    /**
    * 摘要(操作说明)
    */
    @ApiModelProperty(value="摘要(操作说明)")
    private String summary;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 操作类型 0-加余额 1-减余额
    */
    @ApiModelProperty(value="操作类型 0-加余额 1-减余额")
    private String operTp;

    /**
    * 操作员
    */
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
    * 复核员
    */
    @ApiModelProperty(value="复核员")
    private String auditor;

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