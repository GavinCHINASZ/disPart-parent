package com.dispart.service;


import com.dispart.result.Result;

/**
 * @author:xts
 * @date:Created in 2021/6/12 18:31
 * @description 短信接口
 * @modified by:
 * @version: 1.0
 */
public interface SmsService {
    /**
     *
     * @param type 0-注册验证码 1-通知验证码
     * @param tel 手机号
     * @return
     */
    Result sendCode(String type, String tel,String param1,String cost);
}