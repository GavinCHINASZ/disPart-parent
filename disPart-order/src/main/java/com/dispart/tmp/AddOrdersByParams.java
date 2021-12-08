package com.dispart.tmp;

import com.dispart.dto.orderdto.AddOrdersByParam;
import lombok.Data;

import java.util.List;

@Data
public class AddOrdersByParams {
    private List<AddOrdersByParam> chargeList;
}
