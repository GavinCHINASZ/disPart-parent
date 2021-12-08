package com.dispart.parmeterdto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/8/11 15:42
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class DISP20210181MemberShipInfoInDto {
    private static final long serialVersionUID = 1L;
    /**
     * (value = "状态 卡状态 0-有效 6-挂失 9-注销 1-冻结 7-禁用  A-黑名单 ")
     */
    public static final String EFFICIENT = "0";
    public static final String DISABLE = "1";
    public static final String LOGOUT = "8";
    public static final String FREEZE = "9";
    public static final String BLACKLIST = "A";
    public static final String REPORT_LOSS = "6";
    /**
     * 卡类型 0-实体卡 1-虚拟卡
     */
    public static final String PHYSICALCARD = "0";
    public static final String VITUALCARD = "1";

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

    @ApiModelProperty(value = "卡状态 0-有效 1-冻结 6-挂失 9-销户 A-黑名单")
    @TableField("STATUS")
    private String status;

    private String[] statusArr;

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
    private String openId;

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

    @ApiModelProperty(value = "账户余额")
    @TableField("acct_bal")
    private BigDecimal acctBal;

    @ApiModelProperty(value = "可用余额")
    @TableField("avail_bal")
    private BigDecimal availBal;

    @ApiModelProperty(value = "冻结金额")
    @TableField("freeze_amt")
    private BigDecimal frozenAmount;

    @ApiModelProperty(value = "解冻金额")
    @TableField("unfreeze_amt")
    private BigDecimal unfreezeAmount;

    @ApiModelProperty(value = "客户名称")
    @TableField("PROV_NM")
    private String provNm;

    @ApiModelProperty(value = "证件号码")
    @TableField("CERT_NUM ")
    private String certNum;


    /* 分页条数 */
    private Long pageSize;
    /* 分页页数 */
    private Long pageNum;
    /* 起始记录数 */
    private Long startIndex;
    /* 新密码 */
    private String newPassword;
    /*确认密码 */
    private String verifyUsPaWd;

    //总条数
    private Integer tolPageNum;
    //用户手机号
    private String userPhone;
    //用户密码
    private String newCardNo;
    //加密mac
    private String mac;
    //原mac
    private String oldmac;
    //验证码
    private String regCode;
    //开卡卡号
    private String bankNo;
    //开卡卡号
    private String bankPayNo;
    //持卡人姓名
    private String provName;
    //开户行名称
    private String bankName;
    //是否是建行卡
    private String isCcb;

    /**
     * 渠道号
     */
    private String chanlNo;

    //客户账号
    private String provAcct;
    List<DISP20210181MemberShipInfoInDto> cusAccountList = new ArrayList<>();

}
