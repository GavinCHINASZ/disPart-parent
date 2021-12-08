package com.dispart.dto.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
    * 商品计量单位标准数据表
    */
@Data
public class TGoodsUnitParamOutDto {
    /**
     * 主键ID
     */
    private Integer unitId;
    /**
     * 客户编号
     */
    private String provId;

    /**
     * 商品编号
     */
    private String prdctId;


    /**
     * 单位
     */
    private String unit;

    /**
    * 备注
    */
    private String remark;

    /**
    * 创建时间
    */
    private Date creatTime;

    /**
    * 更新时间
    */
    private Date upTime;

    private List<TGoodsUnitRelevanceDto> childList;
}