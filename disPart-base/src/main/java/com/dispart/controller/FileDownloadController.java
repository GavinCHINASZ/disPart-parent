package com.dispart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequestMapping
@RestController
@Slf4j
public class FileDownloadController {

    @PostMapping("/test")
    public void fileDownload(HttpServletResponse response, String fileName){

        log.info("下载任务开始");
        byte[] buff = new byte[1024];
        BufferedInputStream bi = null;
        OutputStream outputStream =null;
        File file = null;
        try {
            File path = new File(ResourceUtils.getURL("classpath").getPath());
            if(!path.exists()) path = new File("");
            file = new File(path.getAbsolutePath(),"upload/" + fileName);
            if(!file.exists()){
                log.info("文件资源不存在:" + path.getAbsolutePath() + "upload/" + fileName);
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName);
        try {
            outputStream = response.getOutputStream();
            bi = new BufferedInputStream(new FileInputStream(file));
            int read = bi.read(buff);
            while (read != -1){
                outputStream.write(buff,0,buff.length);
                outputStream.flush();
                read = bi.read(buff);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("下载失败");
        }finally {
            if(bi !=null){
                try {
                    bi.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    throw new RuntimeException("下载失败");
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    throw new RuntimeException("下载失败");
                }
            }
        }
        log.info("文件下载完成");
        return;
    }
}
