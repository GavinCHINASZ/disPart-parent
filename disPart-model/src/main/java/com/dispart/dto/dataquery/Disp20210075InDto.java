package com.dispart.dto.dataquery;

import lombok.Data;

import java.util.List;

@Data
public class Disp20210075InDto {

    /* 货品id */ private String prdctId;
    /* 货品种类 */ private String prdctNm;
    /* 货品种类ID */ private String prdctTypeId;
    /* 所查询的分类ID及其子ID */ private List<String> prdctTypeIdList;
    /* 客户编号 */ private String provId;
    /* 客户名称 */ private String provNm;
    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;

}
