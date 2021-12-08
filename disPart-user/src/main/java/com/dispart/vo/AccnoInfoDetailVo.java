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
 * @date:Created in 2021/8/13 11:28
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
@ApiModel(description = "账户操作记录")
@TableName("t_accno_info_details")
public class AccnoInfoDetailVo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现 12现金提现
     */
    public static final String OPEN_ACCOUNT = "0";
    public static final String FREEZE = "1";
    public static final String RECONCILIATION = "2";
    public static final String WITHDRAW = "3";
    public static final String REPORT_LOSS = "6";
    public static final String CHANGE_PASS = "7";
    public static final String RESET_PASS = "8";
    public static final String LOGOUT = "9";
    public static final String UNFREEZE_ACCOUNT = "10";
    public static final String UNTIE_CARD = "11";
    public static final String CASH_WITHDRAW = "12";
    public static final String REPLACE_CARD = "4";

    //1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过
    public static final String DEFAUILT = "0";
    public static final String APPLY_RECONCILIATION = "1";
    public static final String RECONCILED = "2";
    public static final String APPLY_WITHDRAWAL = "3";
    public static final String WITHDRAWAL_REVIEW = "4";
    public static final String WITHDRAWN = "5";
    public static final String REVIEW_FAIL = "6";
    public static final String WITHDRAWAL_FAIL = "7";

    @ApiModelProperty(value = "流水号")
    @TableField("ID")
    private String iD;

    @ApiModelProperty(value = "客户编号")
    @TableField("prov_id")
    private String provId;

    //客户名称
    @ApiModelProperty(value = "客户名称")
    @TableField("prov_nm")
    private String provNm;

    @ApiModelProperty(value = "账户")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "卡号")
    @TableField("CARD_NO")
    private String cardNo;

    @ApiModelProperty(value = "交易类型 0-开户 1-冻结 2-调账 3-提现 6-挂失 7-修改密码 8-重置密码 9-销户")
    @TableField("TXN_TYPE")
    private String txnType;

    @ApiModelProperty(value = "操作前金额")
    @TableField("BEFORE_AMT")
    private BigDecimal beforeAmt;

    @ApiModelProperty(value = "操作后金额")
    @TableField("AFTER_AMT")
    private BigDecimal afterAmt;

    @ApiModelProperty(value = "交易金额")
    @TableField("TXN_AMT")
    private BigDecimal txnAmt;

    @ApiModelProperty(value = "交易日期")
    @TableField("TXN_DT")
    private Date txnDt;
    /**
     * 交易查询开始时间
     */
    private Date txnStDate;
    /**
     * 交易查询结束时间
     */
    private Date txnEndDate;

    @ApiModelProperty(value = "交易渠道")
    @TableField("TRANS_CHNL")
    private String transChnl;

    @ApiModelProperty(value = "摘要(操作说明)")
    @TableField("SUMMARY")
    private String summary;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = " 操作类型 0-加余额 1-减余额")
    @TableField("OPER_TP")
    private String operTp;

    @ApiModelProperty(value = "操作员")
    @TableField("OPER_ID")
    private String operId;

    private String operName;

    @ApiModelProperty(value = "创建时间")
    @TableField("creat_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    @TableField("up_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;

    // 状态 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过
    @ApiModelProperty(value = "操作员")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "提现开卡名称")
    @TableField("OPEN_ACCU_NAME")
    private String openAccuName;

    @ApiModelProperty(value = "开卡人姓名")
    @TableField("OPEN_ACCU_NM")
    private String openAccuNm;

    @ApiModelProperty(value = "开卡人卡号")
    @TableField("BANK_NO")
    private String bankNo;

    @ApiModelProperty(value = "银行联行号")
    @TableField("BANK_PAY_NO")
    private String bankPayNo;

    /**
     * 渠道号
     */
    @TableField(value = "CHANL_NO")
    @ApiModelProperty(value = "渠道号")
    private String chanlNo;


    /* 分页条数 */
    private Long pageSize;
    /* 分页页数 */
    private Long pageNum;
    /* 起始记录数 */
    private Long startIndex;
    /* 调账金额 (提现金额)*/
    private BigDecimal applyAmount;
    /*确认密码 */
    private String verifyUsPaWd;
    /*流水号 */
    private String reId;
    /*密码 */
    private String passwd;

    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal charges;

    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    private BigDecimal acctBal;

    /**
     * 可用余额
     */
    @ApiModelProperty(value = "可用余额")
    private BigDecimal availBal;

    //用户手机号
    private String userPhone;
    //用户密码
    private String newUsPaWd;
    //验证码
    private String regCode;
    //身份证号码
    private String certNum;
    //手机号码
    private String phone;
    //操作员
    private String openId;
    //调账标志
    private String reviewStatus;

}
