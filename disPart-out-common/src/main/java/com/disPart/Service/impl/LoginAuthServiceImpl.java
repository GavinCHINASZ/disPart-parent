package com.disPart.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.disPart.Service.LoginAuthService;
import com.dispart.dto.auth.WeiXinDto;
import com.dispart.dto.auth.ZfbDto;
import com.dispart.result.OutCommonResultCodeEnum;
import com.dispart.result.Result;
import com.dispart.utils.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author:xts
 * @date:Created in 2021/6/30 22:10
 * @description
 * @modified by:
 * @version: 1.0
 */
@Service
@Slf4j
public class LoginAuthServiceImpl implements LoginAuthService {
    @Value("${login.weixin.weixinLoginUrl}")
    private String weixinLoginUrl;
    @Value("${login.weixin.weixinAppid}")
    private String weixinAppid;
    @Value("${login.weixin.weixinsSecret}")
    private String weixinsSecret;
    @Value("${login.weixin.weixinGrantType}")
    private String weixinGrantType;
    @Value("${login.zhifubao.zfbLoginUrl}")
    private String zfbLoginUrl;
    @Value("${login.zhifubao.zfbAppId}")
    private String zfbAppId;
    @Value("${login.zhifubao.zfbPrivateKey}")
    private String zfbPrivateKey;
    @Value("${login.zhifubao.zfbPublicKey}")
    private String zfbPublicKey;
    @Value("${login.zhifubao.zfbGrantType}")
    private String zfbGrantType;
    /**
     * 获取微信或支付宝的openID
     *  code
     *  chanlNo 02-微信 03-支付宝
     * @return
     */
    @Override
    public Result loginAuthCheck(String paramStr) {
        log.info("外联区-公共服务-调用微信或支付宝官方授权交易，请求参数为：{}",paramStr);
        if(StringUtils.isEmpty(paramStr)){
            log.info("外联区-公共服务-"+ OutCommonResultCodeEnum.PARAM_NULL.getMessage());
            return Result.build(OutCommonResultCodeEnum.PARAM_NULL.getCode(),OutCommonResultCodeEnum.PARAM_NULL.getMessage());
        }
        String str[]=paramStr.split("&");
        String code=str[0].split("=")[1];
        String chanlNo=str[1].split("=")[1];
        if(chanlNo.equals("02")){
            //调用微信获取openid
            try {
                String resultStr= HttpRequest.httpGet(weixinLoginUrl,"appid="+weixinAppid+"&secret="+weixinsSecret
                        +"&js_code="+code+"&grant_type="+weixinGrantType);
                log.info("微信官方接口返回的数据,{}",resultStr);
                if(StringUtils.isEmpty(resultStr)){
                    log.info("外联区-公共服务-微信登录获取openId失败，{}",resultStr);
                    return Result.build(OutCommonResultCodeEnum.GET_WEIXIN_LOGIN_FAIL.getCode(),OutCommonResultCodeEnum.GET_WEIXIN_LOGIN_FAIL.getMessage());
                }
                //{"session_key":"wsFpgNPZStsmzqPLAJ5XMg==","openid":"ot5SN5Ou09hI7o3QSm8hI1Hm0OwU"}
                JSONObject jsonObject= JSONObject.parseObject(resultStr);
                if(jsonObject.get("openid")==null){
                    log.info("外联区-公共服务-微信登录获取openId失败，{}",resultStr);
                    return Result.build(OutCommonResultCodeEnum.GET_WEIXIN_LOGIN_FAIL.getCode(),OutCommonResultCodeEnum.GET_WEIXIN_LOGIN_FAIL.getMessage());
                }
                WeiXinDto weiXinDto= JSON.parseObject(resultStr,WeiXinDto.class);
                log.info("外联区-公共服务-调用微信授权返回参数解析成功，weiXinDto，{}",JSONObject.toJSONString(weiXinDto));
                return Result.ok(JSONObject.toJSONString(weiXinDto));
            }catch (Exception e){
                log.info("外联区-公共服务-调用微信授权服务异常，{}",e);
            }

        }else if(chanlNo.equals("03")){
            AlipayClient alipayClient=new DefaultAlipayClient(zfbLoginUrl,
                    zfbAppId,zfbPrivateKey,"json","GBK",
                    zfbPublicKey,"RSA2");
            AlipaySystemOauthTokenRequest request=new AlipaySystemOauthTokenRequest();
            request.setGrantType(zfbGrantType);
            request.setCode(code);
            try {
                AlipaySystemOauthTokenResponse response=alipayClient.execute(request);
                if(response.isSuccess()){
                    //调用成功
                    ZfbDto zfbDto=new ZfbDto();
                    zfbDto.setAccessToken(response.getAccessToken());
                    zfbDto.setExpiresIn(response.getExpiresIn());
                    zfbDto.setReExpiresIn(response.getReExpiresIn());
                    zfbDto.setRefreshToken(response.getRefreshToken());
                    zfbDto.setUserId(response.getUserId());
                    return Result.ok(JSONObject.toJSONString(zfbDto));
                }else {
                    //调用失败
                    log.info("外联区-公共服务-支付宝登录获取openId失败，{}");
                    return Result.build(OutCommonResultCodeEnum.GET_ZHIFUBAO_LOGIN_FAIL.getCode(),OutCommonResultCodeEnum.GET_ZHIFUBAO_LOGIN_FAIL.getMessage());
                }
            } catch (AlipayApiException e) {
                log.info("外联区-公共服务-调用支付宝授权服务异常，{}",e);
            }
        }
        return null;
    }
}