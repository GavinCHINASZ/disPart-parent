package com.dispart.dto.hsbdto;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/13 21:24
 * @description 惠市宝签约客户信息增量交易输出信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class HsbCustomDto {
    private String txnSt;//交易状态 00-成功 01-失败
    private String errCode;//错误码
    private String errMsg;//错误描述
}