package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TApportionSumInfoTmp {
    /**
     * 市场编号
     */
    private String marketId;

    /**
     * 分账日期
     */
    private Date partDt;

    @YHHIndex(index = 6)
    private String partDtTmp;

    /**
     * 收款方商家编号
     */
    @YHHIndex(index = 1)
    private String payeeId;

    /**
     * 交易流水号
     */
    @YHHIndex(index = 0)
    private String txnTraceId;

    /**
     * 收款方商家名称
     */
    @YHHIndex(index = 2)
    private String payeeNm;

    /**
     * 分账金额
     */
    private BigDecimal partAmt;

    @YHHIndex(index = 3)
    private String partAmtTmp;

    /**
     * 分账失败原因
     */
    @YHHIndex(index = 4)
    private String respInfo;

    /**
     * 分账状态 2-分账成功 4-分账异常
     */
    @YHHIndex(index = 5)
    private String respSt;

    /**
     * 原清算交易流水号
     */
    private String oriTxnTraceId;

    /**
     * 分账日期时间
     */
    private Date partDtTm;

    private String partDtTmTmp;

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