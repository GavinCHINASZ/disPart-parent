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
    * 流水表
    */
@ApiModel(value="com-dispart-tmp-TPayJrn")
@Data
@TableName(value = "logistics.t_pay_jrn")
public class TPayJrn {
    /**
     * 流水号
     */
    @TableField(value = "CHANL_NO")
    @ApiModelProperty(value="渠道号")
    private String chanlNo;

    /**
     * 流水号/自生成
     */
    @TableId(value = "JRNL_NUM", type = IdType.INPUT)
    @ApiModelProperty(value="流水号/自生成")
    private String jrnlNum;

    /**
     * 业务号
     */
    @TableField(value = "BUSINESS_NO")
    @ApiModelProperty(value="业务号")
    private String businessNo;

    /**
     * 凭证号
     */
    @TableField(value = "CREDIT_NO")
    @ApiModelProperty(value="CREDIT_NO")
    private String creditNo;

    /**
     * 交易类型 0-充值 1-提现 2-进场费 3-停车费 4-退款
     */
    @TableField(value = "TXN_TYPE")
    @ApiModelProperty(value="交易类型 0-充值 1-提现 2-进场费 3-停车费 4-退款")
    private String txnType;

    /**
     * 方式
     */
    @TableField(value = "TRANS_MD")
    @ApiModelProperty(value="方式")
    private String transMd;

    /**
     * 交易时间
     */
    @TableField(value = "TXN_TM")
    @ApiModelProperty(value="交易时间")
    private Date txnTm;

    /**
     * 交易金额
     */
    @TableField(value = "TXN_AMT")
    @ApiModelProperty(value="交易金额")
    private BigDecimal txnAmt;

    /**
     * 付款人编号
     */
    @TableField(value = "PAYER_NO")
    @ApiModelProperty(value="付款人编号")
    private String payerNo;

    /**
     * 付款人名称
     */
    @TableField(value = "PAY_NAME")
    @ApiModelProperty(value="付款人名称")
    private String payName;

    /**
     * 付款人卡号
     */
    @TableField(value = "PAY_CARD")
    @ApiModelProperty(value="付款人卡号")
    private String payCard;

    /**
     * 付款人账号
     */
    @TableField(value = "PAY_ACCT")
    @ApiModelProperty(value="付款人账号")
    private String payAcct;

    /**
     * 收款人编号
     */
    @TableField(value = "PAYEE_NUM")
    @ApiModelProperty(value="收款人编号")
    private String payeeNum;

    /**
     * 收款人编号
     */
    @TableField(value = "MAIN_ORDER_ID")
    @ApiModelProperty(value="主订单编号")
    private String mainOrderId;

    /**
     * 收款人名称
     */
    @TableField(value = "PAYEE")
    @ApiModelProperty(value="收款人名称")
    private String payee;

    /**
     *                                                                                                            收款人账号
     */
    @TableField(value = "PAYEE_ACCT")
    @ApiModelProperty(value="收款人账号")
    private String payeeAcct;

    /**
     * 摘要
     */
    @TableField(value = "SUMMARY")
    @ApiModelProperty(value="摘要")
    private String summary;

    /**
     * 状态 0-待支付 1-支付成功  2-支付失败
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value="状态 0-待支付 1-支付成功  2-支付失败")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
     * 操作员
     */
    @TableField(value = "PAYEE_CARD")
    @ApiModelProperty(value="收款人卡号")
    private String payeeCard;

    /*
     * pos订单号
     */
    @TableField(value = "POS_ORDER_ID")
    @ApiModelProperty(value="POS订单号")
    private String posOrderId;

    /**
     * 创建时间
     */
    @TableField(value = "CREAT_TIME")
    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @TableField(value = "UP_TIME")
    @ApiModelProperty(value="更新时间")
    private Date upTime;

    /**
     * 手续费率
     */
    @TableField(value = "RATE")
    @ApiModelProperty(value="手续费率")
    private BigDecimal rate;

    /**
     * 手续费
     */
    @TableField(value = "CHARGES")
    @ApiModelProperty(value="手续费")
    private BigDecimal charges;


    public static final String COL_JRNL_NUM = "JRNL_NUM";

    public static final String COL_BUSINESS_NO = "BUSINESS_NO";

    public static final String COL_TXN_TYPE = "TXN_TYPE";

    public static final String COL_TRANS_MD = "TRANS_MD";

    public static final String COL_TXN_TM = "TXN_TM";

    public static final String COL_TXN_AMT = "TXN_AMT";

    public static final String COL_PAYER_NO = "PAYER_NO";

    public static final String COL_PAY_NAME = "PAY_NAME";

    public static final String COL_PAY_CARD = "PAY_CARD";

    public static final String COL_PAY_ACCT = "PAY_ACCT";

    public static final String COL_PAYEE_NUM = "PAYEE_NUM";

    public static final String COL_PAYEE = "PAYEE";

    public static final String COL_PAYEE_ACCT = "PAYEE_ACCT";

    public static final String COL_SUMMARY = "SUMMARY";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}