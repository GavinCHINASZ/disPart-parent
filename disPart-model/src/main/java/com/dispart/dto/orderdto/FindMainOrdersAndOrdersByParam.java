package com.dispart.dto.orderdto;

import lombok.Data;

@Data
public class FindMainOrdersAndOrdersByParam {
    private Integer pageNo;
    private Integer pageSize;

    private String customerId;
    private String orderStatus;
    private String orderStartTime;
    private String orderEndTime;
}
