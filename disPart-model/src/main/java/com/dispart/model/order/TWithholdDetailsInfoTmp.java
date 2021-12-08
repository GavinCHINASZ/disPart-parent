package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TWithholdDetailsInfoTmp {

    private String marketId;

    @YHHIndex(index = 0)
    private String orderNum;

    private BigDecimal deduAmt;

    @YHHIndex(index = 1)
    private String deduAmtTmp;

    @YHHIndex(index = 2)
    private String deduAcct;

    @YHHIndex(index = 3)
    private String itemNo;

    @YHHIndex(index = 4)
    private String itemNm;

    @YHHIndex(index = 5)
    private String deduSt;

    private Date deduDt;

    @YHHIndex(index = 6)
    private String deduStTmp;

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