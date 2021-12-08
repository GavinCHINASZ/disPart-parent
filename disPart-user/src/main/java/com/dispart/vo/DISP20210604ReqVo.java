package com.dispart.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商户贷款信息录入
 */
@Data
public class DISP20210604ReqVo {
    //商户编号
    @NotBlank(message = "商户编号为空")
    private String provId;
    //商户名称
    @NotBlank(message = "商户名称为空")
    private String provNm;
    //身份证号
    @NotBlank(message = "身份证号为空")
    private String certNo;
    //从业年限
    @NotNull(message = "从业年限为空")
    private Integer workYears;
    //户籍
    @NotBlank(message = "户籍为空")
    private String censusRegist;
    //省内住房
    @NotNull(message = "省内住房为空")
    private Integer insideHouse;
    //省外住房
    @NotNull(message = "省外住房为空")
    private Integer outsideHouse;
    //文化程度
    @NotBlank(message = "省外住房为空")
    private String education;
    //婚姻状态
    @NotBlank(message = "婚姻状态为空")
    private String marriage;
    //子女个数
    @NotNull(message = "子女个数为空")
    private Integer children;
    //家庭人口
    @NotNull(message = "家庭人口为空")
    private Integer family;
    //家庭担保意愿
    @NotBlank(message = "家庭担保意愿为空")
    private String guarantee;
    //经营情况
    @NotBlank(message = "经营情况为空")
    private String business;
    //合伙人数量
    @NotNull(message = "合伙人数量为空")
    private Integer partner;
    //摊位数量
    @NotNull(message = "摊位数量为空")
    private Integer booth;
}
