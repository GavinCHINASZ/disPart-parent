package com.dispart.vo.order;

import lombok.Data;
import lombok.NonNull;


@Data
public class SelectMainOrderAndOrderDetailVo {

    private Integer pageNum;
    private Integer pageSize;
    private String userId;
    private String type;

    private String startTime;
    private String endTime;

    // 传all是查全部
    private String orderStatus;

    private String merchantCode;
}

