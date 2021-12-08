package com.dispart.dto.entrance;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 进场货物核验
 */
@Data
public class QuryEntranceVeCheckOutDto {

    private List<QuryEntranceCheckParamOutDto> list;

    //总条数
    private Integer tolPageNum;

    //进场实际收费总金额
    private BigDecimal tolInActAmt;

    //出场实际收费总金额
    private BigDecimal tolOutActAmt;

}
