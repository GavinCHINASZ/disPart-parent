package com.dispart.dto.busineCommon;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePayJrnAndInsertCardInfoDTO {
    private String flag;
    private String book;
    private String mainOrderId;
    private BigDecimal txnAmt;
    private String orderStatus;
}
