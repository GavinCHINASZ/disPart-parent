package com.dispart.parmeterdto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author:xiejin
 * @date:Created in 2021/8/11 10:45
 * @description
 * @modified by:
 * @version: 1.0
 */

@Data
public class DISP20210184CusAccDetailOutDto {
    //客户id
    private String provId;
    //客户名称
    private String provNm;
    //客户账号
    private String Account;
    //省份证号码
    private String shrtNm;
    //卡号
    private String cardNo;
    //卡状态 卡状态 0-有效 7-禁用 8-注销 9-冻结 A-黑名单 B-挂失
    private String status;
    //卡片修改日期
    private Date updateDate;
    //余额
    private BigDecimal acctBal ;
    //冻结余额
    private  BigDecimal freezeAmt ;
    //可用余额
    private BigDecimal availBal ;
    //备注
    private String remark;

}
