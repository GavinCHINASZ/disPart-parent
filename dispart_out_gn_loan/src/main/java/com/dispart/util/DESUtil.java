package com.dispart.util;

import com.alibaba.fastjson.JSON;
import com.dispart.modle.dto.Disp20210347InDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


@Slf4j
public class DESUtil {


    private static final String KEY_ALGORITHM = "DES";

    /**
     * 默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * 加密密钥
     */
    private static final String KEY = "gnwlyoutgnloandisp20210347";

    /**
     * DES 加密操作
     * @param content
     * 待加密内容
     * @return 返回Base64转码后的加密数据
     */

    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            // 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(KEY));
            // 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);
            // 加密
            return Base64.encodeBase64String(result);
            // 通过Base64转码返回
        } catch (Exception ex) {
            log.error("DES加密异常",ex);
        }
        return null;
    }

    /**
     * } DES 解密操作
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        KeyGenerator generator = null;
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            // 使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(KEY));
            // 执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, "utf-8");
        } catch (Exception ex) {
            log.error("DES解密异常",ex);
        }
        return null;
    }

    /**
     * } } 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String key) {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kg.init(secureRandom);
//            // DES 要求密钥长度为 56
//            kg.init(56, new SecureRandom(key.getBytes()));
//            // 生成一个密钥
            SecretKey secretKey = kg.generateKey();
            kg = null;
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
            // 转换为DES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
