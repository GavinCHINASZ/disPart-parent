package com.dispart.utils.base;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 10:37
 * @ Description:
 */
public class MapUtils {

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    private static  class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }
}
