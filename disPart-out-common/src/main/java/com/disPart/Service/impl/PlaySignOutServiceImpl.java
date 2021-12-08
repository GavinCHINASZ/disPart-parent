package com.disPart.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.disPart.Service.PlaySignOutService;

import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
@Slf4j
public class PlaySignOutServiceImpl implements PlaySignOutService {
    @Value("${service.url.PlaySignOutTo}")
    private String playSignOutTo;

    @Override
    public Result getPlaySoundSign(String path) {
        path=playSignOutTo+path;
        String result = sendHttp(path);
        System.out.println(result);
        if(!"error".equals(result)){
            JSONObject jsonObject = JSON.parseObject(result);

            int errorCode = jsonObject.getInteger("ret");
            String errorMsg= jsonObject.getString("msg");
            return Result.build(result, errorCode,errorMsg);
        }
        return Result.build(result, 201,"通讯失败");
    }

//发送云喇叭报文
    public static String sendHttp(String path) {
        try {
            log.info("接受到播报报文"+path);
            URL url = new URL(path );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            log.info("接受到返回： "+path);
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] data = output.toByteArray();
            output.flush();
            return new String(data, "UTF-8");
        }catch (Exception e){
            log.error("语音播报失败： "+path, e );
        }
        return "error";

    }
}
