package com.dispart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author:xiejin
 * @date:Created in 2021/8/11 14:13
 * @description
 * @modified by:
 * @version: 1.0
 */

@Data
@ApiModel(description = "会员卡号表")
@TableName("t_membership_info")
public class MumberShipInfoVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "卡号")
    @TableField("CARD_NO")
    private String cardNo;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWD")
    private String passwd;

    @ApiModelProperty(value = "账号")
    @TableField("ACCOUNT")
    private String account;

    @ApiModelProperty(value = "客户号")
    @TableField("PROV_ID")
    private String provId;
    @ApiModelProperty(value = "卡类型 0-实体卡 1-虚拟卡")
    @TableField("CARD_TP")
    private String cardTp;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "卡状态 0-有效 1-冻结 6-挂失 9-销户 7-禁用 A-黑名单")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "押金标志 00-无押金 10-有押金 11-有押金可消费")
    @TableField("CASH_INDENT")
    private String cashIndent;

    @ApiModelProperty(value = "押金金额")
    @TableField("CASH_PLEDGE")
    private BigDecimal cashPledge;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "操作员")
    @TableField("OPER_ID")
    private BigDecimal openID;

    @ApiModelProperty(value = "创建时间")
    @TableField("creat_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("up_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;
    @ApiModelProperty(value = "客户名称")
    @TableField("PROV_NM")
    private String provNm;

    @ApiModelProperty(value = "证件号码")
    @TableField("CERT_NUM ")
    private String certNum;

    /**
     * 操作人
     */
    private String operId;
    /**
     * 操作人名称
     */
    private String operName;

    private String chanlNo;
}
