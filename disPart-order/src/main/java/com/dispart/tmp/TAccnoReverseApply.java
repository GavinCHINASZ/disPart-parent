package com.dispart.tmp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
    * 冲正申请表
    */
@Data
@TableName(value = "t_accno_reverse_apply")
public class TAccnoReverseApply {
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
     * 申请人ID
     */
    @TableField(value = "OPER_ID")
    private String operId;

    /**
     * 申请时间
     */
    @TableField(value = "OPER_TM")
    private Date operTm;

    /**
     * 复核人ID
     */
    @TableField(value = "CHECK_ID")
    private String checkId;

    /**
     * 复核时间
     */
    @TableField(value = "CHECK_TM")
    private Date checkTm;

    /**
     * 复核状态 0-待复核 1-已驳回 2-冲正完成
     */
    @TableField(value = "CHECK_ST")
    private String checkSt;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;

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

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_OPER_TM = "OPER_TM";

    public static final String COL_CHECK_ID = "CHECK_ID";

    public static final String COL_CHECK_TM = "CHECK_TM";

    public static final String COL_CHECK_ST = "CHECK_ST";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_SUB_ORG = "SUB_ORG";

    public static final String COL_DEP_ID = "DEP_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}