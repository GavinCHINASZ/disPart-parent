package com.dispart.dto.entrance;

import lombok.Data;

import java.util.List;
@Data
public class TGoodsUnitOutDto {
    private List<TGoodsUnitParamOutDto> list;
    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;

    private Integer strNum;
}
