package com.dispart.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
public class RSAStaticUtils {

    /**
     * 填充模式
     */
    private final static String RSA_CIPHER = "RSA/ECB/OAEPWithSHA-1AndMGF1PADDING";
    //private final static String RSA_CIPHER = "RSA/ECB/OAEPWithSHA-256AndMGF1PADDING";
    /**
     * 密钥位数
     */
    private static final int KEY_SIZE = 2048;

    private static KeyPair KEY_PAIR;

    static{
        try {
            Security.addProvider(new BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(KEY_SIZE, random);
            KEY_PAIR = generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 生成rsa公钥和私钥
     *
     * @return Map {@link HashMap}: publicKey-公钥(RSAPublicKey),privateKey-私钥(RSAPrivateKey)
     * @author hhs
     * @date 2019-10-22 09:54
     */
    public static Map<String, Object> getRsaKey() {
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
    public static Map<String, String> getX509AndPKCS8Key() {
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
    private static RSAPrivateKey getPrivateKey(String pkcs8Key) {
        PrivateKey privateKey = null;
        try {
//            byte[] decodeKey = (new BASE64Decoder()).decodeBuffer(pkcs8Key);
            byte[] decodeKey = Base64.decodeBase64(pkcs8Key);
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
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
    private static RSAPublicKey getPublicKey(String x509Key) {
        PublicKey publicKey = null;
        try {
//            byte[] decodeKey = (new BASE64Decoder()).decodeBuffer(x509Key);
            byte[] decodeKey = Base64.decodeBase64(x509Key);
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodeKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
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
    public static RSAPublicKey getPublicKey(BigInteger modulus, BigInteger publicExponent) {
        RSAPublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            log.info("----------RSAUtils---------->生成公钥:{}", publicKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
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
    public static RSAPrivateKey getPrivateKey(BigInteger modulus, BigInteger privateExponent) {
        RSAPrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
    public static String encryptPublicKey(String publicKey, String plaintext) {
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
    public static String decryptPublicKey(String publicKey, String ciphertext) {
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
    public static String encryptPrivateKey(String privateKey, String plaintext) {
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
    public static String decryptPrivateKey(String privateKey, String ciphertext) {
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
    private static byte[] decryptByRsaKey(Key key, byte[] ciphertext) throws Exception {
        // Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        Cipher cipher = Cipher.getInstance(RSA_CIPHER);
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
    private static byte[] encryptByRsaKey(Key key, byte[] plaintext) throws Exception {
        // Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        Cipher cipher = Cipher.getInstance(RSA_CIPHER);
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

    public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyMx54wRV3n1J7iHyggoaQvS9+eSN38XEtEtHW2vW/qDpV25DaSLnv0ZicCpO+Xw3ZqC+kvB5a1cdNxuFCvuurFN8YQtgeXQNJMKeSGqrwPQhBzkHtUod4UXSge3AyekNRdhPyvxxBN2my0NCUP+OCJk6Nk/yxeIX1reXVhf2sNMQLbpTulrNxdC/uwZ7rv7PyMSzeH1Zu24ax2HFvmPYK2ezb3ksziBz0uc3uROiWevNrZAfVOgE0co4RjuUPBMBV90WnyEU+i1S96WY3v9P/BLdWDXPD4oWQWbkq4/8smNTTd0JXFuTsMRETTV93NWl3ph4squ00Jo7mhaqhcgs0wIDAQAB";
    public static String privateKey = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQC0pCf4xWlNPw0S+5hqX/3CAosojCw+GT/i1OTDyeFB/YzQWzy/0zy4pjTY10m7oCfYe9z6zbl0KfJkk05wsyBrHVVp922A7LtH+I7Bz2p2PYpLqO3fqtN5b9k96BWrdnL4+Aau+50UZJ20Xg2Saj/CcqIRxtJ6hFP0JcfsRIGC4dtlpb44Vcz2efH6Fdg0bG15cIKz2CSdfUohUNtuOrvo2sBDCfrUYd1RmuwzZjkIID90uyo0DZEy+1tRAOY3riC6p7c2fnaXbBT3NLeHNaTI6owkIJgteXyNwNBTJP7twyqQAumv5sjemUcWk5y1hO28lmzXqwvvWvjZLl8KuKJ/AgMBAAECggEBAJ4qidE50PzQ8zqmzwp9KCxCm+QR7/8jjpunOvLvheuI/U3GRUfUxnsLFIS05JuDHMBhm9iCXxqGS/WiQXFOdlkzowEYo2naXhUjXqP2X8pOEhQzQqyv32CZ/YZF+wtZuEukp/141E3x12ABZjLIEzHHu36DLvIVgYjpt8Z5KUkVzF20tUef+yZoNeIFd+aPeQGMj4+JAxfZswSQBRM4wQM4DMx3kuNI8M5soacaCg5GGidBazJgc+LgkEJ7QM6/reZNRTSf6ubgIHXPloTcoZLkbLGJ3HKJROxNgiGkHSFEk1Cgsj3gnVvt6NokQ+J/6g/D5IscybBmxiUKvGBdiwECgYEA8Gu+zTzpKShwejAEwEIcFZvpahAZW+yHUl8K9EEKZY8/gaq4aUwrBsLqfinUl3LxwpGikTUp2dLI4FayHDVDZ7yg8r94SZDunA5rTr/f7zFbCsdwcLvVzrQ0TLC2+EjDJEWyuJzgGCC6yu26Qeluxl7sfCxTg8mup6Pnnm2pI/8CgYEAwFi/fHB6P2g3WuWwdEo0CwCZfCtzEt59Iqlce6jGcal0INBXY4qrLaBBcUXhxJfe+2Jgo++BFNHWLlHjhqPbxbYehXavPLP/OxOfnEAlE0amvKXWNoxFFKb/G8IPGJO6OcbUyP7G85GcvZH5NBQ9P+rl+Ur6N3pb5pXDzcqmgYECgYEAu6sPTDvQsSz855QWEVFJhOCoSf95HBU86GKCNXmTuQUhiIEP5DntmCCMVKEobnPuHXf7EygRnBN6dAuYioZVMGPAqtwk9B/q8ewbfWQvVBINxBM8RPOKiQiHjpmeqZ56nRS+1na2Qn9B7+ezrYGe08ADD0a2hjfnWrqJsVReZFcCgYEAgtecIqVMs3dMOHwIpYKYlmdpASt5UvugOgTlUYVEwKrZwTyYRbFdsmLSQaCc7KO23mBUhw/0SdpYDRhNYxhx9rNlXbqXh/6Vf3Vg3ORRlCQ6ZheVeXO/xkW3QgWnSTussYLM2pFdevCxo875q3CdHa7Hk+ZqAXUtsNRt5r6Ut4ECgYEAgadKlSr2dEpRaFDiWW0+dRpL4C3eXLgAMny3nFx/kVKayTpmQ3t834ligrowagQ2bcyAecSjIMrR9wt92My5czrTDeiP4RwMyEXYGm4tjNe1NYlgmZpBVQW/25RGhGZPhLzOsY24Te6gF2bZZaXEMeft05xY3sLbHXxEKDN6+7c=";

    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        /*Map<String, String> x509AndPKCS8Key = RSAStaticUtils.getX509AndPKCS8Key();
        String publicKey = x509AndPKCS8Key.get("publicKey");
        System.out.println("------------RSA------公钥------>main:" + "公钥");
        System.out.println(publicKey);
        System.out.println();
        System.out.println("------------RSA------------>main:" + "私钥");
        String privateKey = x509AndPKCS8Key.get("privateKey");
        System.out.println(privateKey);*/
        /*long getKeyEnd = System.currentTimeMillis();
        System.out.println("获得密钥：" + (getKeyEnd - beginTime));
        String aesKey = "6zUwU5McVrZdkKMs";
        String ivKey = "vpgmUFeRd9Ms5o7G";
        String s = RSAStaticUtils.encryptPublicKey(publicKey, aesKey + "@" + ivKey);
        System.out.println("------------RSA------公钥加密------>main:" + s);
        long jiaMiEnd = System.currentTimeMillis();
        System.out.println("加密：" + (jiaMiEnd - getKeyEnd));*/
        String s = "ZlCd8NYlMtxmKwE221akoCz7ya49H06DPEBuL2BICoGsM2+mD9za9G0evCOP3LjQ3OuAZN8KRFYx63muDp8SY/TRvIxSzkRRFwJ2+/JjowY2/TqymeA8RBAq8TuSyW+sMhe6od+QFONk1siGeXWD6N87+dPRD1eWm9e7xqfhN5HzO/bk9ZOf6e+KIXfSQPcwOWTUaiBCPP5tUsaPIb9xnl3USbfzekYEeDj4HkdY4AcnmKQ5CMiy308W+wxZoILCH9DcZKazA19UDWDpXbNok3beIEjbjZAKHK4sYtXI3gcbzdgGgScuiG/iD4NQU3J5C/X8m78hjs3lKGeCQelf/A==";
        System.out.println("------------RSA------私钥解密------>main:" + RSAStaticUtils.decryptPrivateKey(privateKey, s));
        long jieMiEnd = System.currentTimeMillis();
        //System.out.println("解密：" + (jieMiEnd - jiaMiEnd));
        System.out.println("总耗时：" + (jieMiEnd - beginTime));
        System.out.println("======================================================================");
    }
}