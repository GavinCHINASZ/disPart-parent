package com.disPart.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.disPart.Service.SmsService;
import com.disPart.utils.SmsUtil;
import com.dispart.dto.smsdto.SmsDto;
import com.dispart.result.OutCommonResultCodeEnum;
import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Value("${sms.username}")
    private String userName;
    @Value("${sms.password}")
    private String passWord;
    @Value("${sms.templates.token1}")
    private String token1;
    @Value("${sms.templates.templateid1}")
    private String templateid1;
    @Value("${sms.templates.url1}")
    private String url1;
    @Value("${sms.templates.token2}")
    private String token2;
    @Value("${sms.templates.templateid2}")
    private String templateid2;
    @Value("${sms.templates.url2}")
    private String url2;
    @Value("${sms.templates.token3}")
    private String token3;
    @Value("${sms.templates.templateid3}")
    private String templateid3;
    @Value("${sms.templates.url3}")
    private String url3;
    /**
     * type 0-注册验证码 1-通知验证码
     *  tel 手机号
     *  code 验证码
     * @return
     */
    @Override
    public Result sendCode(String parmStr) {

        if(StringUtils.isEmpty(parmStr)){
            log.info(OutCommonResultCodeEnum.PARAM_NULL.getMessage());
            return Result.build( OutCommonResultCodeEnum.PARAM_NULL.getCode(),OutCommonResultCodeEnum.PARAM_NULL.getMessage());
        }
        String str[]=parmStr.split("&");
        String type=str[0].split("=")[1];
        String tel=str[1].split("=")[1];
        String code=str[2].split("=")[1];
        String param1=str[3].split("=")[1];
        String cost=str[4].split("=")[1];
        String beforSign="";
        String postData="";
        long timestamp = System.currentTimeMillis();
        String param="";
        String url="";
        if(type.equals("0")){
            //注册验证码发送
            url=url2;
             param=tel+"|"+code;
             beforSign = "action=sendtemplate&username="+this.userName+"&password="+ SmsUtil.getMD5String(this.passWord)+"&token="+this.token2+"&timestamp="+timestamp;
             postData = "action=sendtemplate&username="+this.userName+"&password="+SmsUtil.getMD5String(this.passWord)+"&token="+this.token2+"&templateid="+this.templateid2+"&param="+param+"&rece=json&timestamp="+timestamp+"&sign="+SmsUtil.getMD5String(beforSign);
        }else if(type.equals("1")){
            //注册验证码发送
            url=url1;
            param=tel+"|"+code;
            beforSign = "action=sendtemplate&username="+this.userName+"&password="+ SmsUtil.getMD5String(this.passWord)+"&token="+this.token1+"&timestamp="+timestamp;
            postData = "action=sendtemplate&username="+this.userName+"&password="+SmsUtil.getMD5String(this.passWord)+"&token="+this.token1+"&templateid="+this.templateid1+"&param="+param+"&rece=json&timestamp="+timestamp+"&sign="+SmsUtil.getMD5String(beforSign);
        }else if(type.equals("2")){
            //注册验证码发送
            url=url3;
            param=tel+"|"+param1+"|"+cost;
            beforSign = "action=sendtemplate&username="+this.userName+"&password="+ SmsUtil.getMD5String(this.passWord)+"&token="+this.token3+"&timestamp="+timestamp;
            postData = "action=sendtemplate&username="+this.userName+"&password="+SmsUtil.getMD5String(this.passWord)+"&token="+this.token3+"&templateid="+this.templateid3+"&param="+param+"&rece=json&timestamp="+timestamp+"&sign="+SmsUtil.getMD5String(beforSign);
        }

        log.debug("短信发送地址:{}", url);
        log.debug("发送短信平台内容:{}", postData);

        String result =SmsUtil. sendPost(url,postData);
        if(StringUtils.isEmpty(result)){
            log.info("外联区-短信业务-"+ OutCommonResultCodeEnum.NOT_EXCEPTION.getMessage());
            return Result.build(OutCommonResultCodeEnum.NOT_EXCEPTION.getCode(),OutCommonResultCodeEnum.NOT_EXCEPTION.getMessage());
        }
        SmsDto smsDto= JSON.parseObject(result,SmsDto.class);
        if(!smsDto.getCode().equals("0")){
            log.info("外联区-短信业务-"+smsDto.getRemark());
            return Result.build(Integer.parseInt(smsDto.getCode()),smsDto.getRemark());
        }
        return Result.ok();
    }
}