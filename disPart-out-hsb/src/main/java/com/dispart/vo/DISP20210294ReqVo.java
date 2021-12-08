package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class DISP20210294ReqVo implements Serializable {
    private static final long serialVersionUID = -2685234765980109594L;
    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    //发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;
    //市场编号
    @JSONField(name = "Mkt_Id")
    private String marketId;
    //客户方退款流水号
    @JSONField(name = "Cust_Rfnd_Trcno")
    private String customRefundTraceId;
    //退款流水号
    @JSONField(name = "Rfnd_Trcno")
    private String refundTraceId;
    //版本号
    @JSONField(name = "Vno")
    private String vno;

}
