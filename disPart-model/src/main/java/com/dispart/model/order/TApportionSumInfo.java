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

@ApiModel(value="com-dispart-model-order-TApportionSumInfo")
@Data
@TableName(value = "logistics.t_apportion_sum_info")
public class TApportionSumInfo {
    /**
     * 市场编号
     */
    @TableId(value = " MARKET_ID", type = IdType.INPUT)
    @ApiModelProperty(value="市场编号")
    private String marketId;

    /**
     * 分账日期
     */
    @TableId(value = "PART_DT", type = IdType.INPUT)
    @ApiModelProperty(value="分账日期")
    private Date partDt;

    /**
     * 收款方商家编号
     */
    @TableId(value = "PAYEE_ID", type = IdType.INPUT)
    @ApiModelProperty(value="收款方商家编号")
    private String payeeId;

    /**
     * 交易流水号
     */
    @TableField(value = "TXN_TRACE_ID")
    @ApiModelProperty(value="交易流水号")
    private String txnTraceId;

    /**
     * 收款方商家名称
     */
    @TableField(value = "PAYEE_NM")
    @ApiModelProperty(value="收款方商家名称")
    private String payeeNm;

    /**
     * 分账金额
     */
    @TableField(value = "PART_AMT")
    @ApiModelProperty(value="分账金额")
    private BigDecimal partAmt;

    /**
     * 分账失败原因
     */
    @TableField(value = "RESP_INFO")
    @ApiModelProperty(value="分账失败原因")
    private String respInfo;

    /**
     * 分账状态 2-分账成功 4-分账异常
     */
    @TableField(value = "RESP_ST")
    @ApiModelProperty(value="分账状态 2-分账成功 4-分账异常")
    private String respSt;

    /**
     * 原清算交易流水号
     */
    @TableField(value = "ORI_TXN_TRACE_ID")
    @ApiModelProperty(value="原清算交易流水号")
    private String oriTxnTraceId;

    /**
     * 分账日期时间
     */
    @TableField(value = "PART_DT_TM")
    @ApiModelProperty(value="分账日期时间")
    private Date partDtTm;

    /**
     * 更新时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="更新时间戳")
    private Date updateDt;

    public static final String COL_MARKET_ID = " MARKET_ID";

    public static final String COL_PART_DT = "PART_DT";

    public static final String COL_PAYEE_ID = "PAYEE_ID";

    public static final String COL_TXN_TRACE_ID = "TXN_TRACE_ID";

    public static final String COL_PAYEE_NM = "PAYEE_NM";

    public static final String COL_PART_AMT = "PART_AMT";

    public static final String COL_RESP_INFO = "RESP_INFO";

    public static final String COL_RESP_ST = "RESP_ST";

    public static final String COL_ORI_TXN_TRACE_ID = "ORI_TXN_TRACE_ID";

    public static final String COL_PART_DT_TM = "PART_DT_TM";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}