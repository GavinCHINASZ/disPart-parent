package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 账户信息查询 响应报文
 */
@Data
public class DISP20210284RespVo implements Serializable {
    private static final long serialVersionUID = 6720200178254865008L;
    //账号
    private String accNo;
    //账户类型
    private String accType;
    //账户户名
    private String accName;
    //开户机构名称
    private String openAccDept;
    //账户状态
    private String accStatus;
    //备注1
    private String remark;
    //备注2
    private String remark2;

}
