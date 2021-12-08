package com.dispart.dto.entrance;

import lombok.Data;

import java.util.List;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 货物统计查询响应dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QueryGoodsTotalOutDto {
    //返回货物信息列表
    private List<QueryGoodsTotalParamOutDto> list;
    //总条数
    private Integer tolPageNum;
}
