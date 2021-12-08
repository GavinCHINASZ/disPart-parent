package com.dispart.dto.prdctPriceDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InputErrorItemOutDto {

    //错误的行
    private int errorRow;

    private String errorMsg;

    private String prdctNm;

    private String maxPrice;

    private String midPrice;

    private String minPrice;

    private String unit;

    private String date;

}
