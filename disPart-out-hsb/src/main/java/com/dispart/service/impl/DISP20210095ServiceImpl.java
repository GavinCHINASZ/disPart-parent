package com.dispart.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.dispart.httpclient.HttpClient;
import com.dispart.utils.RSASignUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * 惠市宝推送对账文件服务
 */
@Service
public class DISP20210095ServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(DISP20210095ServiceImpl.class);

    @Value("${service.url.DISP20210064}")
    private String url;

    @Value("${local.file.path}")
    private String localFilePath;

    public void service(HttpServletRequest request) {

        MDC.put("serviceId", "DISP20210095");
        MDC.put("traceId", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));

        try {
            service0(request);
        } catch (RuntimeException e) {
            logger.error("接收文件服务出现异常", e);
            throw new RuntimeException("接收文件服务出现异常");
        }finally {
            MDC.clear();
        }

    }

    private void service0(HttpServletRequest request) {
        String signInf = request.getParameter("Sign_Inf");
        String smryInf = request.getParameter("File_Smry_Inf");

        logger.debug("接收到的文件摘要:{}", smryInf);
        logger.debug("接收到的签名信息:{}", signInf);

        // 验证签名数据
        RSASignUtils.verify("File_Smry_Inf="+smryInf, signInf);

        // 保存文件
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart) {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();

            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String filename = file.getOriginalFilename();

                logger.debug("接收的文件名:{}", filename);

                try {
                    file.transferTo(new File(localFilePath + filename));
                } catch (IOException e) {
                    logger.error("保存文件出现异常", e);
                    throw new RuntimeException("保存文件出现异常");
                }

                logger.debug("获取对账文件的请求URL地址:{}", url);

                // 发送文件
                HttpRequest httpRequest = HttpRequest.post(url);
                // 加签
                HttpClient.buildSHASign(httpRequest);
                HttpResponse httpResponse = httpRequest.form("file", new File(localFilePath + filename)).execute();
                if(httpResponse.getStatus() != 200) {
                    logger.error("发送文件失败,失败状态:{},请求地址:{},文件名:{}", httpResponse.getStatus(), url, filename);
                    throw new RuntimeException("发送文件失败");
                }

                String respMsg = httpResponse.body();
                logger.info("接收到的响应报文:{}", respMsg);

            }
        }
    }
}
