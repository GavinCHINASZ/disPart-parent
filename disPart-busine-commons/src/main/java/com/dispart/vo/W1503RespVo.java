package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class W1503RespVo extends ResponseBody {
    private static final long serialVersionUID = -1105501901764990812L;
    // 凭证号
    @XStreamAlias("CREDIT_NO")
    private String cerditNo;

    // 交易处理结果
    @XStreamAlias("DEAL_RESULT")
    private String dealResult;

    // 错误原因
    @XStreamAlias("MESSAGE")
    private String message;

}
