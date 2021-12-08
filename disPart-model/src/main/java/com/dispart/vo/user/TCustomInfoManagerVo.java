package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title TCustomInfoManagerVo
 * @Description TODO 客户信息管理
 * @dateTime 2021/8/9 11:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "客户信息管理")
@TableName("t_custom_info_manager")
public class TCustomInfoManagerVo extends PageInfo {
    //正常
    public static final String NORMAL = "0";
    //禁用
    public static final String DISABLE = "1";
    //待审核
    public static final String NOAUDIT = "3";
    //审核通过
    public static final String ISPASS = "4";
    //审核未通过
    public static final String NOPASS = "5";

    private String provId;
    // 税务登记号
    private String taxNum;
    // 客户类型
    private String customTp;
    // 扶贫类型
    private String pvtyTp;
    // 客户名称
    private String provNm;
    // 客户简称
    private String shrtNm;
    // 证件类型
    private String certType;
    //证件号码
    private String certNum;
    // 证件地址
    private String certAddr;
    // 证件有效期
    private Date certPrd;
    private String phone;
    // 法人名称
    private String legalName;
    private String legalPhone;
    // 法人证件类型
    private String legalCertTp;
    private String legalCertNum;

    // 代办人名称
    private String agentName;
    // 代办人电话
    private String agentPhone;
    // 代办人身份证号
    private String agentCertNo;
    // 代办人地址
    private String agentAddr;
    // 代办人身份证正面url
    private String agentFrontUrl;
    // 代办人身份证反面url
    private String agentReverserUrl;
    // 代办委托书 AGENT_TERM_BOOK_URL
    private String agentTermBookUrl;

    private String termPrctn;
    private Date termDueDt;

    // 身份证正面url
    private String frontUrl;
    // 身份证反面url
    private String reverserUrl;
    // 营业执照url
    private String businessUrl;

    /**
     * 客户状态 0-启用 1-禁用 3-待审核 4-审核通过 5-审核未通过
     */
    private String status;

    /**
     * 是否实名 0-实名  1-未实名
     */
    private String isReal;
    /**
     * 助记码
     */
    private String mnmnCode;
    /**
     * 下单二维码url
     */
    private String qrcodeUrl;
    /**
     * 审核人
     */
    private String auditor;
    /**
     * 审核时间
     */
    private Date auditorTm;
    private String remark;

    // 操作人(t_employee_info.EMP_ID)
    private String operId;
    private String operName;

    private Date creatTime;
    private Date upTime;
    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 经营范围
     */
    private String businessRange;

    /**
     * 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
     */
    private String chanlNo;

    /**
     * 其它文件URL
     */
    private String otherFileUrl;

    /**
     * 0允许提现, 1不允许提现
     */
    private String isWithdraw;
}
