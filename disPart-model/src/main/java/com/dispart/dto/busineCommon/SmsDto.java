package com.dispart.dto.busineCommon;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/25 15:56
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class SmsDto {
    private String type;//短信类型：0-注册验证码 1-通知验证码 2-催缴通知
    private String tel;
    private String param1;//自定义参数1
    private String cost;//费用金额
}