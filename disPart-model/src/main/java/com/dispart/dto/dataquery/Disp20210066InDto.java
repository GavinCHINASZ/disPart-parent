package com.dispart.dto.dataquery;

import lombok.Data;

import java.util.Date;

@Data
public class Disp20210066InDto {

    /* 供货商ID */ private String provId;
    /* 用户ID */ private String UserId;
    /* 开始日期 */ private String beginDate;
    /* 结束日期 */ private String endDate;
    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;

}
