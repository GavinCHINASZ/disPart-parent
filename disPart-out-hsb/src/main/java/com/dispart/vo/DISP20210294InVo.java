package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DISP20210294InVo implements Serializable {
    private static final long serialVersionUID = 6906909787859916857L;

    //发起方时间戳
    private String sndDtTm;

    //发起方流水号
    private String sndTraceId;

    //市场编号
    private String marketId;

    //客户方退款流水号
    private String customRefundTraceId;

    //退款流水号
    private String refundTraceId;

}
