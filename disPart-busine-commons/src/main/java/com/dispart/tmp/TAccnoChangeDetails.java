package com.dispart.tmp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 账户明细表
 */
@Data
@TableName(value = "t_accno_change_details")
public class TAccnoChangeDetails {
    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 流水号
     */
    @TableField(value = "JRNL_NUM")
    private String jrnlNum;

    /**
     * 一卡通卡号
     */
    @TableField(value = "CARD_NO")
    private String cardNo;

    /**
     * 客户编号
     */
    @TableField(value = "PROV_ID")
    private String provId;

    /**
     * 客户名称
     */
    @TableField(value = "PROV_NM")
    private String provNm;

    /**
     * 交易类型
     */
    @TableField(value = "TXN_TYPE")
    private String txnType;

    /**
     * 交易方式
     */
    @TableField(value = "TRANS_MD")
    private String transMd;

    /**
     * 进账类型 0-进账 1-出账
     */
    @TableField(value = "INCOME_TP")
    private String incomeTp;

    /**
     * 账户余额
     */
    @TableField(value = "ACCT_BAL")
    private BigDecimal acctBal;

    /**
     * 交易前可用余额
     */
    @TableField(value = "BEFORE_AMT")
    private BigDecimal beforeAmt;

    /**
     * 交易后可用余额
     */
    @TableField(value = "AFTER_AMT")
    private BigDecimal afterAmt;

    /**
     * 交易金额
     */
    @TableField(value = "TXN_AMT")
    private BigDecimal txnAmt;

    /**
     * 交易时间
     */
    @TableField(value = "TXN_TM")
    private Date txnTm;

    /**
     * 摘要
     */
    @TableField(value = "SUMMARY")
    private String summary;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;

    /**
     * 操作人
     */
    @TableField(value = "OPER_ID")
    private String operId;

    /**
     * 所属机构
     */
    @TableField(value = "SUB_ORG")
    private String subOrg;

    /**
     * 部门编号
     */
    @TableField(value = "DEP_ID")
    private String depId;

    /**
     * 创建时间
     */
    @TableField(value = "CREAT_TIME")
    private Date creatTime;

    /**
     * 更新时间
     */
    @TableField(value = "UP_TIME")
    private Date upTime;

    public static final String COL_ID = "ID";

    public static final String COL_JRNL_NUM = "JRNL_NUM";

    public static final String COL_CARD_NO = "CARD_NO";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_PROV_NM = "PROV_NM";

    public static final String COL_TXN_TYPE = "TXN_TYPE";

    public static final String COL_TRANS_MD = "TRANS_MD";

    public static final String COL_INCOME_TP = "INCOME_TP";

    public static final String COL_ACCT_BAL = "ACCT_BAL";

    public static final String COL_BEFORE_AMT = "BEFORE_AMT";

    public static final String COL_AFTER_AMT = "AFTER_AMT";

    public static final String COL_TXN_AMT = "TXN_AMT";

    public static final String COL_TXN_TM = "TXN_TM";

    public static final String COL_SUMMARY = "SUMMARY";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_SUB_ORG = "SUB_ORG";

    public static final String COL_DEP_ID = "DEP_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}