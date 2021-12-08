package com.dispart.utils;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

public class UnionDes {
    static String DES = "DES/ECB/NoPadding";
    static String TriDes = "DESede/ECB/NoPadding";

    public UnionDes() {
    }

    public static byte[] UnionDesEncrypt(byte[] key, byte[] data) {
        try {
            KeySpec ks = new DESKeySpec(key);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);
            Cipher c = Cipher.getInstance(DES);
            c.init(1, ky);
            return c.doFinal(data);
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new RuntimeException("UnionDesEncrypt failed!Caused:" + var6.getMessage());
        }
    }

    public static byte[] UnionDesDecrypt(byte[] key, byte[] data) {
        try {
            KeySpec ks = new DESKeySpec(key);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);
            Cipher c = Cipher.getInstance(DES);
            c.init(2, ky);
            return c.doFinal(data);
        } catch (Exception var6) {
            var6.printStackTrace();
            throw new RuntimeException("UnionDesDecrypt failed!Caused:" + var6.getCause() + "\n" + var6.getMessage());
        }
    }

    public static byte[] Union3DesEncrypt(byte[] key, byte[] data) {
        try {
            byte[] k = new byte[24];
            int len = data.length;
            if (data.length % 8 != 0) {
                len = data.length - data.length % 8 + 8;
            }

            byte[] needData = null;
            if (len != 0) {
                needData = new byte[len];
            }

            for(int i = 0; i < len; ++i) {
                needData[i] = 0;
            }

            System.arraycopy(data, 0, needData, 0, data.length);
            if (key.length == 16) {
                System.arraycopy(key, 0, k, 0, key.length);
                System.arraycopy(key, 0, k, 16, 8);
            } else {
                System.arraycopy(key, 0, k, 0, 24);
            }

            KeySpec ks = new DESedeKeySpec(k);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
            SecretKey ky = kf.generateSecret(ks);
            Cipher c = Cipher.getInstance(TriDes);
            c.init(1, ky);
            return c.doFinal(needData);
        } catch (Exception var9) {
            var9.printStackTrace();
            throw new RuntimeException("Union3DesEncrypt failed!Caused:" + var9.getMessage());
        }
    }

    public static byte[] Union3DesDecrypt(byte[] key, byte[] data) {
        try {
            byte[] k = new byte[24];
            int len = data.length;
            if (data.length % 8 != 0) {
                len = data.length - data.length % 8 + 8;
            }

            byte[] needData = null;
            if (len != 0) {
                needData = new byte[len];
            }

            for(int i = 0; i < len; ++i) {
                needData[i] = 0;
            }

            System.arraycopy(data, 0, needData, 0, data.length);
            if (key.length == 16) {
                System.arraycopy(key, 0, k, 0, key.length);
                System.arraycopy(key, 0, k, 16, 8);
            } else {
                System.arraycopy(key, 0, k, 0, 24);
            }

            KeySpec ks = new DESedeKeySpec(k);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
            SecretKey ky = kf.generateSecret(ks);
            Cipher c = Cipher.getInstance(TriDes);
            c.init(2, ky);
            return c.doFinal(needData);
        } catch (Exception var9) {
            var9.printStackTrace();
            throw new RuntimeException("Union3DesDecrypt failed!Caused:" + var9.getMessage());
        }
    }

    public static String UnionDecryptData(String key, String data) {
        if (key.length() != 16 && key.length() != 32 && key.length() != 48) {
            return null;
        } else if (data.length() % 16 != 0) {
            return "";
        } else {
            int lenOfKey = key.length();
            String strEncrypt = "";
            byte[] sourData = EncryptionHelper.hex2byte(data);
            switch(lenOfKey) {
                case 16:
                    byte[] deskey8 =EncryptionHelper.hex2byte(key);
                    byte[] encrypt = UnionDesDecrypt(deskey8, sourData);
                    strEncrypt = EncryptionHelper.byte2hex(encrypt);
                    break;
                case 32:
                case 48:
                    String newkey1;
                    if (lenOfKey == 32) {
                        String newkey = key.substring(0, 16);
                        newkey1 = key + newkey;
                    } else {
                        newkey1 = key;
                    }

                    byte[] deskey24 =EncryptionHelper.hex2byte(newkey1);
                    byte[] desEncrypt = Union3DesDecrypt(deskey24, sourData);
                    strEncrypt = EncryptionHelper.byte2hex(desEncrypt);
            }

            return strEncrypt;
        }
    }

    public static String UnionEncryptData(String key, String data) {
        if (key.length() != 16 && key.length() != 32 && key.length() != 48) {
            return null;
        } else if (data.length() % 16 != 0) {
            return "";
        } else {
            int lenOfKey = key.length();
            String strEncrypt = "";
            byte[] sourData = EncryptionHelper.hex2byte(data);
            switch(lenOfKey) {
                case 16:
                    byte[] deskey8 = EncryptionHelper.hex2byte(key);
                    byte[] encrypt = UnionDesEncrypt(deskey8, sourData);
                    strEncrypt = EncryptionHelper.byte2hex(encrypt);
                    break;
                case 32:
                case 48:
                    String newkey1;
                    if (lenOfKey == 32) {
                        String newkey = key.substring(0, 16);
                        newkey1 = key + newkey;
                    } else {
                        newkey1 = key;
                    }

                    byte[] deskey24 =EncryptionHelper.hex2byte(newkey1);
                    byte[] desEncrypt = Union3DesEncrypt(deskey24, sourData);
                    strEncrypt = EncryptionHelper.byte2hex(desEncrypt);
            }

            return strEncrypt;
        }
    }

    public static String Union3DesEncrypt(byte[] key, String data) {
        byte[] byteFieldData3DesEnc = Union3DesEncrypt(key, data.trim().getBytes());
        String strFieldData3DesEnc = EncryptionHelper.byte2hex(byteFieldData3DesEnc);
        return strFieldData3DesEnc;
    }

    public static String Union3DesDecrypt(byte[] key, String strFieldData3DesEnc) {
        byte[] byteFieldData3DesDec = Union3DesDecrypt(key,EncryptionHelper.hex2byte(strFieldData3DesEnc));
        String strFieldData3DesDec = new String(byteFieldData3DesDec);
        return strFieldData3DesDec.trim();
    }

    public static boolean validate1Key(String Mi, String Ming) {
        System.out.println("**************开始验证明文&密文是否配对**************");
        System.out.println("加密数据验证***输入密文数据:" + Mi);
        System.out.println("加密数据验证***输入明文数据:" + Ming);
        if (Mi != null && Mi.length() == 48) {
            if (Ming != null && Ming.length() == 48) {
                String msgKey = Ming.substring(0, 32);
                String msgValue = "0000000000000000";
                byte[] dataBytes = Union3DesEncrypt(EncryptionHelper.hex2byte(msgKey), EncryptionHelper.hex2byte(msgValue));
                String result = EncryptionHelper.byte2hex(dataBytes);
                System.out.println("加密后结果:" + result);
                String MiVal = Mi.substring(32, 48);
                return result.equals(MiVal);
            } else {
                System.out.println("加密数据验证：明文长度不足48！");
                return false;
            }
        } else {
            System.out.println("加密数据验证：密文长度不足48！");
            return false;
        }
    }

    public static boolean validate2Key(String Mi2, String Ming2) {
        if (Mi2 != null && Mi2.length() == 96) {
            if (Ming2 != null && Ming2.length() == 96) {
                String Mi_1 = Mi2.substring(0, 48);
                String Mi_2 = Mi2.substring(48, 96);
                String Ming_1 = Ming2.substring(0, 48);
                String Ming_2 = Ming2.substring(48, 96);
                System.out.println("validate1Key(Mi_1, Ming_1):" + validate1Key(Mi_1, Ming_1));
                System.out.println("validate1Key(Mi_2, Ming_2):" + validate1Key(Mi_2, Ming_2));
                return validate1Key(Mi_1, Ming_1) && validate1Key(Mi_2, Ming_2);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
       /* String mainKey = "0123456789ABCDEF0123456789ABCDEF";
        String Ming = "135245635555555655556";
        System.out.println("加密之前的明文：" + Ming);
        String Mi = Union3DesEncrypt(EncryptionHelper.hex2byte(mainKey), Ming);
        System.out.println("加密之后的密文:" + Mi);
        byte[] mingBytes = Union3DesDecrypt(EncryptionHelper.hex2byte(mainKey), EncryptionHelper.hex2byte(Mi));
        Ming = EncryptionHelper.byte2hex(mingBytes);
        System.out.println("解密之后的明文:" + Ming);
        System.out.println("解密之后的数组:" + (new String(EncryptionHelper.hex2byte(Ming))).trim());*/
    }

    public static void main1(String[] args) {
        /*String key = "11223344556677881122334455667799";
        System.out.println("key size:" + key.length());
        String data = "001122334455667700112233445566880011223344556699111122334455667711112233445566881111223344556699";
        byte[] encDataBytes = Union3DesEncrypt(EncryptionHelper.hex2byte(key), EncryptionHelper.hex2byte(data));
        System.out.println("3DesEncData = " + EncryptionHelper.byte2hex(encDataBytes));
        String enData = "3113BBF607C2034557221D37230C6398";
        byte[] desDataBytes = Union3DesDecrypt(EncryptionHelper.hex2byte(key), EncryptionHelper.hex2byte(enData));
        System.out.println("desData = " + EncryptionHelper.byte2hex(desDataBytes));
        String key1 = "1122334455667788";
        String key2 = "1122334455667799";
        String key3 = "1122334455667700";
        byte[] dataByKey1 = UnionDesEncrypt(EncryptionHelper.hex2byte(key1), data.getBytes());
        System.out.println("dataByKey1 = " + EncryptionHelper.byte2hex(dataByKey1));
        byte[] dataByKey2 = UnionDesDecrypt(EncryptionHelper.hex2byte(key2), dataByKey1);
        System.out.println("dataByKey2 = " + EncryptionHelper.byte2hex(dataByKey2));
        byte[] dataByKey3 = UnionDesEncrypt(EncryptionHelper.hex2byte(key3), dataByKey2);
        System.out.println("dataByKey3 = " + EncryptionHelper.byte2hex(dataByKey3));*/
    }
}

