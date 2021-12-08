package com.dispart.dto.deviceManagerDto;

import lombok.Data;
//播报内容
@Data
public class YunInfoVo {
    private String provId;//供货商ID(客户)
    private String txnAmt;//商品交易金额
    private String additAmt;//附加金额
}
