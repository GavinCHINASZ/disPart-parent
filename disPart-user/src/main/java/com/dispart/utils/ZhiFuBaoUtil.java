package com.dispart.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description: 支付宝小程序 --- 获取用户信息解密
 * @version v1.0
 * @author zhongfei
 * @date 2021年7月21日下午4:00:15
 * @see
 **/
@Component
@Slf4j
public class ZhiFuBaoUtil {

    //小程序对应的支付宝公钥
    @Value("${zhifubao.signVeriKey}")
    private String signVeriKey;
    //小程序对应的加解密密钥
    @Value("${zhifubao.decryptKey}")
    private String decryptKey;
    /**
     *
     * @param response 小程序前端返回的加密信息
     * @return
     * @throws Exception
     */
    public  String decode(String response) throws Exception {
        //1. 获取验签和解密所需要的参数
        Map<String, String> openapiResult = JSON.parseObject(response, new TypeReference<Map<String, String>>() {
        }, Feature.OrderedField);
        String signType = "RSA2";
        String charset = "UTF-8";
        String encryptType = "AES";
        String sign = openapiResult.get("sign");
        String content = openapiResult.get("response");
        //判断是否为加密内容
        boolean isDataEncrypted = !content.startsWith("{");
        boolean signCheckPass = false;
        //2. 验签
        String signContent = content;
//        String signVeriKey = "你的小程序对应的支付宝公钥（为扩展考虑建议用appId+signType做密钥存储隔离）";
//        String decryptKey = "你的小程序对应的加解密密钥（为扩展考虑建议用appId+encryptType做密钥存储隔离）";//如果是加密的报文则需要在密文的前后添加双引号
        if (isDataEncrypted) {
            signContent = "\"" + signContent + "\"";
        }
        try {
            signCheckPass = AlipaySignature.rsaCheck(signContent, sign, signVeriKey, charset, signType);
        } catch (AlipayApiException e) {
            // 验签异常, 日志
            log.error("验签异常", e);
        }
        if (!signCheckPass) {
            //验签不通过（异常或者报文被篡改），终止流程（不需要做解密）
            log.info("用户业务-验签失败");
            throw new Exception("验签失败");
        }
        //3. 解密
        String plainData = null;
        if (isDataEncrypted) {
            try {
                plainData = AlipayEncrypt.decryptContent(content, encryptType, decryptKey, charset);
            } catch (AlipayApiException e) {
                //解密异常, 记录日志
                log.error("解密异常", e);
                throw new Exception("解密异常");
            }
        } else {
            plainData = content;
        }
        log.info("用户业务-明文: "+plainData);
        return plainData;
    }

}
