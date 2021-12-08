package com.dispart.modle.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Disp20210347TransInfoOutVo {

        @JSONField(name = "Stat_Mo")
        private String statMo; //统计月份 yyyy-MM

        @JSONField(name = "TxnAmt")
        private String txnAmt; //交易金额

}
