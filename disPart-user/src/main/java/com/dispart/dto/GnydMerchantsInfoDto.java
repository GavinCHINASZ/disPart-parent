package com.dispart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 贵农云贷 商户贷款信息表
 */
@Setter
@Getter
@NoArgsConstructor
public class GnydMerchantsInfoDto {
    //商户编号
    private String provId;
    //商户名称
    private String provNm;
    //入驻日期
    private Date entryDate;
    //身份证号
    private String certNo;
    //从业年限
    private Integer workYears;
    //户籍
    private String censusRegist;
    //省内住房套数
    private Integer insideHouse;
    //省外住房套数
    private Integer outsideHouse;
    //文化程度
    private String education;
    //婚姻状况
    private String marriage;
    //子女数量
    private Integer children;
    //家庭人口数量
    private Integer family;
    //家庭担保意愿
    private String guarantee;
    //经营情况
    private String business;
    //合伙人数
    private Integer partner;
    //档口租赁数量
    private Integer booth;
    //状态
    private String status;
    //备注
    private String remark;
    //创建时间
    private Date creatTime;
    //更新时间
    private Date upTime;

}
