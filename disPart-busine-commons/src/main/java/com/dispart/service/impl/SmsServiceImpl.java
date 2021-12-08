package com.dispart.service.impl;

import com.dispart.dto.smsdto.SmsDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.SmsService;
import com.dispart.utils.HttpRequest;
import com.dispart.utils.RedisUtil;
import com.dispart.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author:xts
 * @date:Created in 2021/6/12 20:29
 * @description 短信接口实现
 * @modified by:
 * @version: 1.0
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${out-common.smsSendUrl}")
    private String sendSmsUrl;

    /**
     * @param type 0-注册验证码 1-通知验证码 2-账单费用催缴
     * @param tel  手机号
     * @return Result
     */
    @Override
    public Result sendCode(String type, String tel,String param1,String cost) {
        if (StringUtils.isBlank(type) || StringUtils.isBlank(tel)) {
            log.info("业务区发送验证码-" + ResultCodeEnum.PARAM_NULL.getMessage());
            return Result.build(ResultCodeEnum.PARAM_NULL.getCode(), ResultCodeEnum.PARAM_NULL.getMessage());
        }

        // 发送验证码
        String code = SmsUtil.createSmsCode();
        log.info("给手机号（" + tel + "）发送的验证码为:" + code);
        String param = "type=" + type + "&tel=" + tel + "&code=" + code+ "&param1=" + param1+ "&cost=" + cost;
        try {
            String result = HttpRequest.httpPost(sendSmsUrl, param);
            log.info("推送给短信方接口返回的报文，{}", result);
            SmsDto smsDto = com.alibaba.fastjson.JSONObject.parseObject(result, SmsDto.class);
            if (Integer.parseInt(smsDto.getCode()) != ResultCodeEnum.SUCCESS.getCode()) {
                log.info("短信业务-" + smsDto.getRemark());
                return Result.build(Integer.parseInt(smsDto.getCode()), smsDto.getRemark());
            }
            // 存储验证码 过期时间1分钟
            redisUtil.setEx(tel, code, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("推送短信异常", e);
            return Result.fail();
        }
        return Result.ok();
    }
}
