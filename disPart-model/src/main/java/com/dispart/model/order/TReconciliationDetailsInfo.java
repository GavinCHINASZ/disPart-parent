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

@ApiModel(value="com-dispart-model-order-TReconciliationDetailsInfo")
@Data
@TableName(value = "logistics.t_reconciliation_details_info")
public class TReconciliationDetailsInfo {

    @TableId(value = "MAIN_ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String mainOrderId;

    @TableId(value = "PAYMENT_TRACE_ID", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String paymentTraceId;

    @TableField(value = "MARKET_ID")
    @ApiModelProperty(value="")
    private String marketId;

    @TableField(value = "PAYEE_ID")
    @ApiModelProperty(value="")
    private String payeeId;

    @TableField(value = "PAYEE_NM")
    @ApiModelProperty(value="")
    private String payeeNm;

    @TableField(value = "TXN_DT")
    @ApiModelProperty(value="")
    private Date txnDt;

    @TableField(value = "TXN_TM")
    @ApiModelProperty(value="")
    private Date txnTm;

    @TableField(value = "ORDER_TYPE")
    @ApiModelProperty(value="")
    private String orderType;

    @TableField(value = "TXN_AMT")
    @ApiModelProperty(value="")
    private BigDecimal txnAmt;

    @TableField(value = "SERV_CHGR")
    @ApiModelProperty(value="")
    private BigDecimal servChgr;

    @TableField(value = "PAYEE_ST")
    @ApiModelProperty(value="")
    private String payeeSt;

    @TableField(value = "TXN_TYPE")
    @ApiModelProperty(value="")
    private String txnType;

    @TableField(value = "OPEN_BANK")
    @ApiModelProperty(value="")
    private String openBank;

    @TableField(value = "CARD_TYPE")
    @ApiModelProperty(value="")
    private String cardType;

    @TableField(value = "ACCOUNT")
    @ApiModelProperty(value="")
    private String account;

    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="")
    private Date updateDt;

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