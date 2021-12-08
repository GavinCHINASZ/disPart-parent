package com.dispart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String convertToDataTime(Date date){
        if (date == null){
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String convertToDate(Date date){
        if (date == null){
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
}
