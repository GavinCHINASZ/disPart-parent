package com.dispart.dto.orderdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubOrderListParams {

    private String subOrderNo;
    private BigDecimal refundAmt;
    private List<ParListParams> parList;
}
