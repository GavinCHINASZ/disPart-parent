package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 行内提现请求报文
 */
@Data
public class DISP20210282ReqVo implements Serializable {
    private static final long serialVersionUID = -4544018965453549358L;

    //对方账户
    private String provAcct;
    //对方姓名
    private String provNm;
    //对方账户一级行号
    private String ubankNo;
    //金额
    private BigDecimal amount;
    //用途编号
    private String purpose;
    //备注1
    private String remark;
    //备注2
    private String remark2;

}
