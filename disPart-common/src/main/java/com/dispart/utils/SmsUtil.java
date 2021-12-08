package com.dispart.utils;

import java.util.Random;

/**
 * @author:xts
 * @date:Created in 2021/6/13 0:14
 * @description 短信工具类
 * @modified by:
 * @version: 1.0
 */
public class SmsUtil {
    /**
     * 生成短信验证码
     * @return
     */
    public static String createSmsCode(){
        Random random=new Random();
        String code="";
        for(int i=0;i<6;i++){
            code+=random.nextInt(10);
        }
        return code;
    }
    public static void main(String[] args) {
        System.out.println(SmsUtil.createSmsCode());
    }
}