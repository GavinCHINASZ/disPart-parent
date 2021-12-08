package com.dispart.dto.dataquery;

import lombok.Data;

@Data
public class Disp20210072InDto {

    /* 交易日期 */ private String txnDt;
    /* 供应商编号 */ private String provId;
    /* 供应商名称 */ private String provNm;
    /* 结算状态 */ private String  payeeSt;
    /* 起始日期 */ private String beginDate;
    /* 结束日期 */ private String endDate;
    /* 对账结果 */ private String reconRslt;
    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;


}
