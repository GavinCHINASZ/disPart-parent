package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class W0101ReqVo extends RequestBody{
    private static final long serialVersionUID = 2648561024435783009L;

    //转入一级行行号
    @XStreamAlias("ACC_BRANCH_CODE")
    private String accBranchCode;

    //转入账户
    @XStreamAlias("ACC_NO")
    private String accNo;

    //转出账户
    @XStreamAlias("ACC_NO1")
    private String accNo1;

}
