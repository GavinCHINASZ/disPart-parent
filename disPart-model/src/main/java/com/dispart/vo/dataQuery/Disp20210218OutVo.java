package com.dispart.vo.dataQuery;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class Disp20210218OutVo {

    private Integer tolPageNum;

    private BigDecimal incAmt;

    private BigDecimal decAmt;

    private ArrayList dataList;
}
