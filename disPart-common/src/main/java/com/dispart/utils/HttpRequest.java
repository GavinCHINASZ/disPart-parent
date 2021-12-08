package com.dispart.utils;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author:xts
 * @date:Created in 2021/6/21 1:56
 * @description
 * @modified by:
 * @version: 1.0
 */
public class HttpRequest {
    // 向第三方接口发送一个post 请求的参数的看具体的要求，该接口想要的数据是什么类型，如果是json，那就把参数转换为json类型，其他的转换为其它类型，如阿里的接口参数就有的不是json类型
    public static String authPost(String url, String token, String authUrl) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", token);
        httpPost.addHeader("requesturl", authUrl);
        CloseableHttpResponse respons = httpClient.execute(httpPost);
        HttpEntity entity = respons.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        respons.close();
        httpClient.close();
        return responseContent;
    }

    public static String httpPost(String url, String paramStr) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(paramStr, ContentType.create("application/json", "utf-8")));
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

    public static String httpGet(String url, String paramStr) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url+"?"+paramStr);
            httpget.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                //System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    //System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    //System.out.println("Response content: " + EntityUtils.toString(entity));
                    return EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }


}