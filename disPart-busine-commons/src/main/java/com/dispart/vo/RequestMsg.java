package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 对公网银请求报文
 */
@Data
@XStreamAlias("TX")
public class RequestMsg implements Serializable {

    private static final long serialVersionUID = 4168535018455490655L;
    // 请求序列号
    @XStreamAlias("REQUEST_SN")
    private String requestSn;

    // 客户号
    @XStreamAlias("CUST_ID")
    private String custId;

    // 操作员号
    @XStreamAlias("USER_ID")
    private String userId;

    // 操作员密码
    @XStreamAlias("PASSWORD")
    private String password;

    // 交易请求码
    @XStreamAlias("TX_CODE")
    private String txCode;

    // 语言
    @XStreamAlias("LANGUAGE")
    private String language;

    @XStreamAlias("TX_INFO")
    private RequestBody body;

}
