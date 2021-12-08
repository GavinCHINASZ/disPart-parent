package com.dispart.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author:xts
 * @date:Created in 2021/6/13 20:48
 * @description 日期处理工具
 * @modified by:
 * @version: 1.0
 */
public class DateUtil {
    /**
     * 获取当前时间的时间戳
     * @return
     */
    public static  String getTimeStamp(){
        return System.currentTimeMillis()+"";
    }

    /**
     * 获取当前时间字符串
     * @param format
     * @return
     */
    public static  String getDateToString(String format){
        SimpleDateFormat df=new SimpleDateFormat(format);
        return df.format(new Date());
    }
    public static  String getDateToStringx(String format){
        SimpleDateFormat df=new SimpleDateFormat(format);
        String datestr=df.format(new Date());
        String rd=new Random().nextInt(9999) +"";
        return rd;
    }
    public static void main(String[] args) {
        System.out.println(DateUtil.getTimeStamp());
        System.out.println(DateUtil.getDateToStringx("YYYYMMddHHmmss"));
    }
}