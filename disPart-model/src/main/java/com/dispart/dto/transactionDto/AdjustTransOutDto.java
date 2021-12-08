package com.dispart.dto.transactionDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdjustTransOutDto {

    private String id;

    private String account;

    private String cardNo;

    private String provId;

    private String provNm;

    private BigDecimal txnAmt;

    private String operTp;

    private Date txnDt;

    private String operNm;

    private String remark;

}
