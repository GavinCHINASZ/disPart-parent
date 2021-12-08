package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
public class W1503ReqVo extends RequestBody{
    private static final long serialVersionUID = 5132051344208457756L;

    //原请求序列号
    @XStreamAlias("REQUEST_SN1")
    private String requestSn1;

}
