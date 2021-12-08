package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("TX")
public class W1303RespMsg extends ResponseHeader{
    private static final long serialVersionUID = 5313471042522947530L;

    @XStreamAlias("TX_INFO")
    private W1303RespVo body;
}
