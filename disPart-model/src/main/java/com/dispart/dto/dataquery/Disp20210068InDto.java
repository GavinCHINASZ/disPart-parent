package com.dispart.dto.dataquery;

import lombok.Data;

@Data
public class Disp20210068InDto {

    /* 用户ID */ private String userId;
    /* 供货商ID */ private String provId;
    /* 开始日期 */ private String beginDate;
    /* 结束日期 */ private String endDate;
    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;

}
