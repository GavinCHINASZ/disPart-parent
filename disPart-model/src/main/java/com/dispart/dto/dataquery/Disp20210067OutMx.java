package com.dispart.dto.dataquery;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Disp20210067OutMx {

    /* 订单ID */ private String orderId;
    /* 采购商ID */ private String purchId;
    /* 供货商ID */ private String provId;
    /* 商品类型 */ private String prdctType;
    /* 商品名称 */ private String prdctNm;
    /* 商品单价 */ private BigDecimal prdctPrice;
    /* 商品数量 */ private Integer prdctNum;
    /* 商品单位 */ private String prdctUnit;
    /* 商品总价 */ private BigDecimal prdctAmt;
    /* 订单模式 */ private String orderModel;
    /* 供货商名称 */ private String provNm;
    /* 优惠金额 */ private BigDecimal preferPrice;
    /* 附加金额 */ private BigDecimal additAmt;
    /* 订单状态 */ private String orderSt;
    /* 下单时间 */ private String orderTm;
    /* 手续费 */ private BigDecimal servChrg;
    /* 分账金额 */ private BigDecimal partAmt;


}
