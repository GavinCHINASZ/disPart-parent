package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 单笔代发代扣交易响应报文
 */
@Data
public class W1303RespVo extends ResponseBody {
    private static final long serialVersionUID = -2614975043698256353L;
    // 凭证号
    @XStreamAlias("CREDIT_NO")
    private String creditNo;

    // 自定义输出名称1
    @XStreamAlias("INDIVIDUAL_NAME1")
    private String individualName1;

    // 自定义输出内容1
    @XStreamAlias("INDIVIDUAL1")
    private String individual1;

    // 自定义输出名称2
    @XStreamAlias("INDIVIDUAL_NAME2")
    private String individualName2;

    // 自定义输出内容2
    @XStreamAlias("INDIVIDUAL2")
    private String individual2;

    // 备注1
    @XStreamAlias("REM1")
    private String rem1;

    // 备注2
    @XStreamAlias("REM2")
    private String rem2;

}
