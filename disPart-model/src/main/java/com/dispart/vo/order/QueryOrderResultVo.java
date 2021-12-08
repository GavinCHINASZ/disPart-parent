package com.dispart.vo.order;

import lombok.Data;

@Data
public class QueryOrderResultVo {

    private String mainOrderId;
    private String version;
    private String paymentTraceId;
    private String marketId;
    private String sndDtTm;
    private String sndTraceId;
}
