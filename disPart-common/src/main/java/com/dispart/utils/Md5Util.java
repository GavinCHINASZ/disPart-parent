package com.dispart.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class Md5Util {
    /**
     * MD5加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String getMd5(String str) throws Exception {
        byte[]  md5bs= DigestUtil.md5(str);
        String md5Str = new String(md5bs,"UTF-8");
        return md5Str;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Md5Util.getMd5("Gz123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
