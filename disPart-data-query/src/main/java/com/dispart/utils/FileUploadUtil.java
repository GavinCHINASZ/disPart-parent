package com.dispart.utils;

import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
public class FileUploadUtil {

    public static Result post(File file, String remotePath,RestTemplate restTemplate2){
        log.info("文件生成成功，开始上传文件");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("uploadMkdir", remotePath);
        multipartBodyBuilder.part("file", new FileSystemResource(file));
        MultiValueMap<String, HttpEntity<?>> multiValueBody = multipartBodyBuilder.build();
        //上传文件服务
        Result result = null;
        try {
            result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105", multiValueBody, Result.class);
        } catch (Exception e) {
            log.error("调用文件上传服务失败" + e);
            throw new RuntimeException("调用文件上传服务失败");
        }
        if (ObjectUtils.isEmpty(result) || result.getCode() != 200) {
            return Result.build(310,"文件上传失败");
        }
        return result;
    }

}
