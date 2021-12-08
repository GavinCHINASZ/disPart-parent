package com.dispart.model.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Prj {

    private String itemNo;
    private String itemNm;
    private String itemTp;
    private BigDecimal amt;
}
