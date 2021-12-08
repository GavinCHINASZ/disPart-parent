package com.dispart.dto.dataquery;

import lombok.Data;

@Data
public class Disp20210071InDto {

    /* 供应商编号 */ private String provId;
    /* 供应商名称 */ private String provNm;
    /* 起始日期 */ private String beginDate;
    /* 结束日期 */ private String endDate;
    /* 分账状态代码 */ private String respSt;
    /* 业务订单号 */ private String orderId;
    /* 分账订单号 */ private String subOrderId;
    /* 分页条数 */ private Long pageSize;
    /* 分页页数 */ private Long curPage;
    /* 起始记录数 */ private Long startIndex;
   /* 支付方式 */ private String paymentMode;

}
