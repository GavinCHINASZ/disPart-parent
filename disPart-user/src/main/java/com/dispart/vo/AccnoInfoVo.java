package com.dispart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:xiejin
 * @date:Created in 2021/8/11 15:07
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
@ApiModel(description = "客户账户信息")
@TableName("t_accno_info")
public class AccnoInfoVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户编号")
    @TableField("prov_id")
    private String provId;

    @ApiModelProperty(value = "账户")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "账户余额")
    @TableField("acct_bal")
    private BigDecimal acctBal;

    @ApiModelProperty(value = "可用余额")
    @TableField("avail_bal")
    private BigDecimal availBal;

    @ApiModelProperty(value = "冻结金额")
    @TableField("freeze_amt")
    private BigDecimal freezeAmt;

    @ApiModelProperty(value = "日贷记金额")
    @TableField("dcredit_amt")
    private BigDecimal dcreditAmt;

    @ApiModelProperty(value = "日借记金额")
    @TableField("ddebit_amt")
    private BigDecimal ddebitAmt;

    @ApiModelProperty(value = "月贷记金额")
    @TableField("mcredit_amt")
    private BigDecimal mcreditAmt;

    @ApiModelProperty(value = "月借记金额")
    @TableField("mdebit_amt")
    private BigDecimal mdebitAmt;

    @ApiModelProperty(value = "年贷记金额")
    @TableField("ycredit_amt")
    private BigDecimal ycreditAmt;

    @ApiModelProperty(value = "年借记金额")
    @TableField("ydebit_amt")
    private BigDecimal ydebitAmt;

    @ApiModelProperty(value = "积数(利息使用)")
    @TableField("acc_amt")
    private BigDecimal accAmt;

    @ApiModelProperty(value = "Mac校验值")
    @TableField("mac")
    private String mac;

    @ApiModelProperty(value = "日结日期 yyyyMMdd")
    @TableField("day_dt")
    private Date dayDt;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "操作员")
    @TableField("oper_id")
    private String operId;

    @ApiModelProperty(value = "创建时间")
    @TableField("creat_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("up_time")
    private Date upTime;

    /**
     * 调账金额/提现金额
     */
    private BigDecimal applyAmount;
}
