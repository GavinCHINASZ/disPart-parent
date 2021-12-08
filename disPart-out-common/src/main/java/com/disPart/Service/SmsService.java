package com.disPart.Service;

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
     *  type 0-注册验证码 1-通知验证码
     *  tel 手机号
     *  code 验证码
     * @return
     */
    Result sendCode(String parmStr );
}