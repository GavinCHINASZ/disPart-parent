package com.dispart.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DatabaseCount {
    BigDecimal countRec;
    BigDecimal tolAmt;
    BigDecimal tolNum;
}
