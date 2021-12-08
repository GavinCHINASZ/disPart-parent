package com.dispart.controller;

import com.dispart.dto.deviceManagerDto.DeviceVo;
import com.dispart.result.Result;
import com.dispart.service.PlaySignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@Api(tags = "订单管理")
@Slf4j
@RequestMapping("/securityCenter")
@CrossOrigin
public class HornController {
    @Autowired
    PlaySignService playSignService;
    
    @PostMapping("/DISP20210999")
    @ApiOperation(value = "云喇叭播报")
    public Result<String> getPlaySignService(){
        log.info("开始播报语音。。。。。。");
//        String accessKey="wsz";
//        String accessId = "123456";
//        String domain_prex = "http://localhost:8087/api/v1?";  //联系技术获取测试地址
//
//        TerminalPlayModel terminalPlayModel = new TerminalPlayModel();
//        terminalPlayModel.setAccessId(accessId);
//        terminalPlayModel.setAction("play");
//        terminalPlayModel.setAmount("45.45");
//        terminalPlayModel.setDeviceId("ZS3190800100");
//        terminalPlayModel.setTemplate("{叮咚}{微信支付}{收款}${元}");
//        terminalPlayModel.setTraceId("201812230001");
//        terminalPlayModel.setTraceInfo("test trace");
//        terminalPlayModel.setTimeout(10);
//        terminalPlayModel.setProductKey("a1Z0BXAK0jS");
//        terminalPlayModel.setVolume(100);
//        terminalPlayModel.setRequestId("huzhengwei11111");
//        String playSign = AssenbleManager.getParamStringByModel(terminalPlayModel,accessKey);
//        String path = domain_prex + playSign;
//        log.info("云喇叭报文-播报语音： "+path);
//        String result = sendHttp(path);
//        System.out.println(result);
        return playSignService.getPlaySoundSign("M202107080000400");
    }



//    //发送云喇叭报文
//    public static String sendHttp(String path) {
//        try {
//            URL url = new URL(path );
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5 * 1000);
//            conn.setRequestMethod("GET");
//            InputStream inStream = conn.getInputStream();
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
//            int n = 0;
//            while (-1 != (n = inStream.read(buffer))) {
//                output.write(buffer, 0, n);
//            }
//            byte[] data = output.toByteArray();
//            output.flush();
//            log.info("云喇叭报文-播报语音： "+data.toString());
//            return new String(data, "UTF-8");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "error";
//
//    }
}
