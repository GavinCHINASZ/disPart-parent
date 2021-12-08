package com.dispart.parmeterdto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiejin
 * @date 2021/8/12 10:00
 */
@Data
public class MemberShipInfoOutDto {
    @ApiModelProperty(value = "账户余额")
    @TableField("acct_bal")
    private BigDecimal acctBal;

    @ApiModelProperty(value = "可用余额")
    @TableField("avail_bal")
    private BigDecimal availBal;

    @ApiModelProperty(value = "冻结金额")
    @TableField("freeze_amt")
    private BigDecimal freezeAmt;

    @ApiModelProperty(value = "客户编号")
    @TableField("prov_id")
    private String provId;

    @ApiModelProperty(value = "Mac校验值")
    @TableField("MAC")
    private String mac;
}
