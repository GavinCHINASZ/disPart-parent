package com.dispart.modle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Disp20210347InDto {

    @JsonProperty(value = "Cst_Lgl_Nm")
    private String Cst_Lgl_Nm; //借款人名称

    @JsonProperty(value = "Crdt_No")
    private String Crdt_No; //借款人证件号码

    @JsonProperty(value = "Crdt_TpCd")
    private String Crdt_TpCd; //借款人证件类型代码

    @JsonProperty(value = "Enqr_Dt_BgDy")
    private String Enqr_Dt_BgDy; //查询日期

}
