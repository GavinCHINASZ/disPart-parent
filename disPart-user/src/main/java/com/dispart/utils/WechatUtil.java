package com.dispart.utils;


import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

/**
 * description: 微信小程序 --- 获取用户信息解密
 * @version v1.0
 * @author zhongfei
 * @date 2021年7月21日下午4:00:15
 * @see 
 **/
@Slf4j
public class WechatUtil {
	
	public static final String AES = "AES";
	public static final String AES_CBC_PADDING = "AES/CBC/PKCS7Padding";
 
	/**
     *开放数据校验与解密 <br/>
     *对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br/>
     *对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br/>
     *对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br/>
     *对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br/>
     *@param encrypted 目标密文
     *@param session_key 会话ID
     *@param iv 加密算法的初始向量
     */
    public static String wxDecrypt(String encrypted, String session_key, String iv) throws Exception {
        String result = null;
        byte[] encrypted64 = Base64.decodeBase64(encrypted);
        byte[] key64 = Base64.decodeBase64(session_key);
        byte[] iv64 = Base64.decodeBase64(iv);
        try {
            init();
            byte[] decrStr=decrypt(encrypted64, key64, generateIV(iv64));
            result = StringUtil.getCharToString(StringUtil.getByteToChar(decrStr));
        } catch (Exception e) {
            log.error("解密异常", e);
            throw new Exception("解密异常");
        }
        log.info("用户业务-明文: "+result);
        return result;
    }
	    
   
	    
    /**
     * description: 初始化密钥
     * @throws Exception
     * @return void
     * @version v1.0
     * @author w
     * @date 2021年7月21日下午4:00:15 下午5:07:04
     */
    public static void init() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator.getInstance(AES).init(128);
    }
 
    /**
     * description: 生成iv
     * @param iv
     * @throws Exception
     * @return AlgorithmParameters
     * @version v1.0
     * @author w
     * @date 2021年7月21日下午4:00:15 下午3:50:35
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        // Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(AES);
        params.init(new IvParameterSpec(iv));
        return params;
    }
 
    /**
     * description: 执行解密操作
     * @param encryptedData  加密后的字符串
     * @param keyBytes  密钥key
     * @param iv  便宜向量iv
     * @throws Exception
     * @return byte[]
     * @version v1.0
     * @author w
     * @date 2021年7月21日下午4:00:15 下午3:51:10
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)throws Exception {
        Key key = new SecretKeySpec(keyBytes, AES);
        Cipher cipher = Cipher.getInstance(AES_CBC_PADDING);
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }
    	  
    
    public static void main(String[] args) throws Exception {
		String appid = "wx4f4bc4dec97d474b";
		String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
		String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"+
							"QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"+
							"9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"+
							"3hVbJSRgv+4lGOETKUQz6OYStslQ142d"+
							"NCuabNPGBzlooOmB231qMM85d2/fV6Ch"+
							"evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"+
							"/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"+
							"u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"+
							"/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"+
							"8LOddcQhULW4ucetDf96JcR3g0gfRK4P"+
							"C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"+
							"6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"+
							"/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"+
							"lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"+
							"oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"+
							"20f0a04COwfneQAGGwd5oa+T8yO5hzuy"+
							"Db/XcxxmK01EpqOyuxINew==" ;
						
		String iv = "r7BXXKkLb8qrSNn05n0qiA==";
		
		/*String wxDecrypt = wxDecrypt(encryptedData, sessionKey, iv);*/
		//System.out.println(wxDecrypt);
	}
}