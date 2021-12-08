package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 账户信息查询 请求报文
 */
@Data
public class DISP20210284RepVo implements Serializable {

    private static final long serialVersionUID = 694213341634958423L;

    //转入一级行行号
    private String accBranchCode;
    //转入账户
    private String accNo;
    //转出账户
    private String accNo1;

}
