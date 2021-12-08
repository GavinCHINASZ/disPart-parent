package com.dispart.dto.orderdto;

import com.dispart.model.order.TOrderDetailInfo;
import lombok.Data;

@Data
public class MainOrderAndOrders {
    private String mainOrderId;
    private TOrderDetailInfo tOrderDetailInfo;
}
