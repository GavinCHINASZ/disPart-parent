package com.dispart.dto.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 商品库存表
    */
@Data
public class TProductInventoryInfoDto {
    /**
    * 商品编号
    */
    private String prdctId;

    /**
    * 供应商编号
    */
    private String provId;

    /**
     * 供应商名称
     */
    private String provNm;

    /**
    * 商品名称
    */
    private String prdctNm;

    /**
    * 库存数量
    */
    private BigDecimal stock;

    /**
    * 单位
    */
    private String unit;

    /**
    * 备注
    */
    private String remark;

    /**
    * 更新日期
    */
    private Date updateDt;

    /**
    * 货品种类
    */
    private String prdctType;

    /**
    * 货品种类ID
    */
    private String prdctTypeId;

    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;

    private Integer strNum;
    //销售量
    private Long saleNum;
    //销售额
    private Double saleAmt;
    //开始日期
    private String startDate;
    //结束日期
    private String endDate;
    //客户手机号
    private String phone;
    //肋记码
    private String mnmnCode;
    //客户手机号
    private String provPhone;

}