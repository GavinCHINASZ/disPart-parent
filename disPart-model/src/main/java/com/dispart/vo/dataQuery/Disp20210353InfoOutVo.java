package com.dispart.vo.dataQuery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Disp20210353InfoOutVo {

    private String txnCount;  //交易单数

    private BigDecimal txnAmt;  //交易总金额

    private BigDecimal commission;  //手续费

    private BigDecimal finalAmt;  //结算金额

    private BigDecimal avrAmt; //单笔平均金额

    private String provNm;  //客户名称

    private String avrCommission;  //平均手续费

    private String commissionCount;  //收取手续费的订单总条数
}
