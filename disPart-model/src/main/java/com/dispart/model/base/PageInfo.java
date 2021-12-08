package com.dispart.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PageInfo {
    //分页开始索引
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer pageNum;
    //分页抓取数据大小
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer pageSize;
    //数据库数据总条数
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer tolPageNum;
}
