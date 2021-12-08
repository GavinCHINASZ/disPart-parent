package com.dispart.modle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商户信息
 */
@ApiModel(value = "com-dispart-model-DataMerchant")
@Data
public class DataMerchant {
    @ApiModelProperty(value = "")
    private String id;

    /**
     * 商户编号 Merchantcode
     */
    @ApiModelProperty(value = "商户编号 Merchantcode")
    private String code;

    /**
     * 企业名称 CName
     */
    @ApiModelProperty(value = "企业名称 CName")
    private String cname;

    /**
     * 经营者名称 CEO
     */
    @ApiModelProperty(value = "经营者名称 CEO")
    private String legalPerson;

    /**
     * 经营者身份证 CEOID
     */
    @ApiModelProperty(value = "经营者身份证 CEOID")
    private String idCard;

    /**
     * 统一社会信用代码/营业执照号 TaxNo
     */
    @ApiModelProperty(value = "统一社会信用代码/营业执照号 TaxNo")
    private String taxNo;

    /**
     * 入驻日期
     */
    @ApiModelProperty(value = "入驻日期")
    private Date entryDate;

    /**
     * 客户类型 1.季节性  0.非季节性 0
     */
    @ApiModelProperty(value = "客户类型 1.季节性  0.非季节性 0")
    private String seasonType;

    /**
     * 客户类别 ：0.其他 1. 蔬菜  2.水果  3.食用菌
     */
    @ApiModelProperty(value = "客户类别 ：0.其他 1. 蔬菜  2.水果  3.食用菌")
    private String varietyType;

    /**
     * 风险级别  1. 高风险(0)  2  中风险(1-3)  3 低风险(>3)
     */
    @ApiModelProperty(value = "风险级别  1. 高风险(0)  2  中风险(1-3)  3 低风险(>3)")
    private String riskLevel;

    /**
     * 是否正常经营 1.正常  0.不正常
     */
    @ApiModelProperty(value = "是否正常经营 1.正常  0.不正常 ")
    private String status;

    /**
     * 租赁到期日
     */
    @ApiModelProperty(value = "租赁到期日")
    private Date rentExpireDate;

    /**
     * 租赁开始日
     */
    @ApiModelProperty(value = "租赁开始日")
    private Date rentStartDate;

    /**
     * 是否建行用户 0 否 1 是
     */
    @ApiModelProperty(value = "是否建行用户 0 否 1 是")
    private String isCcb;

    /**
     * 是否有交易数据 0 否 1 是
     */
    @ApiModelProperty(value = "是否有交易数据 0 否 1 是")
    private String isTrade;
}