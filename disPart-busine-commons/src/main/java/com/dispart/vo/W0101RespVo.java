package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class W0101RespVo extends ResponseBody {
    private static final long serialVersionUID = 3440326627472875635L;
    //账号
    @XStreamAlias("ACC_NO")
    private String accNo;
    //账户类型
    @XStreamAlias("ACC_TYPE")
    private String accType;
    //账户户名
    @XStreamAlias("ACC_NAME")
    private String accName;
    //开户机构名称
    @XStreamAlias("OPENACC_DEPT")
    private String openAccDept;
    //账户状态
    @XStreamAlias("ACC_STATUS")
    private String accStatus;

    //UBANK_NO
    @XStreamAlias("UBANK_NO")
    private String ubankNo;

    //COUNTER_NO
    @XStreamAlias("COUNTER_NO")
    private String counterNo;

    //EXCHANGE_NO
    @XStreamAlias("EXCHANGE_NO")
    private String exchangeNo;

    //CUR_TYPE
    @XStreamAlias("CUR_TYPE")
    private String curType;

    //备注1
    @XStreamAlias("REM1")
    private String remark;

    //备注2
    @XStreamAlias("REM2")
    private String remark2;

}
