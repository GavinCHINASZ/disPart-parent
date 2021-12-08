package com.dispart.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getDateString(){
        Date nowDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(nowDate);
    }

}
