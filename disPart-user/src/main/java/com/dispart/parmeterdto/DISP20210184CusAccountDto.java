package com.dispart.parmeterdto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DISP20210184CusAccountDto {
    //手机端传入的模糊人名或手机号
    private String provNmOrPhone;
    //客户名称
    private String provId;
    //客户账户
    private String account;
    //客户名称
    private String provNm;
    //省份证号码
    private String certNum;
    //客户名称
    private String phone;
    //卡号
    private String cardNo;
    private String status;
    private String remark;
    //余额
    private BigDecimal acctBal;
    //冻结余额
    private BigDecimal freezeAmt;
    //可用余额
    private BigDecimal availBal;

    /**
     * 操作人
     */
    private String operId;
    /**
     * 操作人名称
     */
    private String operName;

    /**
     * 开户时间(创建时间)
     */
    private Date creatTime;
    private Date creatStTime;
    private Date creatEndTime;

    /**
     * 渠道号
     */
    private String chanlNo;

    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long pageNum;
    /* 起始记录数 */ private Long startIndex;

}
