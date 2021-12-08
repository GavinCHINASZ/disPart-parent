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

@ApiModel(value="com-dispart-model-order-TApportionDetailsInfo")
@Data
@TableName(value = "logistics.t_apportion_details_info")
public class TApportionDetailsInfo {

    @TableId(value = "MAIN_ORDER_ID", type = IdType.INPUT)
    @ApiModelProperty(value="主订单编号")
    private String mainOrderId;

    /**
     * 市场编号
     */
    @TableId(value = "MARKET_ID", type = IdType.INPUT)
    @ApiModelProperty(value="市场编号")
    private String marketId;

    /**
     * 序号
     */
    @TableId(value = "SER_NUM", type = IdType.INPUT)
    @ApiModelProperty(value="序号")
    private String serNum;

    /**
     * 支付流水号
     */
    @TableId(value = "PAYMENT_TRACE_ID", type = IdType.INPUT)
    @ApiModelProperty(value="支付流水号")
    private String paymentTraceId;

    /**
     * 收款账户
     */
    @TableField(value = "PAYEE_ACCT")
    @ApiModelProperty(value="收款账户")
    private String payeeAcct;

    /**
     * 收款方商家编号
     */
    @TableField(value = "PAYEE_ID")
    @ApiModelProperty(value="收款方商家编号")
    private String payeeId;

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
     * 分账状态
     */
    @TableField(value = "RESP_ST")
    @ApiModelProperty(value="分账状态")
    private String respSt;

    /**
     * 分账日期
     */
    @TableField(value = "PART_DT")
    @ApiModelProperty(value="分账日期")
    private Date partDt;


    /**
     * 手续费
     */
    @TableField(value = "SERV_CHRG")
    @ApiModelProperty(value="手续费")
    private BigDecimal servChrg;

    /**
     * 子订单号
     */
    @TableField(value = "SUB_ORDER_ID")
    @ApiModelProperty(value="子订单号")
    private String subOrderId;

    /**
     * 原始分账金额
     */
    @TableField(value = "ORI_PART_AMT")
    @ApiModelProperty(value="原始分账金额")
    private BigDecimal oriPartAmt;

    /**
     * 重新出发分账日期时间
     */
    @TableField(value = "ORI_PART_DT")
    @ApiModelProperty(value="重新出发分账日期时间")
    private Date oriPartDt;

    /**
     * 更新时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="更新时间戳")
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