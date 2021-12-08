package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("TX")
public class W1503RespMsg extends ResponseHeader{
    private static final long serialVersionUID = -4136552210070576278L;

    @XStreamAlias("TX_INFO")
    private W1503RespVo body;

}
