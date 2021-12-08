package com.dispart.utils.base;

//import com.yun.api.sdk.model.BaseModel;
//import com.yun.api.sdk.utils.MapUtils;
//import com.yun.api.sdk.utils.ReflectUtils;
//import com.yun.api.sdk.utils.SignUtils;
import com.dispart.vo.horn.BaseModel;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 11:52
 * @ Description:
 */
public class AssenbleManager {

    public static String getParamStringByModel(BaseModel baseModel,String appKey){
        Map<String,String > map = ReflectUtils.reflect(baseModel);
        map = MapUtils.sortMapByKey(map);
        String originString = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            originString += entry.getKey() + "=" + String.valueOf(entry.getValue()) + "&";
        }
        originString = originString.substring(0,originString.length() -1);
        String sign = SignUtils.checkSignValid(originString,appKey);
        String encodeString = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            encodeString += entry.getKey() + "=" + getURLEncoderString(String.valueOf(entry.getValue())) + "&";
        }
        encodeString = encodeString +"&" + "signatureString=" + getURLEncoderString(sign);
        return encodeString;
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
