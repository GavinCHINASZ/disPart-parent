package com.dispart.vo.gnLoan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Disp20210347TransInfoOutVo {

        @JsonProperty(value = "Stat_Mo")
        private String Stat_Mo; //统计月份 yyyy-MM

        @JsonProperty(value = "TxnAmt")
        private String TxnAmt; //交易金额

}
