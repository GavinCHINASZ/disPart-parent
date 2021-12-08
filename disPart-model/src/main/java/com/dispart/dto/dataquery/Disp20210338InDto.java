package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionInDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Disp20210338InDto extends BaseSelectionInDto {

    private String txnType;  //交易类型

    private String transMd;  //交易方式

    private String status;  //状态

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date txnTm;  //交易时间

    private String provId;  //客户编号

    private String provNm;  //客户名称

    private String operNm;  //操作员名称

    private String businessNum;  //业务单号

    @JsonFormat(pattern = "yyyy-MM-dd 00:00:00")
    private String beginDate;  //根据交易时间查询开始日期

    @JsonFormat(pattern = "yyyy-MM-dd 23:59:59")
    private String endDate;//根据交易时间查询结束日期
}
