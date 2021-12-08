package com.dispart.dto.dataquery;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class Disp20210074OutDto {

    /* 记录数 */ private Long recNum;
    /* 总金额 */ private BigDecimal totalAmt;

    /* 明细 */
    private ArrayList<Disp20210074OutMx> list;

}
