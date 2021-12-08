package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TReconciliationDetailsInfoTmp {

    @YHHIndex(index = 0)
    private String mainOrderId;

    @YHHIndex(index = 4)
    private String paymentTraceId;

    @YHHIndex(index = 1)
    private String marketId;

    @YHHIndex(index = 2)
    private String marketName;

    private String payeeId;

    private String payeeNm;


    private String orderTpcd;

//    @YHHIndex(index = 3)
    private Date txnDt;

    @YHHIndex(index = 3)
    private String txnDtTmp;

    private Date txnTm;

    @YHHIndex(index = 13)
    private String txtTmTmp;

    @YHHIndex(index = 5)
    private String orderType;

    private BigDecimal txnAmt;

    @YHHIndex(index = 6)
    private String txnAmtTmp;

    private BigDecimal servChgr;

    @YHHIndex(index = 7)
    private String servCharTmp;

    @YHHIndex(index = 8)
    private String payeeSt;

    @YHHIndex(index = 9)
    private String txnType;

    @YHHIndex(index = 10)
    private String openBank;

    @YHHIndex(index = 11)
    private String cardType;

    @YHHIndex(index = 12)
    private String account;

    private Date updateDt;

    private String updateDtTmp;

    public static final String COL_PAYMENT_TRACE_ID = "PAYMENT_TRACE_ID";

    public static final String COL_MARKET_ID = "MARKET_ID";

    public static final String COL_PAYEE_ID = "PAYEE_ID";

    public static final String COL_PAYEE_NM = "PAYEE_NM";

    public static final String COL_TXN_DT = "TXN_DT";

    public static final String COL_TXN_TM = "TXN_TM";

    public static final String COL_ORDER_TYPE = "ORDER_TYPE";

    public static final String COL_TXN_AMT = "TXN_AMT";

    public static final String COL_SERV_CHGR = "SERV_CHGR";

    public static final String COL_PAYEE_ST = "PAYEE_ST";

    public static final String COL_TXN_TYPE = "TXN_TYPE";

    public static final String COL_OPEN_BANK = "OPEN_BANK";

    public static final String COL_CARD_TYPE = "CARD_TYPE";

    public static final String COL_ACCOUNT = "ACCOUNT";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}