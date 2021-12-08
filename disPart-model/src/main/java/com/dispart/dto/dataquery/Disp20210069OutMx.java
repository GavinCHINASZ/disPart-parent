package com.dispart.dto.dataquery;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Disp20210069OutMx {

    /*主订单ID*/ private String mainOrderId;
    /*付款方式*/ private String paymentMode;
    /* 订单ID */ private String orderId;
    /*订单类型*/ private String orderTp;
    /* 采购商ID */ private String purchId;
    /* 采购商名 */ private String userNm;
    /* 采购商电话 */ private String userPhone;
    /* 供货商ID */ private String provId;
    /* 供货商名称 */ private String provNm;
    /* 商品总价 */ private BigDecimal prdctAmt;
    /* 订单模式 */ private String orderModel;
    /* 优惠金额 */ private BigDecimal preferPrice;
    /* 附加金额 */ private BigDecimal additAmt;
    /* 交易日期 */ private String txnDt;
    /* 交易金额 */ private BigDecimal txnAmt;
    /* 分账金额 */ private BigDecimal partAmt;
    /* 手续费 */ private BigDecimal servChrg;
    /* 订单状态 */ private String orderSt;
    /* 下单时间 */ private Date orderTm;

}
