package com.dispart.controller;

import com.dispart.dto.billDto.BillInsertInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.MinIOService;
import com.dispart.vo.entrance.TVechicleProcurer;
import com.dispart.vo.user.TCustomInfoManagerVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * minio文件服务
 *
 * @author 黄贵川
 * @date 2021-09-27
 */
@RestController
@RequestMapping("/securityCenter")
public class MinIOController {
    @Autowired
    private MinIOService minioService;

    /**
     * 上传文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param file 上传的文件
     * @param uploadMkdir 存放的目录名称
     * @param request HttpServletRequest
     * @return Result
     */
    @PostMapping("/DISP20210105")
    @ApiOperation(value = "MinIO文件上传接口")
    public Result MinIOUpload(MultipartFile file, String uploadMkdir, HttpServletRequest request) {
        return minioService.minIOUpload(file, uploadMkdir, request);
    }

    /**
     * 下载文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param fileName  文件绝对路径
     * @param response HttpServletResponse
     */
    @GetMapping("/DISP20210106")
    @ApiOperation(value = "MinIO文件下载接口")
    public Result downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) {
        return minioService.downloadFile(fileName, request, response);
    }

    /**
     * 删除文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param fileUrl  文件绝对路径
     * @return String
     */
    @RequestMapping("/DISP20210343")
    public Result removeObject(String fileUrl) {
        return minioService.removeObject(fileUrl);
    }

    /**
     * 更新客户信息
     *
     * @author 黄贵川
     * @date 2021-10-08
     * @param param 数据
     * @return Result
     */
    @RequestMapping("/DISP20210344")
    public Result updateCustomInfoManager(@RequestBody Request<TCustomInfoManagerVo> param) {
        return minioService.updateCustomInfoManager(param);
    }

    /**
     * 更新 车辆进出管理
     *
     * @author 黄贵川
     * @date 2021-10-09
     * @param param 数据
     * @return Result
     */
    @RequestMapping("/DISP20210348")
    public Result updateVechicleProcurer(@RequestBody Request<TVechicleProcurer> param) {
        return minioService.updateVechicleProcurer(param);
    }
}
