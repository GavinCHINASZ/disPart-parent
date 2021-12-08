package com.dispart.baseDto;

import lombok.Data;

@Data
public class BaseSelectionInDto {

    //分页参数，当前页
    private Integer pageNum;

    //分页参数，分页大小
    private Integer pageSize;

    //App统一查询条件字段
    private String appSelectKey;

}
