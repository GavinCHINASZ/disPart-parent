package com.dispart.service;

import com.dispart.request.Request;
import com.dispart.result.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:xts
 * @date:Created in 2021/6/18 18:01
 * @description 文件处理接口
 * @modified by:
 * @version: 1.0
 */
public interface FileService {
    /**
     * 文件上传
     * @param file 文件
     * @param uploadMkdir 文件存储目录
     * @param request
     * @return
     */
    Result uploadFile(MultipartFile file, String uploadMkdir, HttpServletRequest request);

    /**
     * 文件下载
     * @param request
     * @param response
     * @return
     */
    Result download(String fileName, HttpServletRequest request, HttpServletResponse response);
}