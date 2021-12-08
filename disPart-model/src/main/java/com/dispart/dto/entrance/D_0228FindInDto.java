package com.dispart.dto.entrance;

import lombok.Data;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0228FindInDto {
    //品种名称
    private String prdctNm;
    //查询方式（0-查全部信息，其他-查品种名称）
    private String type;
}
