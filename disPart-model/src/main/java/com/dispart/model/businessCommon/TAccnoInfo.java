package com.dispart.model.businessCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 客户账户信息
 */
@ApiModel(value = "com-dispart-tmp-TAccnoInfo")
@Data
@TableName(value = "logistics.t_accno_info")
public class TAccnoInfo {
    /**
     * 账户
     */
    @TableId(value = "ACCOUNT", type = IdType.INPUT)
    @ApiModelProperty(value = "账户")
    private String account;

    /**
     * 客户编号
     */
    @TableField(value = "PROV_ID")
    @ApiModelProperty(value = "客户编号")
    private String provId;

    /**
     * 账户余额
     */
    @TableField(value = "ACCT_BAL")
    @ApiModelProperty(value = "账户余额")
    private BigDecimal acctBal;

    /**
     * 可用余额
     */
    @TableField(value = "AVAIL_BAL")
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availBal;

    /**
     * 冻结金额
     */
    @TableField(value = "FREEZE_AMT")
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal freezeAmt;

    /**
     * 日贷记金额
     */
    @TableField(value = "DCREDIT_AMT")
    @ApiModelProperty(value = "日贷记金额")
    private BigDecimal dcreditAmt;

    /**
     * 日借记金额
     */
    @TableField(value = "DDEBIT_AMT")
    @ApiModelProperty(value = "日借记金额")
    private BigDecimal ddebitAmt;

    /**
     * 月贷记金额
     */
    @TableField(value = "MCREDIT_AMT")
    @ApiModelProperty(value = "月贷记金额")
    private BigDecimal mcreditAmt;

    /**
     * 月借记金额
     */
    @TableField(value = "MDEBIT_AMT")
    @ApiModelProperty(value = "月借记金额")
    private BigDecimal mdebitAmt;

    /**
     * 年贷记金额
     */
    @TableField(value = "YCREDIT_AMT")
    @ApiModelProperty(value = "年贷记金额")
    private BigDecimal ycreditAmt;

    /**
     * 年借记金额
     */
    @TableField(value = "YDEBIT_AMT")
    @ApiModelProperty(value = "年借记金额")
    private BigDecimal ydebitAmt;

    /**
     * 积数(利息使用)
     */
    @TableField(value = "ACC_AMT")
    @ApiModelProperty(value = "积数(利息使用)")
    private BigDecimal accAmt;

    /**
     * Mac校验值
     */
    @TableField(value = "MAC")
    @ApiModelProperty(value = "Mac校验值")
    private String mac;

    /**
     * 日结日期 yyyyMMdd
     */
    @TableField(value = "DAY_DT")
    @ApiModelProperty(value = "日结日期 yyyyMMdd")
    private Date dayDt;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value = "操作员")
    private String operId;

    /**
     * 创建时间
     */
    @TableField(value = "CREAT_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @TableField(value = "UP_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date upTime;

    /**
     * 渠道号
     */
    @TableField(value = "CHANL_NO")
    @ApiModelProperty(value = "渠道号")
    private String chanlNo;


    public static final String COL_ACCOUNT = "ACCOUNT";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_ACCT_BAL = "ACCT_BAL";

    public static final String COL_AVAIL_BAL = "AVAIL_BAL";

    public static final String COL_FREEZE_AMT = "FREEZE_AMT";

    public static final String COL_DCREDIT_AMT = "DCREDIT_AMT";

    public static final String COL_DDEBIT_AMT = "DDEBIT_AMT";

    public static final String COL_MCREDIT_AMT = "MCREDIT_AMT";

    public static final String COL_MDEBIT_AMT = "MDEBIT_AMT";

    public static final String COL_YCREDIT_AMT = "YCREDIT_AMT";

    public static final String COL_YDEBIT_AMT = "YDEBIT_AMT";

    public static final String COL_ACC_AMT = "ACC_AMT";

    public static final String COL_MAC = "MAC";

    public static final String COL_DAY_DT = "DAY_DT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}