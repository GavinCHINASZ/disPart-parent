package com.dispart.dto.userdto;

import lombok.Data;

@Data
public class UserAppCheckInDto {
    //微信小程序appId
    private String wxpayId;
    //支付宝小程序appId
    private String zfbpayId;
    //渠道号 01 微信 02 支付宝
    private String channel;
}
