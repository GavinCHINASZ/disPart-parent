package com.dispart.dto.smsdto;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/12 18:33
 * @description 接收短信接口返回信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class SmsDto {
    private String returnstatus;//响应码
    private String code;//响应返回码
    private String remark;//响应错误返回说明
}