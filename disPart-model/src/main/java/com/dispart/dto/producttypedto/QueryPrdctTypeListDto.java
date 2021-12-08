package com.dispart.dto.producttypedto;

import lombok.Data;

import java.util.List;

@Data
public class QueryPrdctTypeListDto {

    private String prdctTypeId;

    private String prdctType;

    private String parentTypeId;

    private String remark;

    private List<QueryPrdctTypeListDto> childTypeList;
}
