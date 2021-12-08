package com.dispart.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReconciliationResult {

//    private String
    private String main_order_id;

    private Date txn_dt;

    private String order_st;

    private BigDecimal txt_amt;

    private BigDecimal txt_amt2;

    private String prov_id;

    private String prov_nm;

    private String marker_id;

    private String payment_trace_id;

    private Date txn_tm;

    private String payee_st;
}
