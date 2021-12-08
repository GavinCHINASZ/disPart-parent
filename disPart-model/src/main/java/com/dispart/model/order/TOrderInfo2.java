package com.dispart.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TOrderInfo2 {

    private String provId;
    private String subOrderId;
    private BigDecimal orderAmt;
    private BigDecimal txnAmt;

    private BigDecimal additAmt;
    private List prjList;

}
