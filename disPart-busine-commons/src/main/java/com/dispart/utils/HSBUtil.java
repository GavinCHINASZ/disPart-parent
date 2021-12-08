package com.dispart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:zhongfei
 * @date:Created in 2021/6/20 18:59
 * @description 格式化时间
 * @modified by:
 * @version: 1.0
 */
public class HSBUtil {
    /**
     * 返回时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 根据时间戳生成32位流水号
     * @param id
     * @return
     */
    public static String getTimeStampSeq(int id) {
        long time = System.currentTimeMillis();
        String seqId = time + String.format("%0" + 19 + "d", id);
        return seqId;
    }
}
