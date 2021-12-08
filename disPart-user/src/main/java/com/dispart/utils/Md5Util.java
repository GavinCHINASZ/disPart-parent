package com.dispart.utils;

import cn.hutool.crypto.digest.DigestUtil;

import static com.dispart.result.UserResultCodeEnum.USER_PASSWD_ERROR;

public class Md5Util {

    public static String getMd5(String str) throws Exception {
        byte[]  md5bs= DigestUtil.md5(str);
        String md5Str = new String(md5bs,"UTF-8");
        return md5Str;
    }
    public static void main(String[] agrs){

        try {
            System.out.println(getMd5("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
