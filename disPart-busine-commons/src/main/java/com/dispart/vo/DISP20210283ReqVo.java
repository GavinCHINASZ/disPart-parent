package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 转账结果查询 请求报文
 */
@Data
public class DISP20210283ReqVo implements Serializable {

    private static final long serialVersionUID = -5232922604298655880L;

    //原交易序列号
    private String requestSn1;
}
