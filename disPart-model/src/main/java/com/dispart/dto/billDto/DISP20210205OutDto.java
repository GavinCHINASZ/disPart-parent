package com.dispart.dto.billDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DISP20210205OutDto {

    private String billNum;

    private BigDecimal actRecevAmt;

    private String provId;

    private String telephone;

    private String provNm;
}
