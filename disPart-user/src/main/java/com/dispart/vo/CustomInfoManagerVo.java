package com.dispart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author:xiejin
 * @date:Created in 2021/8/11 15:12
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
@ApiModel(description = "客户账户信息")
@TableName("t_custom_info_manager")
public class CustomInfoManagerVo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "客户编号")
    @TableField("PROV_ID")
    private String provId;
    ;
    @ApiModelProperty(value = "税务登记号")
    @TableField("TAX_NUM")
    private String taxNum;
    @ApiModelProperty(value = "客户类型 0-供应商 1-采购商")
    @TableField("CUSTOM_TP")
    private String customTp;
    @ApiModelProperty(value = "扶贫类型 0-扶贫A类商户 1-扶贫B类商户 2-优惠券客户")
    @TableField("PVTY_TP")
    private String pvtyTp;
    @ApiModelProperty(value = "客户名称")
    @TableField("PROV_NM")
    private String provNm;
    @ApiModelProperty(value = "客户简称")
    @TableField("SHRT_NM")
    private String shrtNm;
    @ApiModelProperty(value = "证件类型")
    @TableField("CERT_TYPE")
    private String certType;
    @ApiModelProperty(value = "证件号码")
    @TableField("CERT_NUM ")
    private String certNum;
    @ApiModelProperty(value = "证件住址")
    @TableField("CERT_ADDR")
    private String certAddr;
    @ApiModelProperty(value = "证件有效期")
    @TableField("CERT_PRD ")
    private Date certPrd;
    @ApiModelProperty(value = "手机号码")
    @TableField("PHONE")
    private String phone;
    @ApiModelProperty(value = "法人名称")
    @TableField("LEGAL_NAME")
    private String legalName;
    @ApiModelProperty(value = "法人电话")
    @TableField("LEGAL_PHONE ")
    private String legalPhone;
    @ApiModelProperty(value = "法人证件类型")
    @TableField("LEGAL_CERT_TP ")
    private String legalCertTp;
    @ApiModelProperty(value = "法人身份证号")
    @TableField("LEGAL_CERT_NUM")
    private String legalCertNum;
    @ApiModelProperty(value = "代办人名称")
    @TableField("AGENT_NAME")
    private String agentName;
    @ApiModelProperty(value = "代办人电话")
    @TableField("AGENT_PHONE ")
    private String agentPhone;
    @ApiModelProperty(value = "代办人身份证号")
    @TableField("AGENT_CERT_NO ")
    private String agentCertNo;
    @ApiModelProperty(value = "代办人地址")
    @TableField("AGENT_ADDR")
    private String agentAddr;
    @ApiModelProperty(value = "委托期限 0-长期有效 1-固定期限  ")
    @TableField("TERM_PRCTN")
    private String termPrctn;
    @ApiModelProperty(value = "委托到期日期")
    @TableField("TERM_DUE_DT ")
    private Date termDueDt;
    @ApiModelProperty(value = "身份证正面url")
    @TableField("FRONT_URL")
    private String frontUrl;
    @ApiModelProperty(value = "身份证反面url")
    @TableField("REVERSER_URL")
    private String reverserUrl;
    @ApiModelProperty(value = "营业执照url")
    @TableField("BUSINESS_URL")
    private String businessUrl;
    @ApiModelProperty(value = "客户状态 0-启用 1-禁用 3-待审核 4-审核通过 5-审核未通过")
    @TableField("STATUS ")
    private String status;
    @ApiModelProperty(value = "是否实名 0-否  1-是")
    @TableField("IS_REAL")
    private String is_real;
    @ApiModelProperty(value = "助记码 ")
    @TableField("MNMN_CODE")
    private String mnmnCode;
    @ApiModelProperty(value = "下单二维码url")
    @TableField("QRCODE_URL")
    private String qrcodeUrl;
    @ApiModelProperty(value = "审核人 ")
    @TableField("AUDITOR")
    private String auditor;
    @ApiModelProperty(value = "审核时间")
    @TableField("AUDITOR_TM")
    private Date auditorTm;
    @ApiModelProperty(value = "备注  ")
    @TableField("REMARK ")
    private String remark;
    @ApiModelProperty(value = "操作员 ")
    @TableField("OPER_ID")
    private String operId;
    @ApiModelProperty(value = "创建时间")
    @TableField("CREAT_TIME")
    private Date creatTime;
    @ApiModelProperty(value = "更新时间")
    @TableField("UP_TIME")
    private Date upTime;

    /**
     * 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
     */
    private String chanlNo;

    /**
     * 其它文件URL
     */
    private String otherFileUrl;

    // 代办人身份证正面url
    private String agentFrontUrl;
    // 代办人身份证反面url
    private String agentReverserUrl;
    // 代办委托书 AGENT_TERM_BOOK_URL
    private String agentTermBookUrl;
}
