package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 客户信息
    */
@ApiModel(value="com-dispart-model-base-TCustomInfoManager")
@Data
public class TCustomInfoManager {
    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
    * 税务登记号
    */
    @ApiModelProperty(value="税务登记号")
    private String taxNum;

    /**
    * 客户类型 0-供应商 1-采购商
    */
    @ApiModelProperty(value="客户类型 0-供应商 1-采购商")
    private String customTp;

    /**
    * 扶贫类型 0-扶贫A类商户 1-扶贫B类商户 2-优惠券客户
    */
    @ApiModelProperty(value="扶贫类型 0-扶贫A类商户 1-扶贫B类商户 2-优惠券客户")
    private String pvtyTp;

    /**
    * 客户名称
    */
    @ApiModelProperty(value="客户名称")
    private String provNm;

    /**
    * 客户简称
    */
    @ApiModelProperty(value="客户简称")
    private String shrtNm;

    /**
    * 证件类型
    */
    @ApiModelProperty(value="证件类型")
    private String certType;

    /**
    * 证件号码
    */
    @ApiModelProperty(value="证件号码")
    private String certNum;

    /**
    * 证件住址
    */
    @ApiModelProperty(value="证件住址")
    private String certAddr;

    /**
    * 证件有效期
    */
    @ApiModelProperty(value="证件有效期")
    private Date certPrd;

    /**
    * 手机号码
    */
    @ApiModelProperty(value="手机号码")
    private String phone;

    /**
    * 法人名称
    */
    @ApiModelProperty(value="法人名称")
    private String legalName;

    /**
    * 法人电话
    */
    @ApiModelProperty(value="法人电话")
    private String legalPhone;

    /**
    * 法人证件类型
    */
    @ApiModelProperty(value="法人证件类型")
    private String legalCertTp;

    /**
    * 法人身份证号
    */
    @ApiModelProperty(value="法人身份证号")
    private String legalCertNum;

    /**
    * 代办人名称
    */
    @ApiModelProperty(value="代办人名称")
    private String agentName;

    /**
    * 代办人电话
    */
    @ApiModelProperty(value="代办人电话")
    private String agentPhone;

    /**
    * 代办人身份证号
    */
    @ApiModelProperty(value="代办人身份证号")
    private String agentCertNo;

    /**
    * 代办人地址
    */
    @ApiModelProperty(value="代办人地址")
    private String agentAddr;

    /**
    * 委托期限 0-长期有效 1-固定期限
    */
    @ApiModelProperty(value="委托期限 0-长期有效 1-固定期限")
    private String termPrctn;

    /**
    * 委托到期日期
    */
    @ApiModelProperty(value="委托到期日期")
    private Date termDueDt;

    /**
    * 身份证正面url
    */
    @ApiModelProperty(value="身份证正面url")
    private String frontUrl;

    /**
    * 身份证反面url
    */
    @ApiModelProperty(value="身份证反面url")
    private String reverserUrl;

    /**
    * 营业执照url
    */
    @ApiModelProperty(value="营业执照url")
    private String businessUrl;

    /**
    * 客户状态 0-启用 1-禁用 3-待审核 4-审核通过 5-审核未通过
    */
    @ApiModelProperty(value="客户状态 0-启用 1-禁用 3-待审核 4-审核通过 5-审核未通过")
    private String status;

    /**
    * 是否实名 0-否  1-是
    */
    @ApiModelProperty(value="是否实名 0-否  1-是")
    private String isReal;

    /**
    * 助记码
    */
    @ApiModelProperty(value="助记码")
    private String mnmnCode;

    /**
    * 下单二维码url
    */
    @ApiModelProperty(value="下单二维码url")
    private String qrcodeUrl;

    /**
    * 审核人
    */
    @ApiModelProperty(value="审核人")
    private String auditor;

    /**
    * 审核时间
    */
    @ApiModelProperty(value="审核时间")
    private Date auditorTm;

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

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date upTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="经营范围")
    private String businessScope;
}