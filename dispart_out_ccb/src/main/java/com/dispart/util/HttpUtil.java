package com.dispart.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpUtil {
    public static String post(String url, String entity){
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization","Basic");
            StringEntity se = new StringEntity(entity,"UTF-8");
            se.setContentType("application/json");
            httpPost.setEntity(se);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity1 = response.getEntity();
            String resStr = null;
            if(entity1 != null){
                resStr = EntityUtils.toString(entity1,"UTF-8");
            }
            httpClient.close();
            response.close();
            return resStr;
        }catch (Exception e){
            log.error("HTTP请求异常",e);
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "http://128.11.132.8:8092/outGnLoan/DISP20210347";
        String entity = "jP/Wnz6zKINaqPMkCPdOEg0O9+POFAOLPmEeYJjxPUaOSVmTQRvnB57dfKNEmfijd6/+NliwitYNDEcobJZqC4UYKqFNFDXsAH+fjKiGmk8=";
        String post = HttpUtil.post(url, entity);
        System.out.println(post);
    }
}
