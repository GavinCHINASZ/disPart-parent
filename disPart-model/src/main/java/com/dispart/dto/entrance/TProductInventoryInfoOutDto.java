package com.dispart.dto.entrance;

import lombok.Data;

import java.util.List;

@Data
public class TProductInventoryInfoOutDto {
    //返回货物信息列表
    private List<TProductInventoryInfoDto> list;
    //总条数
    private Integer tolPageNum;

}
