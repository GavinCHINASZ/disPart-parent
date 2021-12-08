package com.dispart.dto.orderdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ZOrderInfo {

    private String provId;
    private String subOrderId;
    private BigDecimal ordAmt;
    private BigDecimal txnAmt;


}
