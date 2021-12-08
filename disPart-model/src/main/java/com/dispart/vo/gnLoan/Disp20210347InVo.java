package com.dispart.vo.gnLoan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Disp20210347InVo {

    @ApiModelProperty(value = "经营者名称 CEO")
    private String legalPerson;

    @ApiModelProperty(value = "经营者身份证 CEOID")
    private String idCard;

    @ApiModelProperty(value = "查询日期")
    private String queryDate;

    @ApiModelProperty(value = "商户编号")
    private String code;

    @ApiModelProperty(value = "查询开始日期")
    private String beginDateMonth;
}
