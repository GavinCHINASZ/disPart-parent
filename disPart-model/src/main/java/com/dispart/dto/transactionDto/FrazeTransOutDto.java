package com.dispart.dto.transactionDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FrazeTransOutDto {

    private String id;

    private String account;

    private String cardNo;

    private String provId;

    private String provNm;

    private BigDecimal beforAmt;

    private BigDecimal txnAmt;

    private BigDecimal afterAmt;

    private String txnType;

    private Date txnDt;

    private String operNm;

    private String remark;

}
