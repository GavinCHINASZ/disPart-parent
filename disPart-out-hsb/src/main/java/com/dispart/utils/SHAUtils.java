package com.dispart.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

/**
 * 到业务系统的签名处理
 */
@Component("SHAUtils")
public class SHAUtils {

    private final Logger logger = LoggerFactory.getLogger(SHAUtils.class);

    @Value("${spring.application.name}")
    private String appName;

    @Value("${sign.secret}")
    private String secret;

    public String getSHA256Str(String message) {
        try {
            String ENCODE_TYPE_HMAC_SHA_256 = "HmacSHA256";
            String ENCODE_UTF_8_UPPER = "UTF-8";

            logger.debug("secret:{}", secret);

            Mac HMAC_SHA256 = Mac.getInstance(ENCODE_TYPE_HMAC_SHA_256);
            SecretKeySpec secre_spec = new SecretKeySpec(secret.getBytes(ENCODE_UTF_8_UPPER), ENCODE_TYPE_HMAC_SHA_256);
            HMAC_SHA256.init(secre_spec);
            byte[] bytes = HMAC_SHA256.doFinal(message.getBytes(ENCODE_UTF_8_UPPER));
            if (bytes==null || bytes.length<1){
                return null;
            }

            return byteToHex(bytes);

        } catch (NoSuchAlgorithmException|InvalidKeyException|UnsupportedEncodingException e) {
            logger.warn("业务系统签名异常", e);
        }

        return null;
    }

    /**
     * 获取本机IP
     * @return 本机IP xxx.xxx.xxx.xxx
     */
    public String getNodeId() {

        String nodeId;

        try {
            nodeId = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.warn("获取机器地址出错", e);
            nodeId = "127.0.0.1";
        }

        return nodeId;
    }

    /**
     * 获取node-name
     */
    public String getNodeName() {
        return appName;
    }

    /**
     * 获取请求时间戳
     */
    public String getRequestTime() {

        long second = Instant.now().getEpochSecond();

        return String.valueOf(second);
    }

    /**
     *
     * @param bytes 需要转换的字节数组
     * @return 16进制
     */
    private String byteToHex(byte[] bytes){
        if (bytes==null){
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xff);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
