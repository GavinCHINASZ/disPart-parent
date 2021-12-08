package com.dispart.model.businessCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 客户提现累积表
    */
@ApiModel(value="com-dispart-tmp-TCustomWithdrawTmp")
@Data
@TableName(value = "logistics.t_custom_withdraw_tmp")
public class TCustomWithdrawTmp {
    /**
     * 客户编号
     */
    @TableId(value = "PROV_ID", type = IdType.INPUT)
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
     * 累计金额
     */
    @TableField(value = "ACCRU_AMT")
    @ApiModelProperty(value="累计金额")
    private BigDecimal accruAmt;

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

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_ACCRU_AMT = "ACCRU_AMT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}