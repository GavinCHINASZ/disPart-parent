package com.dispart.vo.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 商品计量单位标准数据表
    */
@Data
public class TGoodsUnitRelevance {
    /**
    * 计量单位ID
    */
    private Integer unitId;

    /**
    * 每个单位对应的重量
    */
    private BigDecimal weight;

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
}