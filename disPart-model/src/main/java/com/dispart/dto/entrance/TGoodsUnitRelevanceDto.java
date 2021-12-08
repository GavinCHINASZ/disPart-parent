package com.dispart.dto.entrance;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TGoodsUnitRelevanceDto {

    /**
     * 每个单位对应的重量
     */
    private BigDecimal weight;
    /**
     *配置Id
     */
    private String unitId;
    /**
     *备注
     */
    private String remark;


}
