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

            if(innerService) {
                buildSHASign(httpRequest);
            }

            HttpResponse httpResponse = httpRequest.body(reqJson).timeout(10000).execute();

            if(httpResponse.getStatus() != 200) {
                logger.error("http头返回的状态:{}", httpResponse.getStatus());
                throw new RuntimeException("惠市宝返回的http状态代码:" + httpResponse.getStatus());
            }

            String respJson = httpResponse.body();

            logger.debug("收到的响应报文:{}", respJson);

            return respJson;

        }catch (RuntimeException e) {
            logger.error("外呼请求出现异常,外呼地址:{},外呼请求报文:{}", url, reqJson, e);
            throw new RuntimeException("外呼出现异常:" + e.getMessage());
        }
    }

    public static void buildSHASign(HttpRequest httpRequest) {
        SHAUtils shaUtils = SpringUtil.getBean("SHAUtils", SHAUtils.class);
        String nodeId = shaUtils.getNodeId();
        String nodeName = shaUtils.getNodeName();
        String requestTime = shaUtils.getRequestTime();

        httpRequest.header("node-ip", nodeId);
        httpRequest.header("node-name", nodeName);
        httpRequest.header("request-time", requestTime);

        logger.debug("node-ip:{},node-name:{},request-time:{}", nodeId, nodeName, requestTime);

        String temp = "node-ip=" + nodeId +"&node-name=" + nodeName +"&request-time=" + requestTime;
        String sign = shaUtils.getSHA256Str(temp);

        httpRequest.header("sign", sign);
    }

}
