package com.dispart.utils;
import java.lang.reflect.Field;
/**
 * @author:xts
 * @date:Created in 2021/6/13 22:38
 * @description 普通java对象转换处理
 * @modified by:
 * @version: 1.0
 */
public class JavaBeanUtil {
    /**
     * 赋值给同类对象
     * 将origin属性注入到destination中
     * @param origin
     * @param destination
     * @param <T>
     */
    public static  <T> void mergeObject(T origin, T destination) {
        if (origin == null || destination == null)
            return;
        if (!origin.getClass().equals(destination.getClass()))
            return;

        Field[] fields = origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(origin);
                if (null != value && !"".equals(value)) {
                    fields[i].set(destination, value);
                }
                fields[i].setAccessible(false);
            } catch (Exception e) {
            }
        }
    }
}
