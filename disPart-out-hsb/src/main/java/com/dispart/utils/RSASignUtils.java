package com.dispart.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加签和验签功能
 */
public class RSASignUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSASignUtils.class);

    //加签
    public static String sign(String data) {

        if(StringUtils.isEmpty(data)) {
            return null;
        }

        PrivateKey privateKey = RSAKeyUtils.getLocalPrivateKey();

        return Base64.encodeBase64String(sign(privateKey, data));
    }

    private static byte[] sign(PrivateKey privateKey, String data) {
        byte[] signed;

        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(data.getBytes());
            signed = sign.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.error("报文加签出现异常", e);
            throw new RuntimeException("报文加签出现异常");
        }

        return signed;
    }


    /**
     * 验证签名
     * @param json 响应的json字符串
     */
    public static void verify(String json) {

        JSONObject respJson = JSONObject.parseObject(json);

        Object signed = respJson.get("Sign_Inf");
        if(signed == null || ((String) signed).trim().length() == 0) {
            logger.warn("签名字段为空,不进行验签处理");
            return;
        }

        String signInf = signed.toString();
        logger.debug("响应报文中的数字签名:{}", signInf);

        String sign = SignUtils.getSign(json);
        logger.debug("根据响应报文生成的数字签名原串:{}", sign);

        PublicKey publicKey = RSAKeyUtils.getHsbPublicKey();

        boolean verifySign = verifySign(publicKey, sign, signInf);
        logger.info("数字签名校验结果:{}", verifySign);

        if(!verifySign) {
            logger.error("数字签名校验不通过");
            throw new RuntimeException("签名校验不通过");
        }
    }

    /**
     * 验证签名
     * @param data 原始数据
     * @param signInf 签名数据
     */
    public static void verify(String data, String signInf) {
        PublicKey publicKey = RSAKeyUtils.getHsbPublicKey();

        logger.debug("验签字符串:{}", data);

        boolean verifySign = verifySign(publicKey, data, signInf);
        logger.info("数字签名校验结果:{}", verifySign);

        if(!verifySign) {
            logger.error("数字签名校验不通过");
            throw new RuntimeException("签名校验不通过");
        }
    }

    private static boolean verifySign(PublicKey publicKey, String data, String signed) {
        boolean result;

        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(publicKey);
            sign.update(data.getBytes());
            result = sign.verify(Base64.decodeBase64(signed.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.error("验证签名出现异常", e);
            throw new RuntimeException("验证签名出现异常");
        }

        return result;
    }

    /**
     * 恢复惠市宝公钥
     */
    public static PublicKey restorePublicKey(String key) {
        PublicKey publicKey;

        byte[] bytes = Base64.decodeBase64(key.getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("恢复公钥出现异常", e);
            throw new RuntimeException("获取公钥出现异常");
        }

        return publicKey;
    }

    /**
     * 恢复本地私钥 base64格式的字符串转换privateKey
     */
    public static PrivateKey restorePrivateKey(String privateKey) {
        PrivateKey privateKey1;

        byte[] bytes = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey1 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("恢复私钥出现异常", e);
            throw new RuntimeException("获取私钥失败");
        }

        return privateKey1;
    }
}
