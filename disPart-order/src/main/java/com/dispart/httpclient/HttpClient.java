package com.dispart.httpclient;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.dispart.utils.SHAUtils;
import com.dispart.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http请求客户端
 */
public class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);


    /**
     * http post请求方法
     * @param url 请求地址
     * @param reqJson 请求报文
     * @return 响应报文
     */
    public static String post(String url, String reqJson) {
        return post(url, reqJson, false);
    }

    /**
     *
     * @param url 请求地址
     * @param reqJson 请求报文
     * @return 响应报文
     */
    public static String post(String url, String reqJson, boolean innerService) {

        try {

            logger.debug("发送的请求报文:{}", reqJson);
            HttpRequest httpRequest = HttpRequest.post(url);
            HttpResponse httpResponse = httpRequest.body(reqJson).timeout(10000).execute();
            String respJson = httpResponse.body();
            return respJson;

        }catch (RuntimeException e) {
            logger.error("外呼请求出现异常,外呼地址:{},外呼请求报文:{}", url, reqJson, e);
            throw new RuntimeException("外呼出现异常:" + e.getMessage());
        }
    }
}
