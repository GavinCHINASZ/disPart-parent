package com.dispart.model.order;

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
    * 出场退费一卡通任务表
    */
@ApiModel(value="com-dispart-tmp-TCardReturnTask")
@Data
@TableName(value = "logistics.t_card_return_task")
public class TCardReturnTask {
    /**
     * 任务ID
     */
    @TableId(value = "TASK_ID", type = IdType.AUTO)
    @ApiModelProperty(value="任务ID")
    private Integer taskId;

    /**
     * 任务日期
     */
    @TableField(value = "TASK_DT")
    @ApiModelProperty(value="任务日期")
    private Date taskDt;

    /**
     * 客户编号
     */
    @TableField(value = "PROV_ID")
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
     * 卡号
     */
    @TableField(value = "CARD_NO")
    @ApiModelProperty(value="卡号")
    private String cardNo;

    /**
     * 账户号
     */
    @TableField(value = "ACCOUNT")
    @ApiModelProperty(value="账户号")
    private String account;

    /**
     * 退费金额
     */
    @TableField(value = "AMOUNT")
    @ApiModelProperty(value="退费金额")
    private BigDecimal amount;

    /**
     * 用途
     */
    @TableField(value = "USE_OF")
    @ApiModelProperty(value="用途")
    private String useOf;

    /**
     * 流水表流水号
     */
    @TableField(value = "JRNL_NUM")
    @ApiModelProperty(value="流水表流水号")
    private String jrnlNum;

    /**
     * 退费状态 0-待退费 1-待查询 2-完成
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value="退费状态 0-待退费 1-待查询 2-完成")
    private String status;

    /**
     * 银企直连请求序列号，查询交易使用
     */
    @TableField(value = "REQUEST_SN")
    @ApiModelProperty(value="银企直连请求序列号，查询交易使用")
    private String requestSn;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 预充值完了的流水号
     */
    @TableField(value = "CHARGE_JRNL_NUM")
    @ApiModelProperty(value="预充值完了的流水号")
    private String chargeJrnlNum;

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
     * 车出场时间
     */
    @TableField(value = "OUT_TIME")
    @ApiModelProperty(value="车辆出场时间")
    private Date outTime;

    /**
     * 代充值时间
     */
    @TableField(value = "CHARGE_TIME")
    @ApiModelProperty(value="代出场时间")
    private Date chargeTime;

    /**
     * 操作人
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value="操作员")
    private String OperId;

    /**
     * 出场办理人
     */
    @TableField(value = "OUT_OPER_ID")
    @ApiModelProperty(value="出场办理人")
    private String outOperId;

    public static final String COL_TASK_ID = "TASK_ID";

    public static final String COL_TASK_DT = "TASK_DT";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_CARD_NO = "CARD_NO";

    public static final String COL_ACCOUNT = "ACCOUNT";

    public static final String COL_AMOUNT = "AMOUNT";

    public static final String COL_USE_OF = "USE_OF";

    public static final String COL_JRNL_NUM = "JRNL_NUM";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_REQUEST_SN = "REQUEST_SN";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}