package com.dispart.utils;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author:xts
 * @date:Created in 2021/6/13 0:11
 * @description http请求工具类
 * @modified by:
 * @version: 1.0
 */
@Slf4j
public class RequestUtil {
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！，{}",e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static String sendGet(String url, String param) {
        log.info("服务端内部请求外部，URL=,{}",url);
        String result="";
        BufferedReader in=null;
        try {
            String urlNameString=url+"?"+param;
            log.info("服务端内部请求外部，拼接后URL=,{}",url);
            URL realUrl=new URL(urlNameString);
            URLConnection connection=realUrl.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","kEEP-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;sv1)");
            connection.connect();
            Map<String, List<String>> map=connection.getHeaderFields();
            for(String key:map.keySet()){
               //遍历响应头信息
            }
            in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line=in.readLine())!=null){
                result +=line;
            }
        }catch (Exception e){
            log.info("发送 GET 请求出现异常！，{}",e);
            e.printStackTrace();
        }
        return result;
    }

}