package com.dispart.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
public class DateUtil {

    public static String getDateString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }

    public static Date getStringToDate(String  strdate)  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
          date= df.parse(strdate);
        } catch (Exception e) {
            log.error("数据转换异常", e);
            throw new RuntimeException("数据转换异常");
        }
        return date;
    }

    /**
     * 请求头序列号
     * @param
     * @return
     */
    public static String getReqSeqNo(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        int random4 = (int) (Math.random() * 9000) + 10;
        return df.format(date)+String.valueOf(random4);
    }
}
