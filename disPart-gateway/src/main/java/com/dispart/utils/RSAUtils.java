package com.dispart.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA处理工具类
 *
 * @author hhs
 * @date 2019-10-22 09:54
 * @since JDK1.8
 */
@Slf4j
public class RSAUtils {

    private  KeyPair KEY_PAIR;

    /* {
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","BC");
            generator.initialize(2048, random);
            KEY_PAIR = generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

   /* public RSAUtils(){
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA","BC");
            generator.initialize(2048, random);
            KEY_PAIR = generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


    /**
     * 生成rsa公钥和私钥
     *
     * @return Map {@link HashMap}: publicKey-公钥(RSAPublicKey),privateKey-私钥(RSAPrivateKey)
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public Map<String, Object> getRsaKey() {
        Map<String, Object> keyInfo = new HashMap<>(2);
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) KEY_PAIR.getPublic();
        keyInfo.put("publicKey", publicKey);
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) KEY_PAIR.getPrivate();
        keyInfo.put("privateKey", privateKey);
        return keyInfo;
    }


    /**
     * 生成rsa公钥和私钥
     *
     * @return Map {@link HashMap}: publicKey-公钥(X509格式),privateKey-私钥(PKCS8格式)
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public Map<String, String> getX509AndPKCS8Key() {
        Map<String, Object> keyInfo = getRsaKey();
        Map<String, String> x509AndPKCS8Key = new HashMap<>(2);
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyInfo.get("publicKey");
//        String rsaPublicKey = (new BASE64Encoder()).encodeBuffer(publicKey.getEncoded());
        String rsaPublicKey = Base64.encodeBase64String(publicKey.getEncoded());
        x509AndPKCS8Key.put("publicKey", rsaPublicKey);
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyInfo.get("privateKey");
//        String rsaPrivateKey = (new BASE64Encoder()).encodeBuffer(privateKey.getEncoded());
        String rsaPrivateKey = Base64.encodeBase64String(privateKey.getEncoded());
        x509AndPKCS8Key.put("privateKey", rsaPrivateKey);
        return x509AndPKCS8Key;
    }


    /**
     * PKCS8的私钥字符串还原为RSA私钥
     *
     * @param pkcs8Key {@link String} 待还原私钥字符串
     * @return RSAPrivateKey {@link RSAPrivateKey}
     * @author hhs
     * @date 2019-10-22 09:54
     */
    private RSAPrivateKey getPrivateKey(String pkcs8Key) {
        PrivateKey privateKey = null;
        try {
//            byte[] decodeKey = (new BASE64Decoder()).decodeBuffer(pkcs8Key);
            byte[] decodeKey = Base64.decodeBase64(pkcs8Key);
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            privateKey = keyFactory.generatePrivate(pkcs8);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("----------RSAUtils---------->PKCS8的私钥字符串还原为RSA私钥出错:{}", e.getMessage());
        }
        return (RSAPrivateKey) privateKey;
    }


    /**
     * X509的公钥字符串还原为RSA公钥
     *
     * @param x509Key {@link String} 待还原公钥字符串
     * @return RSAPublicKey {@link RSAPublicKey}
     * @author hhs
     * @date 2019-10-22 09:54
     */
    private RSAPublicKey getPublicKey(String x509Key) {
        PublicKey publicKey = null;
        try {
//            byte[] decodeKey = (new BASE64Decoder()).decodeBuffer(x509Key);
            byte[] decodeKey = Base64.decodeBase64(x509Key);
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            publicKey = keyFactory.generatePublic(x509);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("----------RSAUtils---------->X509的公钥字符串还原为RSA公钥:{}", e.getMessage());
        }
        return (RSAPublicKey) publicKey;
    }


    /**
     * <p>使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     * </p>
     *
     * @param modulus        {@link BigInteger} 模
     * @param publicExponent {@link BigInteger} 公钥指数
     * @return RSAPublicKey {@link RSAPublicKey}
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public RSAPublicKey getPublicKey(BigInteger modulus, BigInteger publicExponent) {
        RSAPublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            log.info("----------RSAUtils---------->生成公钥:{}", publicKey);
        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("----------RSAUtils---------->生成公钥异常:{}", e.getMessage());
        }
        return publicKey;
    }


    /**
     * <p>
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA/None/NoPadding】
     * </p>
     *
     * @param modulus         {@link BigInteger} 模
     * @param privateExponent {@link BigInteger} 私钥指数
     * @return RSAPrivateKey {@link RSAPrivateKey}
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger privateExponent) {
        RSAPrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, privateExponent);
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("----------RSAUtils---------->生成私钥异常:{}", e.getMessage());
        }
        return privateKey;
    }


    /**
     * 公钥加密
     *
     * @param publicKey {@link String} X509格式公钥
     * @param plaintext {@link String} 明文
     * @return String {@link String} 密文
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public String encryptPublicKey(String publicKey, String plaintext) {
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        String result = "";
        try {
            byte[] bytes = encryptByRsaKey(rsaPublicKey, plaintext.getBytes());
            result = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("----------RSAUtils---------->公钥加密异常:{}", e.getMessage());
        }
        return result;
    }


    /**
     * 公钥解密
     *
     * @param publicKey  {@link String} X509格式公钥
     * @param ciphertext {@link String} 密文
     * @return String {@link String} 明文
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public String decryptPublicKey(String publicKey, String ciphertext) {
        byte[] bytes = Base64.decodeBase64(ciphertext);
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        String result = "";
        try {
            result = new String(decryptByRsaKey(rsaPublicKey, bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("----------RSAUtils---------->私钥解密异常:{}", e.getMessage());
        }
        return result;
    }


    /**
     * 私钥加密
     *
     * @param privateKey {@link String} PKCS8格式私钥
     * @param plaintext  {@link String} 明文
     * @return String {@link String} 密文
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public String encryptPrivateKey(String privateKey, String plaintext) {
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        String result = "";
        try {
            byte[] bytes = encryptByRsaKey(rsaPrivateKey, plaintext.getBytes());
            result = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            log.error("----------RSAUtils---------->公钥加密异常:{}", e.getMessage());
        }
        return result;
    }


    /**
     * 私钥解密
     *
     * @param privateKey {@link String} PKCS8格式私钥
     * @param ciphertext {@link String} 密文
     * @return String {@link String}
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public String decryptPrivateKey(String privateKey, String ciphertext) {
        byte[] bytes = Base64.decodeBase64(ciphertext);
        RSAPrivateKey rsaPrivateKey = getPrivateKey(privateKey);
        String result = "";
        try {
            result = new String(decryptByRsaKey(rsaPrivateKey, bytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("----------RSAUtils---------->私钥解密异常:{}", e.getMessage());
        }
        return result;
    }


    /**
     * 数据分组解密
     *
     * @param key                 {@link Key} RSA公钥或者私钥
     * @param ciphertext-密文byte数组
     * @return byte[] 明文byte数组
     * @throws Exception {@link Exception} 异常数据
     * @author hhs
     * @date 2019-10-22 09:54
     */
    private byte[] decryptByRsaKey(Key key, byte[] ciphertext) throws Exception {
        // Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1PADDING", "BC");
        cipher.init(2, key);
        int inputLen = ciphertext.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        for (int i = 0; inputLen - offSet > 0; offSet = i * 256) {
            byte[] cache;
            if (inputLen - offSet > 256) {
                cache = cipher.doFinal(ciphertext, offSet, 256);
            } else {
                cache = cipher.doFinal(ciphertext, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }


    /**
     * 数据分组加密
     *
     * @param key                {@link Key} Rsa公钥或私钥
     * @param plaintext-明文byte数组
     * @return byte[] 密文byte数组
     * @throws Exception {@link Exception} 异常数据
     * @author hhs
     * @date 2019-10-22 09:54
     */
    private byte[] encryptByRsaKey(Key key, byte[] plaintext) throws Exception {
        // Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1PADDING", "BC");
        cipher.init(1, key);
        int inputLen = plaintext.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        for (int i = 0; inputLen - offSet > 0; offSet = i * 244) {
            byte[] cache;
            if (inputLen - offSet > 244) {
                cache = cipher.doFinal(plaintext, offSet, 244);
            } else {
                cache = cipher.doFinal(plaintext, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            ++i;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static void main(String[] args) {
        RSAUtils rsaUtils = new RSAUtils();
        Map<String, String> x509AndPKCS8Key = rsaUtils.getX509AndPKCS8Key();
        String publicKey = x509AndPKCS8Key.get("publicKey");
        System.out.println("------------RSA------公钥------>main:" + "公钥");
        System.out.println(publicKey);
        System.out.println();
        System.out.println("------------RSA------------>main:" + "私钥");
        String privateKey = x509AndPKCS8Key.get("privateKey");
        System.out.println(privateKey);

        String str = "pUMLe6szRGXyMjQp@mHtoZvc9vB0WgH1M";
        String s = rsaUtils.encryptPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3+pQast12ew9tSX0QxdJc+G5LlNQAXCqfGytuoEsKBsjz8QtZMIHUHLxce6nAVAJOgg8Gr3hFILpq5+Ltxb98tvws6L0FscV3eqgvaSDFTlldIv+9VTgU/6Fef9P4h1qHRTXmS/fz2DtagAXrSYFTrlqZ/LuJVw5GIBamsO85K/sQYyK8KPce+t5IjTyDnWARrg4nzAkJYcBvc8VXymeVTkVkLJeqR9Loany0bvCzdiwMKqNuw8uMwhb9JMCSVdizE4tdYTCsyS6Yhkp08Jjztjp7ykxHzqYh01KrM/RcrleOaYPP5oAvuh6hpeMITbqJg+joVcfKf4L6JJIcfz1nQIDAQAB", str);
        System.out.println("------------RSA------公钥加密------>main:" + s);


        System.out.println("------------RSA------私钥解密------>main:" + rsaUtils.decryptPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDf6lBqy3XZ7D21JfRDF0lz4bkuU1ABcKp8bK26gSwoGyPPxC1kwgdQcvFx7qcBUAk6CDwaveEUgumrn4u3Fv3y2/CzovQWxxXd6qC9pIMVOWV0i/71VOBT/oV5/0/iHWodFNeZL9/PYO1qABetJgVOuWpn8u4lXDkYgFqaw7zkr+xBjIrwo9x763kiNPIOdYBGuDifMCQlhwG9zxVfKZ5VORWQsl6pH0uhqfLRu8LN2LAwqo27Dy4zCFv0kwJJV2LMTi11hMKzJLpiGSnTwmPO2OnvKTEfOpiHTUqsz9FyuV45pg8/mgC+6HqGl4whNuomD6OhVx8p/gvokkhx/PWdAgMBAAECggEAEiHMT0mYioQZ3yBqrkAlf4/IyhqPAP7Zn2fBjbfGFp1UAhAVyPH5W6djK+GyfmW3yY4/J782ic2sFGzACJyBBfiPfkx0zZ9jguvsBk9bGg3izFRF2iH8ZHDY6C8njdzp6d71Mn9w9T8ZGptVT30jsuNK3Y4LgNT9/qDuNVgiZLY/dmyEghPbZScjIGYA7lebU79s+KpqG7lNAAZroOLDALBp32ZoyZ5dEdBzjaROTjGHYWU2Fgt3bYd2c71lQ3ywwVu4+f8SmPBXLMDpKBUVCLFFZlhpBJnsql9EiSn+qAytMcJEKJJrzkXJXZIUPSub7vx5HrRM3tcg3srZ5uxK1QKBgQD1yQmRJiWBRBIJvKievAqpUPxHuw96AibnzjA3X33TJv2CPnlgaeyefWg/FM1LGCa12vLSuqcvFxeKPGP5DBrH51bN2He9saTlb5QVZkQBkeq21nx4DJSGD/0zHgF1iAOZ3P48xQXc1UJkmIGtv1F+VBvAYrs6D9ogHSAvZE344wKBgQDpOJjUtyHov504bXPzBZiiN+3I2VoUNQ8fa2Ejl3UxhS+iMAIWzHrzwPUVB6jEGE1TSikEyyxBPm+vwAFzasaaUC09SA2FgwWXPwV+gPhrtBy+t1bXagLF5dRp7YUwvdUcLsJCvKMTbhUNCXaYbM4qVQtf4ioSxuGbh7uUUBkffwKBgEPA4YRDsb8KlrZcbVckmwPFsWXMqR2HDlaEPkJlbngQnX2T3z22tWWTSHNrNpRm2fTzoFB6569RfEm9EAoNTiyhFwqzi36dQ8mDkpy7ji+om70/LxygyyiRq8i1ks1layi1BNiXr2AK4bx9VfIhdUOPx/6muNnumMCVhtPJoLzlAoGAXtghbwA6zgh+y5/Xc5FnDTFphjC+LVGWoNRuYKcLheQABxk3AEaQCekCwlanD3hCmgiivcxSZwYZLYQMEv/tXHwWqcFPnmg2MdfzSAry+/n3ZqfwrRHzpr9crudlLvlEXX22iqVkPFacQP0EOeClxxlm0suLCK/QuAtvAVXBockCgYEAq3Znj/oBMkWzxHEFVjlRCM2RBrl8FZlWPcporFMs+ULqSRQvk3+TTJU4AwunsMILuRkKSAVnnbRTO7WzIlKf4azHCSWx+tOTd7RlcUPXCjMD+iwo1RWeu9kio8brZmEjQ6F8LGw8OCHewgfDED+fDW+3prdTqhkRBVJVrjipqb0=", s));
    }
}