package com.dispart.dto.entrance;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 货物统计查询响应dto
 * @modified by:
 * @version: 1.0
 */
public class QueryGoodsTotalParamOutDto {
    //商品种类名称
    private String prdctType;
    //商品名称
    private String prdctNm;
    //供货商
    private String provNm;
    //库存数量
    private String stock;
    //单位
    private String unit;
    //销售量
    private String saleTol;
    //销售额
    private String saleValue;
}
