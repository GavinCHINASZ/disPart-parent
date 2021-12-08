package com.dispart.dto.entrance;

import lombok.Data;

import java.util.List;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0228FindOutDto {
    //品种名称集合
    private List<D_0228FindOutYDto> prdctNmList;
    //品种名称
    private String prdctNm;
    //品种id
    private String varietyId;
    //品类名称
    private String categoryNm;
    //品类id
    private String categoryId;
    //品种费率
    private double rate;
}
