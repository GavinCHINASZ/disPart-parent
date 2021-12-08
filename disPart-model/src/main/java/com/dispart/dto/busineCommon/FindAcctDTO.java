package com.dispart.dto.busineCommon;

import lombok.Data;

@Data
public class FindAcctDTO {

    // 卡号
    private String payCard;

    // 主订单号
    private String mainOrderId;

    // 状态
    private String status;

    private String flag;
    private String book;

    private String jrnlNum;
}
