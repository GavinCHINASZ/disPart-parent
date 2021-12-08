package com.dispart.dto.entrance;

import lombok.Data;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 货物统计查询请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QueryGoodsTotalInDto {
    //商品类型
    private String prdctId;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;

}
