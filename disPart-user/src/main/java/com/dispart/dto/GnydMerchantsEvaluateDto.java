package com.dispart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 贵农云贷 市场方评价表
 */
@Setter
@Getter
@NoArgsConstructor
public class GnydMerchantsEvaluateDto {

    //每页条数
    private Integer pageSize;
    //当前页数
    private Integer pageNum;

    //客户编号
    private String provId;
    //正常经营标识别
    private String businessType;
    //租赁到期日期
    private Date leaseExpires;
    //结算用户是否是建行
    private String settlement;
    //市场信誉
    private String credit;
    //备注
    private String remark;
    //操作员ID
    private String operId;
    //操作员
    private String operator;
    //部门ID
    private String depId;
    //机构编号
    private String subOrg;
    //创建时间
    private Date creatTime;
    //更新时间
    private Date upTime;
}
