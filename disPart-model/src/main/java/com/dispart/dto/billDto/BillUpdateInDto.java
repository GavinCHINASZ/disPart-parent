package com.dispart.dto.billDto;

import lombok.Data;

@Data
public class BillUpdateInDto {

    private String billNum; //账单编号

    private String billSt;  //账单状态

    private String payMentMode;  //支付方式

    private String payStatus;  //支付状态

    private String operId;  //操作人

    private String operNm;  //操作人名称

    private String depId;  //部门ID

}
