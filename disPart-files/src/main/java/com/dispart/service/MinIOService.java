package com.dispart.service;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.entrance.TVechicleProcurer;
import com.dispart.vo.user.TCustomInfoManagerVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author 黄贵川
 * @date 2021-09-27
 */
public interface MinIOService {
    /**
     * 上传文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param file 上传的文件
     * @return Result
     */
    Result minIOUpload(MultipartFile file, String uploadMkdir, HttpServletRequest request);
    /**
     * 上传文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param file 上传的文件
     * @return Result
     */
    Result minIOUpload(MultipartFile file, String uploadMkdir);

    /**
     * 下载文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param fileName  文件绝对路径
     * @param response HttpServletResponse
     */
    Result downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除文件
     *
     * @author 黄贵川
     * @date 2021-09-27
     * @param fileUrl  文件绝对路径
     * @return String
     */
    Result removeObject(String fileUrl);

    /**
     * 更新客户信息
     *
     * @author 黄贵川
     * @date 2021-10-08
     * @return Result
     */
    Result updateCustomInfoManager(Request<TCustomInfoManagerVo> param);

    /**
     * 更新 车辆进出管理
     *
     * @author 黄贵川
     * @date 2021-10-09
     * @param param 数据
     * @return Result
     */
    Result updateVechicleProcurer(Request<TVechicleProcurer> param);
}
