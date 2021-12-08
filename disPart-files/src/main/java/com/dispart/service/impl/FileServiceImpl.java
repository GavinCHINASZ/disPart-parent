package com.dispart.service.impl;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author:xts
 * @date:Created in 2021/6/18 18:05
 * @description 文件处理服务
 * @modified by:
 * @version: 1.0
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${fileProps.downUploadPath}")
    private String downUploadPath;
    @Value("${fileProps.fileUploadPath}")
    private String fileUploadPath;
    @Value("${fileProps.fileLinkPath}")
    private String fileLinkPath;

    /**
     * 统一文件上传
     *
     * @param file        文件
     * @param uploadMkdir 文件存储目录
     * @param request
     * @return
     */
    public Result uploadFile(MultipartFile file, String uploadMkdir, HttpServletRequest request) {
        log.info("上传文件开始......");
        if (file == null || StringUtils.isBlank(uploadMkdir)) {
            log.info("文件服务-统一上传文件-" + ResultCodeEnum.PARAM_NULL.getMessage());
            return Result.build(ResultCodeEnum.PARAM_NULL.getCode(), ResultCodeEnum.PARAM_NULL.getMessage());
        }
        //文件原名称
        String fileName = file.getOriginalFilename();
        //获取后缀
        String suffixname = fileName.substring(fileName.lastIndexOf("."));
        //文件重新命名
        fileName = System.currentTimeMillis() + suffixname;
        File dest = new File(fileUploadPath + uploadMkdir, fileName);
        if (!dest.exists()) dest.mkdirs();
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            log.info("开放区-文件上传服务-" + ResultCodeEnum.FILE_UPLOAD_FAIL.getMessage());
            e.printStackTrace();
            return Result.build(ResultCodeEnum.FILE_UPLOAD_FAIL.getCode(), ResultCodeEnum.FILE_UPLOAD_FAIL.getMessage());
        }
        log.info("上传文件成功.......");
        return Result.ok(fileLinkPath + "/" + uploadMkdir + "/" + fileName);
    }

    @Override
    public Result download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        if (fileName == null) {
            log.info("文件服务-文件统一下载-" + ResultCodeEnum.PARAM_NULL.getMessage());
            return Result.build(ResultCodeEnum.PARAM_NULL.getCode(), ResultCodeEnum.PARAM_NULL.getMessage());
        }
        log.info("文件名称，{}", fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bi = null;
        OutputStream outputStream = null;
        File file = new File(downUploadPath + fileName);
        //File file = new File( fileName);
        if (!file.exists()) {
            log.info("文件资源不存在");
            return null;
        }
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            outputStream = response.getOutputStream();
            bi = new BufferedInputStream(new FileInputStream(file));
            int read = bi.read(buff);
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bi.read(buff);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException("下载失败");
        } finally {
            if (bi != null) {
                try {
                    bi.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        log.info("文件下载完成");
        return null;
    }
}