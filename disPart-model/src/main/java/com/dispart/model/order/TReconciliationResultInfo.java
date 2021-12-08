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

@ApiModel(value = "com-disPart-test-TReconciliationResultInfo")
@Data
@TableName(value = "logistics.t_reconciliation_result_info")
public class TReconciliationResultInfo {
    /**
     * 交易流水号
     */
    @TableId(value = "PAYMENT_TRACE_ID", type = IdType.INPUT)
    @ApiModelProperty(value = "交易流水号")
    private String paymentTraceId;

    /**
     * 市场编号
     */
    @TableField(value = "MARKET_ID")
    @ApiModelProperty(value = "市场编号")
    private String marketId;

    /**
     * 收款方商家编号
     */
    @TableField(value = "PAYEE_ID")
    @ApiModelProperty(value = "收款方商家编号")
    private String payeeId;

    /**
     * 收款方商家名称
     */
    @TableField(value = "PAYEE_NM")
    @ApiModelProperty(value = "收款方商家名称")
    private String payeeNm;

    /**
     * 交易日期
     */
    @TableField(value = "TXN_DT")
    @ApiModelProperty(value = "交易日期")
    private Date txnDt;

    /**
     * 交易时间
     */
    @TableField(value = "TXN_TM")
    @ApiModelProperty(value = "交易时间")
    private Date txnTm;

    /**
     * 交易金额
     */
    @TableField(value = "TXN_AMT")
    @ApiModelProperty(value = "交易金额")
    private BigDecimal txnAmt;

    /**
     * 对账结果
     */
    @TableField(value = "RECON_RSLT")
    @ApiModelProperty(value = "对账结果")
    private String reconRslt;

    /**
     * 原因
     */
    @TableField(value = "CAUSE")
    @ApiModelProperty(value = "原因")
    private String cause;

    /**
     * 主订单号
     */
    @TableField(value = "MAIN_ORDER_NO")
    @ApiModelProperty(value = "主订单号")
    private String mainOrderNo;

    /**
     * 子订单号
     */
    @TableField(value = "ORDER_ID")
    @ApiModelProperty(value = "子订单号")
    private String orderId;

    /**
     * 惠市宝主订单号
     */
    @TableField(value = "CCB_ORDER_ID")
    @ApiModelProperty(value = "惠市宝主订单号")
    private String ccbOrderId;

    /**
     * 惠市宝子订单号
     */
    @TableField(value = "SUB_ORDER_ID")
    @ApiModelProperty(value = "惠市宝子订单号")
    private String subOrderId;

    /**
     * 分账金额
     */
    @TableField(value = "PART_AMT")
    @ApiModelProperty(value = "分账金额")
    private BigDecimal partAmt;

    /**
     * 分账状态
     */
    @TableField(value = "RESP_ST")
    @ApiModelProperty(value = "分账状态")
    private String respSt;

    /**
     * 分账日期
     */
    @TableField(value = "PART_DT")
    @ApiModelProperty(value = "分账日期")
    private Date partDt;

    /**
     * 时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value = "时间戳")
    private Date updateDt;

    public static final String COL_PAYMENT_TRACE_ID = "PAYMENT_TRACE_ID";

    public static final String COL_MARKET_ID = "MARKET_ID";

    public static final String COL_PAYEE_ID = "PAYEE_ID";

    public static final String COL_PAYEE_NM = "PAYEE_NM";

    public static final String COL_TXN_DT = "TXN_DT";

    public static final String COL_TXN_TM = "TXN_TM";

    public static final String COL_TXN_AMT = "TXN_AMT";

    public static final String COL_RECON_RSLT = "RECON_RSLT";

    public static final String COL_CAUSE = "CAUSE";

    public static final String COL_MAIN_ORDER_NO = "MAIN_ORDER_NO";

    public static final String COL_ORDER_ID = "ORDER_ID";

    public static final String COL_CCB_ORDER_ID = "CCB_ORDER_ID";

    public static final String COL_SUB_ORDER_ID = "SUB_ORDER_ID";

    public static final String COL_PART_AMT = "PART_AMT";

    public static final String COL_RESP_ST = "RESP_ST";

    public static final String COL_PART_DT = "PART_DT";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}