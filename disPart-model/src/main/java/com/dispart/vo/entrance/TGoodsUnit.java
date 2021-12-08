package com.dispart.vo.entrance;

import lombok.Data;

import java.util.Date;

/**
    * 商品计量单位表
    */
@Data
public class TGoodsUnit {
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
    * 计量单位 箱/件/个
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
}