package com.dispart.dto.orderdto;

import lombok.Data;

@Data
public class FindProductInfoByParam {
    private Integer pageNo;
    private Integer pageSize;

    private String customerId;
}
