package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseHeader implements Serializable {
    private static final long serialVersionUID = -5323900760077344706L;

    // 请求序列号
    @XStreamAlias("REQUEST_SN")
    private String requestSn;

    // 客户号
    @XStreamAlias("CUST_ID")
    private String custId;

    // 交易码
    @XStreamAlias("TX_CODE")
    private String txCode;

    // 响应码
    @XStreamAlias("RETURN_CODE")
    private String returnCode;

    // 相应信息
    @XStreamAlias("RETURN_MSG")
    private String returnMsg;

    // 语言
    @XStreamAlias("LANGUAGE")
    private String language;

}
