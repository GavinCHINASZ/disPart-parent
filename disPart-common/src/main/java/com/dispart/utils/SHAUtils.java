package com.dispart.utils;

import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author:xts
 * @date:Created in 2021/6/16 1:28
 * @description 哈希签名工具类
 * @modified by:
 * @version: 1.0
 */
public class SHAUtils {
    public static final String ENCODE_TYPE_HMAC_SHA_256 ="HmacSHA256";
    public static final String ENCODE_UTF_8_UPPER ="UTF-8";

    /**
     * 获得签名值
     * @param secret 密钥字符串
     * @param message   参与签名的消息字符串
     * @return
     * @throws Exception
     */
    public static String getSHA256Str(String secret,String message) throws  Exception {
        if (StringUtils.isEmpty(secret)){
            return null;
        }
        String encodeStr;
        try{
            // HMAC_SHA256 加密
            Mac HMAC_SHA256 = Mac.getInstance(ENCODE_TYPE_HMAC_SHA_256);
            SecretKeySpec secre_spec = new SecretKeySpec(secret.getBytes(ENCODE_UTF_8_UPPER),ENCODE_TYPE_HMAC_SHA_256);
            HMAC_SHA256.init(secre_spec);
            byte[] bytes = HMAC_SHA256.doFinal(message.getBytes(ENCODE_UTF_8_UPPER));
            if (bytes==null&&bytes.length<1){
                return null;
            }
            // 字节转换为16进制字符串
            encodeStr = byteToHex(bytes);
        }catch (Exception e){
            throw new Exception ("HMAC_SHA256 加密运算错误 ....");
        }
        return encodeStr;
    }


    public static void main(String[] args) {
        String times = String.valueOf(System.currentTimeMillis()/1000);
        System.out.println(times);
        String sign = null;
        try {
            sign = SHAUtils.getSHA256Str("KTLFJDAK@543&#23234FdfdsT",
                    "node-ip=127.0.0.1&node-name=jcpt&request-time="+times);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sign);
    }

    /**
     * byte转换
     * @param bytes
     * @return
     */
    private static String byteToHex(byte[] bytes){
        if (bytes==null){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String temp;
        for (int i = 0; i <bytes.length ; i++) {
            temp = Integer.toHexString(bytes[i]&0xff);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
