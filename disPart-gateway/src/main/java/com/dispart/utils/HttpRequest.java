package com.dispart.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.RequestEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author:xts
 * @date:Created in 2021/6/21 1:56
 * @description
 * @modified by:
 * @version: 1.0
 */
public class HttpRequest {
    // 向第三方接口发送一个post 请求的参数的看具体的要求，该接口想要的数据是什么类型，如果是json，那就把参数转换为json类型，其他的转换为其它类型，如阿里的接口参数就有的不是json类型
    public static String authPost(String url,String token,String authUrl) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization",token);
        httpPost.addHeader("requesturl",authUrl);
        //httpPost.setEntity(new StringEntity(null, ContentType.create("application/json", "utf-8")));
        CloseableHttpResponse respons = httpClient.execute(httpPost);
        if (respons != null && respons.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = respons.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8");
            respons.close();
            httpClient.close();
            return responseContent;
        } else {
            respons.close();
            httpClient.close();

        }
        return null;
    }
    public static String httpPost(String url,String paramStr) throws Exception {
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpPost httpPost=new HttpPost(url);
        httpPost.addHeader("Content-Type","application/json");
        httpPost.setEntity(new StringEntity(paramStr, ContentType.create("application/json","utf-8")));
        CloseableHttpResponse respons=httpClient.execute(httpPost);
        if(respons!=null&&respons.getStatusLine().getStatusCode()==200){
            HttpEntity entity=respons.getEntity();
            String responseContent= EntityUtils.toString(entity,"UTF-8");
            respons.close();
            httpClient.close();
            return responseContent;
        }else{
            respons.close();
            httpClient.close();

        }
        return null;
    }
}