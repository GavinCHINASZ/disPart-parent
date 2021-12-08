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
    * 会员卡信息
    */
@ApiModel(value="com-dispart-tmp-TMembershipInfo")
@Data
@TableName(value = "logistics.t_membership_info")
public class TMembershipInfo {
    /**
     * 卡号
     */
    @TableId(value = "CARD_NO", type = IdType.INPUT)
    @ApiModelProperty(value="卡号")
    private String cardNo;

    /**
     * 密码
     */
    @TableField(value = "PASSWD")
    @ApiModelProperty(value="密码")
    private String passwd;

    /**
     * 账户号
     */
    @TableField(value = "ACCOUNT")
    @ApiModelProperty(value="账户号")
    private String account;

    /**
     * 客户编号
     */
    @TableField(value = "PROV_ID")
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
     * 卡类型
     */
    @TableField(value = "CARD_TP")
    @ApiModelProperty(value="卡类型")
    private String cardTp;

    /**
     * 绑定手机号
     */
    @TableField(value = "PHONE")
    @ApiModelProperty(value="绑定手机号")
    private String phone;

    /**
     * 卡状态 0-有效 7-禁用 8-注销 9-冻结 A-黑名单 B-挂失
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value="卡状态 0-有效 7-禁用 8-注销 9-冻结 A-黑名单 B-挂失")
    private String status;

    /**
     * 押金标志 00-无押金 10-有押金 11-有押金可消费
     */
    @TableField(value = "CASH_INDENT")
    @ApiModelProperty(value="押金标志 00-无押金 10-有押金 11-有押金可消费")
    private String cashIndent;

    /**
     * 押金金额
     */
    @TableField(value = "CASH_PLEDGE")
    @ApiModelProperty(value="押金金额")
    private BigDecimal cashPledge;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value="操作员")
    private String operId;

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

    public static final String COL_CARD_NO = "CARD_NO";

   // public static final String COL_PASSWD = "PASSWD";

    public static final String COL_ACCOUNT = "ACCOUNT";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_CARD_TP = "CARD_TP";

    public static final String COL_PHONE = "PHONE";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_CASH_INDENT = "CASH_INDENT";

    public static final String COL_CASH_PLEDGE = "CASH_PLEDGE";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}