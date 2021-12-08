package com.dispart.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@Data
@XStreamAlias("TX")
public class W0101RespMsg extends ResponseHeader{
    private static final long serialVersionUID = 942486598275143881L;

    @XStreamAlias("TX_INFO")
    private W0101RespVo body;
}
