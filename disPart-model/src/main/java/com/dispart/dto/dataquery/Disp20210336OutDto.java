package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionOutDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Disp20210336OutDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dayDt;

    private BigDecimal currAmt;  //本期余额

    private BigDecimal availBal;  //可用余额

    private BigDecimal freezeAmt;  //冻结金额

    private BigDecimal debitAmt;  //借记金额

    private BigDecimal creditAmt;  //贷记金额

}
