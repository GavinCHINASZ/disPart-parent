package com.dispart.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 根据json字符串,获得签名
 */
public class SignUtils {

    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    private static String getSign1(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();

        TreeMap<String, Object> treeMap = new TreeMap<>();

        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value instanceof JSONArray) {
                JSONArray arrayJson = (JSONArray) value;
                ArrayList<String> list = new ArrayList<>();
                for (Object o : arrayJson) {
                    String s = getSign1(((JSONObject) o).toJSONString());
                    list.add(s);
                }
                treeMap.put(key, list);

            } else if(value instanceof JSONObject) {
                String jsonValue = getSign1(((JSONObject) value).toJSONString());
                treeMap.put(key, jsonValue);

            } else {
                if(!StringUtils.isEmpty(value.toString())) {
                    treeMap.put(key, value.toString());
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 签名字段和公共响应字段不参与签名
            if(!"Sign_Inf".equalsIgnoreCase(key) && !"Svc_Rsp_St".equalsIgnoreCase(key)
                && !"Svc_Rsp_Cd".equalsIgnoreCase(key) && !"Rsp_Inf".equalsIgnoreCase(key)) {

                if(value instanceof ArrayList) {
                    for (Object v : ((ArrayList) value)) {
                        builder.append(v);
                    }
                } else {
                    builder.append(key).append("=").append(value).append("&");
                }
            }
        }
        String s = builder.toString();
        logger.debug("签名数据:{}", s);
        return s;
    }

    public static String getSign(String json) {

        String result = getSign1(json);

        if(StringUtils.isEmpty(result)) {
            logger.warn("根据报文组装的签名数据为空");
            return null;
        }

        String sign = result.substring(0, result.length()-1);

        logger.debug("根据报文组装的签名数据:{}", sign);

        return sign;
    }
}
