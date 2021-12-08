package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 用户优惠记录表
    */
@ApiModel(value="com-dispart-tmp-TDiscountsUser")
@Data
@TableName(value = "logistics.t_discounts_user")
public class TDiscountsUser {
    /**
     * 优惠活动ID
     */
    @TableField(value = "ACT_ID")
    @ApiModelProperty(value="优惠活动ID")
    private Integer actId;

    /**
     * 客户编号
     */
    @TableField(value = "PROV_ID")
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
     * 优惠日期
     */
    @TableField(value = "DISCNT_DT")
    @ApiModelProperty(value="优惠日期")
    private Date discntDt;

    /**
     * 优惠日期
     */
    @TableField(value = "DISCNT_AMT")
    @ApiModelProperty(value="优惠金额")
    private BigDecimal discntAmt;

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

    public static final String COL_ACT_ID = "ACT_ID";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_DISCNT_DT = "DISCNT_DT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}