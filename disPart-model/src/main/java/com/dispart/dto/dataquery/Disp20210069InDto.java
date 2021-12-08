package com.dispart.dto.dataquery;

import lombok.Data;

import java.util.List;

@Data
public class Disp20210069InDto {

    /* 客户编号 */ private String provId;
    /* 客户名称 */ private String provNm;
    /* 采购商电话 */ private String userPhone;
    /* 主订单编号 */ private String mainOrderId;
    /* 子订单编号 */ private String orderId;
    /* 货品种类Id */ private String prdctTypeId;
    /* 货品种类 */ private String prdctType;
    /* 付款方式 */ private String paymentMode;
    /* 订单状态 */ private String orderSt;
    /* 结算状态 */ private String respSt;
    /* 下单起始日期 */ private String beginDate;
    /* 下单结束日期 */ private String endDate;
    /* 所查询的分类ID及其子ID */ private List<String> prdctTypeIdList;
    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;

}
