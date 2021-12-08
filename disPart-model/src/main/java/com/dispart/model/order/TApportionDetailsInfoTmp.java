package com.dispart.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TApportionDetailsInfoTmp {

    private String marketId;


    @YHHIndex(index = 0)
    private String mainOrderId;

    /**
     * 序号
     */
    @YHHIndex(index = 1)
    private String serNum;

    /**
     * 支付流水号
     */
    @YHHIndex(index = 2)
    private String paymentTraceId;

    /**
     * 收款账户
     */
    @YHHIndex(index = 3)
    private String payeeAcct;

    /**
     * 收款方商家编号
     */
    @YHHIndex(index = 4)
    private String payeeId;

    /**
     * 收款方商家名称
     */
    @YHHIndex(index = 5)
    private String payeeNm;

    /**
     * 分账金额
     */
    private BigDecimal partAmt;

    @YHHIndex(index = 6)
    private String tempPartAmt;

    /**
     * 分账状态
     */
    @YHHIndex(index = 7)
    private String respSt;

    /**
     * 分账日期
     */
    private Date partDt;

    @YHHIndex(index = 8)
    private String tmpPartDt;

    /**
     * 手续费
     */
    private BigDecimal servChrg;

    @YHHIndex(index = 9)
    private String servChrgTmp;


    /**
     * 子订单号
     */
    @YHHIndex(index = 10)
    private String subOrderId;

    /**
     * 原始分账金额
     */
    private BigDecimal oriPartAmt;

    /**
     * 重新出发分账日期时间
     */
    private Date oriPartDt;

    /**
     * 更新时间戳
     */
    private Date updateDt;

    public static final String COL_MARKET_ID = "MARKET_ID";

    public static final String COL_SER_NUM = "SER_NUM";

    public static final String COL_PAYMENT_TRACE_ID = "PAYMENT_TRACE_ID";

    public static final String COL_PAYEE_ACCT = "PAYEE_ACCT";

    public static final String COL_PAYEE_ID = "PAYEE_ID";

    public static final String COL_PAYEE_NM = "PAYEE_NM";

    public static final String COL_PART_AMT = "PART_AMT";

    public static final String COL_RESP_ST = "RESP_ST";

    public static final String COL_PART_DT = "PART_DT";

    public static final String COL_SERV_CHRG = "SERV_CHRG";

    public static final String COL_SUB_ORDER_ID = "SUB_ORDER_ID";

    public static final String COL_ORI_PART_AMT = "ORI_PART_AMT";

    public static final String COL_ORI_PART_DT = "ORI_PART_DT";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}