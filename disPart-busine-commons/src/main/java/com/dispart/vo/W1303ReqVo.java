package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单笔代发代扣请求报文
 */
@Data
public class W1303ReqVo extends RequestBody {
    private static final long serialVersionUID = 904147271468732637L;

    // 企业账户
    @XStreamAlias("ACC_NO1")
    private String accNo1;

    // 代发代扣编号
    @XStreamAlias("BILL_CODE")
    private String billCode;

    // 对方账户
    @XStreamAlias("ACC_NO2")
    private String accNo2;

    // 对方姓名
    @XStreamAlias("OTHER_NAME")
    private String otherName;

    // 金额
    @XStreamAlias("AMOUNT")
    private BigDecimal amount;

    // 用途编号
    @XStreamAlias("USEOF_CODE")
    private String useofCode;

    // 网银审批标识
    @XStreamAlias("FLOW_FLAG")
    private String flowFlag;

    // 对方账户支付系统行号
    @XStreamAlias("UBANK_NO")
    private String ubankNo;

    // 备注1
    @XStreamAlias("REM1")
    private String rem1;

    // 备注2
    @XStreamAlias("REM2")
    private String rem2;

}
