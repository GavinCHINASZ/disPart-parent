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

@ApiModel(value="com-dispart-model-order-TWithholdDetailsInfo")
@Data
@TableName(value = "logistics.t_withhold_details_info")
public class TWithholdDetailsInfo {
    @TableId(value = "MARKET_ID", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String marketId;

    @TableField(value = "ORDER_NUM")
    @ApiModelProperty(value="")
    private String orderNum;

    @TableField(value = "DEDU_AMT")
    @ApiModelProperty(value="")
    private BigDecimal deduAmt;

    @TableField(value = "DEDU_ACCT")
    @ApiModelProperty(value="")
    private String deduAcct;

    @TableField(value = "ITEM_NO")
    @ApiModelProperty(value="")
    private String itemNo;

    @TableField(value = "ITEM_NM")
    @ApiModelProperty(value="")
    private String itemNm;

    @TableField(value = "DEDU_ST")
    @ApiModelProperty(value="")
    private String deduSt;

    @TableField(value = "DEDU_DT")
    @ApiModelProperty(value="")
    private Date deduDt;

    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="")
    private Date updateDt;

    public static final String COL_MARKET_ID = "MARKET_ID";

    public static final String COL_ORDER_NUM = "ORDER_NUM";

    public static final String COL_DEDU_AMT = "DEDU_AMT";

    public static final String COL_DEDU_ACCT = "DEDU_ACCT";

    public static final String COL_ITEM_NO = "ITEM_NO";

    public static final String COL_ITEM_NM = "ITEM_NM";

    public static final String COL_DEDU_ST = "DEDU_ST";

    public static final String COL_DEDU_DT = "DEDU_DT";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}