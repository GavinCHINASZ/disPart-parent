package com.dispart.dto.busineCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePayJrnDTO {
    /**
     * 流水号/自生成
     */
    @ApiModelProperty(value="流水号/自生成")
    private String jrnlNum;

    /**
     * 业务号
     */
    @ApiModelProperty(value="业务号")
    private String businessNo;

    /**
     * 交易类型 0-充值 1-提现 2-进场费 3-停车费 4-退款
     */
    @ApiModelProperty(value="交易类型 0-充值 1-提现 2-进场费 3-停车费 4-退款")
    private String txnType;

    /**
     * 方式
     */
    @ApiModelProperty(value="方式")
    private String transMd;

    /**
     * 交易金额
     */
    @ApiModelProperty(value="交易金额")
    private BigDecimal txnAmt;

    /**
     * 付款人编号
     */
    @ApiModelProperty(value="付款人编号")
    private String payerNo;

    /**
     * 付款人名称
     */
    @TableField(value = "PAY_NAME")
    @ApiModelProperty(value="付款人名称")
    private String payName;

    /**
     * 付款人卡号
     */
    @ApiModelProperty(value="付款人卡号")
    private String payCard;

    /**
     * 付款人账号
     */
    @ApiModelProperty(value="付款人账号")
    private String payAcct;

    /**
     * 收款人编号
     */
    @ApiModelProperty(value="收款人编号")
    private String payeeNum;

    /**
     * 收款人名称
     */
    @ApiModelProperty(value="收款人名称")
    private String payee;

    /**
     * 收款人账号
     */
    @ApiModelProperty(value="收款人账号")
    private String payeeAcct;

    /**
     * 摘要
     */
    @ApiModelProperty(value="摘要")
    private String summary;

    /**
     * 状态 0-待支付 1-支付成功  2-支付失败
     */
    @ApiModelProperty(value="状态 0-待支付 1-支付成功  2-支付失败")
    private String status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 操作员
     */
    @ApiModelProperty(value="操作员")
    private String operId;

}
