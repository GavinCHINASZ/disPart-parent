package com.dispart.controller;

import com.dispart.result.Result;
import com.dispart.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:xts
 * @date:Created in 2021/6/18 18:00
 * @description 文件处理接口
 */
@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    /*@PostMapping("/securityCenter/DISP20210105")
    @ApiOperation(value = "统一文件上传接口")*/
    //@CrossOrigin
    public Result uploadFile(MultipartFile file, String uploadMkdir, HttpServletRequest request) {
        return fileService.uploadFile(file, uploadMkdir, request);
    }

    /*@RequestMapping(value = "/securityCenter/DISP20210106", method = RequestMethod.GET)
    @ApiOperation(value = "统一文件下载接口")*/
    public Result download(String fileName, HttpServletRequest request, HttpServletResponse response) {
        return fileService.download(fileName, request, response);
    }
}
