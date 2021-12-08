package com.dispart.dto.orderdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashOutParam {

    private String provId;
    private String payCard;
    private String provAcct;
    private String provNm;
    private String ubankNo;
    private String purpose;
    private BigDecimal amount;
    private String remark;
    private String remark2;

    private String chanlNo;

    private String transMd;

}
