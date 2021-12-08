package com.dispart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccnoInfoVo  {

    private static final long serialVersionUID = 1L;

    //客户编号
    private String provId;
    //账户
    private String account;
    // 账户余额
    private BigDecimal acctBal;
    // 账户可用余额
    private BigDecimal availBal;
    // 账户冻结金额
    private BigDecimal freezeAmt;
    // 日贷记金额
    private BigDecimal dcreditAmt;
    // 日借记金额
    private BigDecimal ddebitAmt;
    // 账户mac
    private     String   mac;
    // old mac
    private String oldMac;

}
