package com.dispart.utils;

import org.bouncycastle.util.encoders.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密工具类
 */
public class AESUtils {

    public static final String CHAR_ENCODING = "UTF-8";
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] ivKey) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);// 创建密码器
            IvParameterSpec iv = new IvParameterSpec(ivKey);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, seckey, iv);// 初始化
            byte[] result = cipher.doFinal(data);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key,byte[] ivKey) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivKey);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, seckey, iv);
            byte[] result = cipher.doFinal(data);
            return result; // 解密
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encryptToBase64(String data, String key, String iVkey) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING),iVkey.getBytes(CHAR_ENCODING));
            return new String(Base64.encode(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    public static String decryptFromBase64(String data, String key, String iVkey) {
        try {
            byte[] originalData = Base64.decode(data.getBytes());
            final char[] chars = getChars(decrypt(originalData, key.getBytes(CHAR_ENCODING),iVkey.getBytes(CHAR_ENCODING)));
            StringBuffer sb = new StringBuffer();
            for(int i= 0 ;i < chars.length; i++){
                if(chars[i] == '\0'){
                    continue;
                }
                sb.append(chars[i]);
            }
            Arrays.fill(chars,' ');
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    private static char[] getChars(byte[] bytes){
        Charset cs = Charset.forName(CHAR_ENCODING);
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static byte[] genarateRandomKey() {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance(AES_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(" genarateRandomKey fail!", e);
        }
       /* SecureRandom random = new SecureRandom();
        keygen.init(random);*/
        keygen.init(128);
        Key key = keygen.generateKey();
        return key.getEncoded();
    }

    public static String genarateRandomKeyWithBase64() {
        return new String(Base64.encode(genarateRandomKey()));
    }

   /* public static void main(String[] args) {
        //随机生成AES密钥
        String aesKey = SecureRandomUtil.getRandom(16);
        String iVAesKey = SecureRandomUtil.getRandom(16);
        System.out.println(aesKey+"@"+iVAesKey);
        //AES加密数据
        String tokenId = "gfdsgd5354df-gdfs25fds-gfds";
        String data = AESUtils.encryptToBase64("5643654654654365436545-365465@"+tokenId,
                aesKey,iVAesKey);
        System.out.println(data);

        String mwData = AESUtils.decryptFromBase64(data,aesKey,iVAesKey);
        System.out.println("base64:"+mwData);
        String[] arr = mwData.split("\\@");
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println(tokenId.equals(arr[1]));
    }*/
}
