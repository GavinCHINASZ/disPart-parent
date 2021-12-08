package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-dispart-model-order-TOrderInfo")
@Data
@TableName(value = "logistics.t_order_info")
public class TOrderInfo {
    /**
     * 主订单状态
     */
    @TableId(value = "MAIN_ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value = "主订单ID")
    private String mainOrderId;

    /**
     * 主订单ID
     */
    @TableId(value = "ORDER_ST", type = IdType.INPUT)
    @ApiModelProperty(value = "主订单状态")
    private String orderSt;

    /**
     * 支付方式
     */
    @TableField(value = "PAYMENT_MODE")
    @ApiModelProperty(value = "支付方式")
    private String paymentMode;

    /**
     * 订单类型
     */
    @TableField(value = "ORDER_TYPE")
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    /**
     * 市场编号
     */
    @TableField(value = "MARKET_ID")
    @ApiModelProperty(value = "市场编号")
    private String marketId;

    /**
     * 订单总金额
     */
    @TableField(value = "ORDER_TOT_AMT")
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderTotAmt;

    /**
     * 交易总金额
     */
    @TableField(value = "TXN_TOT_AMT")
    @ApiModelProperty(value = "交易总金额")
    private BigDecimal txnTotAmt;

    /**
     * 建行钱包合约号
     */
    @TableField(value = "CCB_WALLET_NO")
    @ApiModelProperty(value = "建行钱包合约号")
    private String ccbWalletNo;

    /**
     * 第三方APP平台号
     */
    @TableField(value = "APP_ID")
    @ApiModelProperty(value = "第三方APP平台号")
    private String appId;

    /**
     * 钱包名称
     */
    @TableField(value = "WALLET_NM")
    @ApiModelProperty(value = "钱包名称")
    private String walletNm;

    /**
     * 支付结果通知序列号
     */
    @TableField(value = "PAYMENT_RSLT")
    @ApiModelProperty(value = "支付结果通知序列号")
    private String paymentRslt;

    /**
     * 银行编码
     */
    @TableField(value = "BANK_CD")
    @ApiModelProperty(value = "银行编码")
    private String bankCd;

    /**
     * 支付URL
     */
    @TableField(value = "PAYMENT_URL")
    @ApiModelProperty(value = "支付URL")
    private String paymentUrl;

    /**
     * 支付参数
     */
    @TableField(value = "PAYEMTN_PARM")
    @ApiModelProperty(value = "支付参数")
    private String payemtnParm;

    /**
     * 用户子标识
     */
    @TableField(value = "USER_IDENT")
    @ApiModelProperty(value = "用户子标识")
    private String userIdent;

    /**
     * 支付描述
     */
    @TableField(value = "PAYMENT_DESC")
    @ApiModelProperty(value = "支付描述")
    private String paymentDesc;

    /**
     * 订单超时时间,最长1800秒
     */
    @TableField(value = "OEDER_TIMEOUT")
    @ApiModelProperty(value = "订单超时时间,最长1800秒")
    private String oederTimeout;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value = "操作员")
    private String operId;

    /**
     * 发起方时间
     */
    @TableField(value = "INITIATOR_TIME")
    @ApiModelProperty(value = "发起方时间")
    private Date initiatorTime;

    /**
     * 支付流水号
     */
    @TableField(value = "PAYMENT_TRACE_ID")
    @ApiModelProperty(value = "支付流水号")
    private String paymentTraceId;

    /**
     * 更新时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value = "更新时间戳")
    private Date updateDt;

    @TableField(value = "PAYMENT_DT_TM")
    @ApiModelProperty(value = "支付时间")
    private Date paymentDtTM;

    @TableField(value = "PART_MODE_ID")
    private String partModeId;

    public static final String COL_MAIN_ORDER_ID = "MAIN_ORDER_ID";

    public static final String COL_PAYMENT_MODE = "PAYMENT_MODE";

    public static final String COL_ORDER_TYPE = "ORDER_TYPE";

    public static final String COL_MARKET_ID = "MARKET_ID";

    public static final String COL_ORDER_TOT_AMT = "ORDER_TOT_AMT";

    public static final String COL_TXN_TOT_AMT = "TXN_TOT_AMT";

    public static final String COL_CCB_WALLET_NO = "CCB_WALLET_NO";

    public static final String COL_APP_ID = "APP_ID";

    public static final String COL_WALLET_NM = "WALLET_NM";

    public static final String COL_PAYMENT_RSLT = "PAYMENT_RSLT";

    public static final String COL_BANK_CD = "BANK_CD";

    public static final String COL_PAYMENT_URL = "PAYMENT_URL";

    public static final String COL_PAYEMTN_PARM = "PAYEMTN_PARM";

    public static final String COL_USER_IDENT = "USER_IDENT";

    public static final String COL_PAYMENT_DESC = "PAYMENT_DESC";

    public static final String COL_OEDER_TIMEOUT = "OEDER_TIMEOUT";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_INITIATOR_TIME = "INITIATOR_TIME";

    public static final String COL_PAYMENT_TRACE_ID = "PAYMENT_TRACE_ID";

    public static final String COL_UPDATE_DT = "UPDATE_DT";

    public static final String PAYMENT_DT_TM = "PAYMENT_DT_TM";
}