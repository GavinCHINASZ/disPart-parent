package com.dispart.vo.order;

import com.dispart.model.order.TOrderDetailInfo;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageOrderDTO {

    private long total;
    private List<OrderListReturn> list;
}
