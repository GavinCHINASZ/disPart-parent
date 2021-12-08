package com.dispart.utils;


/**
 * 对密码键盘密文进行解密
 */
public class PwdKeyboardUtil {
    private static final String LIANDI_KEY = "3333333344444444";

    /**
     * 键盘密文解密
     * @param pwd 键盘密文
     * @param cardcode 解密秘钥（约定使用卡号）
     * @return
     */
    public static String genClearTextPwd(String pwd, String cardcode) {
        if (pwd.contains("S160_")) {
            //TODO 键盘加密方法修改后需要变更
            return getFinalPwd_S160(pwd, cardcode);
        }
        return null;
    }


    private static String getCardCode_12(String cardcode) {
        String cardCode12 = cardcode.substring(0, 12);
        return CmsStringUtil.fillLeft(cardCode12, '0', 16);
    }


    private static byte[] exor(byte[] byteArray1, byte[] byteArray2) {
        byte[] result = new byte[byteArray1.length];
        for (int i = 0; i < byteArray1.length; i++) {
            result[i] = (byte) (byteArray1[i] ^ byteArray2[i]);
        }
        return result;
    }

    private static String getFinalPwd_S160(String pwd, String cardCode) {
        pwd = pwd.substring(pwd.indexOf('_')+1, pwd.length());
        cardCode = getCardCode_12(cardCode);
        byte[] bytes = UnionDes.Union3DesDecrypt(LIANDI_KEY.getBytes(), EncryptionHelper.hex2byte(pwd));
        String s2=new String(bytes);
        byte[] clearPwdBytesExor = exor(bytes, cardCode.getBytes());
        int length = clearPwdBytesExor[1];
        byte[] bytesPwd = new byte[length];
        String s1=new String(clearPwdBytesExor);
        System.arraycopy(clearPwdBytesExor,2,bytesPwd,0,length);
        String str=new String(bytesPwd);
        return str;
    }
    public static void main(String[] args) {
        System.out.println(genClearTextPwd("S160_C8D09F6E5BC48205720CE1A44C611BBF","1212121212121213"));
    }

}
