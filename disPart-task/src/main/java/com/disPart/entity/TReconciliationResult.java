package com.disPart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 对账结果
    */
@ApiModel(value="com-disPart-entity-TReconciliationResult")
@Data
@TableName(value = "logistics.t_reconciliation_result")
public class TReconciliationResult {
    /**
     * 交易日期
     */
    @TableId(value = "TXN_DT", type = IdType.INPUT)
    @ApiModelProperty(value="交易日期")
    private Date txnDt;

    /**
     * 市场编号
     */
    @TableId(value = "MARKET_ID", type = IdType.INPUT)
    @ApiModelProperty(value="市场编号")
    private String marketId;

    /**
     * 对账结果, 0-对账一致 1-对账不一致
     */
    @TableField(value = "RECON_RSLT")
    @ApiModelProperty(value="对账结果, 0-对账一致 1-对账不一致")
    private String reconRslt;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

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

    public static final String COL_TXN_DT = "TXN_DT";

    public static final String COL_MARKET_ID = "MARKET_ID";

    public static final String COL_RECON_RSLT = "RECON_RSLT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}