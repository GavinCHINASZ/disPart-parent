package com.dispart.service.impl;

import com.dispart.dao.CustomInfoManagerDao;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.MinIOService;
import com.dispart.util.MinIOUtil;
import com.dispart.vo.entrance.TCustomInfoManager;
import com.dispart.vo.entrance.TVechicleProcurer;
import com.dispart.vo.user.TCustomInfoManagerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author 黄贵川
 * @date 2021-09-27
 */
@Service
@Slf4j
public class MinIOServiceImpl implements MinIOService {
    @Autowired
    private MinIOUtil minioUtil;
    @Value("${fileProps.fileLinkPath}")
    private String fileLinkPath;

    @Resource
    private CustomInfoManagerDao customInfoManagerDao;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 上传文件
     *
     * @param file       上传的文件
     * @param bucketName 存放文件的目录
     * @return Result
     * @author 黄贵川
     * @date 2021-09-27
     */
    @Override
    public Result minIOUpload(MultipartFile file, String bucketName, HttpServletRequest request) {
        try {
            if (file.isEmpty() || file.getSize() == 0) {
                log.info("minIOUpload上传的文件为空");
                return Result.build(201, "文件为空");
            }

            // 检查存储桶是否存在
            bucketName = bucketName.toLowerCase();

            if (!minioUtil.bucketExists(bucketName)) {
                // 创建存储桶
                minioUtil.makeBucket(bucketName);
            }

            // 文件原名称
            String fileName = file.getOriginalFilename();
            // 获取后缀名 并转换成小写  .png
            String suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            // 文件重新命名             多级目录   20210930/图片.image
            fileName = sdf.format(new Date()) + "/" + System.currentTimeMillis() + suffixName;

            InputStream inputStream = file.getInputStream();
            minioUtil.putObject(bucketName, fileName, inputStream, suffixName);
            inputStream.close();

            String objectUrl = minioUtil.getObjectUrl(bucketName, fileName);
            if (StringUtils.isEmpty(objectUrl)) {
                return Result.fail();
            }
            return Result.ok(fileLinkPath + "/" + bucketName + "/" + fileName);
            //return Result.build(200, objectUrl);
        } catch (Exception e) {
            log.error("DISP20210341上传文件失败", e);
            return Result.build(201, "上传文件失败");
        }
    }

    /**
     * 上传文件
     *
     * @param file       上传的文件
     * @param bucketName 存放文件的目录
     * @return Result
     * @author 黄贵川
     * @date 2021-09-27
     */
    @Override
    public Result minIOUpload(MultipartFile file, String bucketName) {
        try {
            if (file.isEmpty() || file.getSize() == 0) {
                log.info("minIOUpload上传的文件为空");
                return Result.build(201, "文件为空");
            }

            // 检查存储桶是否存在
            bucketName = bucketName.toLowerCase();
            if (!minioUtil.bucketExists(bucketName)) {
                // 创建存储桶
                log.info("存储桶对象:" + bucketName);
                minioUtil.makeBucket(bucketName);
            }

            // 文件原名称(本地生成文件名获取)
            String fileName = file.getName();
            // 获取后缀名 并转换成小写
            log.info("上传文件名称 ： " + fileName);
            String suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            // 文件重新命名             多级目录   20210930/图片.image
            fileName = sdf.format(new Date()) + "/" + fileName;

            InputStream inputStream = file.getInputStream();
            minioUtil.putObject(bucketName, fileName, inputStream, suffixName);
            inputStream.close();

            String objectUrl = minioUtil.getObjectUrl(bucketName, fileName);
            if (StringUtils.isEmpty(objectUrl)) {
                return Result.fail();
            }
            return Result.ok(fileLinkPath + "/" + bucketName + "/" + fileName);
            //return Result.build(200, objectUrl);
        } catch (Exception e) {
            log.error("DISP20210341上传文件失败", e);
            return Result.build(201, "上传文件失败");
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件绝对路径
     * @param response HttpServletResponse
     * @author 黄贵川
     * @date 2021-09-27
     */
    @Override
    public Result downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isEmpty(fileName)) {
                log.info("DISP20210106 下载文件URL为空");
                return Result.build(201, "文件URL为空");
            }
            log.info("DISP20210106 MinIO文件下载接口fileName=" + fileName);
            String[] split = fileName.split("/");
            String bucketName = split[split.length - 3];
            String bucketNames = split[split.length - 2];
            String objectName = split[split.length - 1];
            // 获取文件对象
            InputStream object = minioUtil.getObject(bucketName.toLowerCase(), bucketNames + "/" + objectName);
            if (object != null) {
                response.reset();
                response.setHeader("Content-Disposition", "attachment;filename="
                        + URLEncoder.encode(objectName, "UTF-8"));

                // 获取后缀名 并转换成小写  .png
                String suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                boolean imageBoolean = ".webp".equals(suffixName) || ".jpg".equals(suffixName) || ".png".equals(suffixName)
                        || ".bmp".equals(suffixName) || ".jfif".equals(suffixName) || ".jpeg".equals(suffixName)
                        || ".tiff".equals(suffixName) || ".raw".equals(suffixName);
                if (!imageBoolean){
                    response.setContentType("application/octet-stream");
                    response.setCharacterEncoding("UTF-8");
                }

                OutputStream outputStream = response.getOutputStream();

                byte buf[] = new byte[1024];
                int length;
                // 输出文件
                while ((length = object.read(buf)) > 0) {
                    outputStream.write(buf, 0, length);
                }
                // 关闭输出流
                outputStream.close();
            } else {
                return Result.fail();
            }
            return Result.ok();
        } catch (Exception ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = null;
            try {
                ps = response.getOutputStream();
                ps.write(data.getBytes("UTF-8"));
            } catch (IOException e) {
                log.error("文件下载失败", e);
            }
            return Result.fail();
        }
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件绝对路径
     * @return String
     * @author 黄贵川
     * @date 2021-09-27
     */
    @Override
    public Result removeObject(String fileUrl) {
        try {
            if (StringUtils.isEmpty(fileUrl)) {
                log.info("DISP20210343 删除文件URL为空");
                return Result.build(201, "文件URL为空");
            }

            String[] split = fileUrl.split("/");
            String bucketName = split[split.length - 2];
            String objectName = split[split.length - 1];
            boolean removeObjectBoolean = minioUtil.removeObject(bucketName.toLowerCase(), objectName);
            if (removeObjectBoolean) {
                return Result.ok();
            }
            return Result.fail();
        } catch (Exception e) {
            log.error("删除文件异常", e);
            return Result.fail();
        }
    }

    /**
     * 更新客户信息
     *
     * @return Result
     * @author 黄贵川
     * @date 2021-10-08
     */
    @Override
    public Result updateCustomInfoManager(Request<TCustomInfoManagerVo> param) {
        log.info("DISP20210344 minio更新客户信息body=" + param.getBody());
        TCustomInfoManagerVo body = param.getBody();
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }

        List<TCustomInfoManager> customInfoManagerList = customInfoManagerDao.selectBaseMerchantFileList(body);
        if(CollectionUtils.isEmpty(customInfoManagerList)){
            log.info("DISP20210344更新客户信息未查询到相关数据");
            return Result.build("", ResultCodeEnum.DATA_NO_ERROR);
        }

        String[] bucketNameArr = {"idcard", "businessurl"};
        for (int i = 0; i < bucketNameArr.length; i++) {
            if (!minioUtil.bucketExists(bucketNameArr[i])) {
                // 创建存储桶
                minioUtil.makeBucket(bucketNameArr[i]);
            }
        }

        try {
            URL url = null;
            // 后缀名 .png
            String suffixName = "";
            // 身份证 idcard    营业执照   businessurl
            String bucketName = "";
            HttpURLConnection conn = null;
            for (TCustomInfoManager customInfoManager : customInfoManagerList) {
                String objectName = sdf.format(new Date()) + "/" + System.currentTimeMillis() + suffixName;

                String frontUrl = customInfoManager.getFrontUrl();
                if (!StringUtils.isEmpty(frontUrl)) {
                    url = new URL(frontUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置请求方式
                    conn.setRequestMethod("GET");
                    // 超时响应时间5秒
                    conn.setConnectTimeout(5 * 1000);

                    bucketName = "idcard";

                    boolean b = httpConnection(conn, suffixName, bucketName, objectName);
                    if (b) {
                        String objectUrl = minioUtil.getObjectUrl(bucketName, objectName);
                        customInfoManager.setFrontUrl(objectUrl);
                    }else {
                        customInfoManager.setFrontUrl("");
                    }
                }

                String reverserUrl = customInfoManager.getReverserUrl();
                if (!StringUtils.isEmpty(reverserUrl)) {
                    url = new URL(reverserUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置请求方式
                    conn.setRequestMethod("GET");
                    // 超时响应时间5秒
                    conn.setConnectTimeout(5 * 1000);

                    bucketName = "idcard";

                    boolean b = httpConnection(conn, suffixName, bucketName, objectName);
                    if (b) {
                        String objectUrl = minioUtil.getObjectUrl(bucketName, objectName);
                        customInfoManager.setReverserUrl(objectUrl);
                    }else {
                        customInfoManager.setReverserUrl("");
                    }
                }

                String businessUrl = customInfoManager.getBusinessUrl();
                if (!StringUtils.isEmpty(businessUrl)) {
                    url = new URL(businessUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置请求方式
                    conn.setRequestMethod("GET");
                    // 超时响应时间5秒
                    conn.setConnectTimeout(5 * 1000);

                    bucketName = "businessurl";

                    boolean b = httpConnection(conn, suffixName, bucketName, objectName);
                    if (b) {
                        String objectUrl = minioUtil.getObjectUrl(bucketName, objectName);
                        customInfoManager.setBusinessUrl(objectUrl);
                    }else {
                        customInfoManager.setBusinessUrl("");
                    }
                }
            }
        } catch (Exception e) {
            log.error("更新客户信息异常", e);
            return Result.fail();
        }

        int num = customInfoManagerDao.updateCustomInfoManagerList(customInfoManagerList);
        if (num > 0){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 更新 车辆进出管理
     *
     * @author 黄贵川
     * @date 2021-10-09
     * @param param 数据
     * @return Result
     */
    @Override
    public Result updateVechicleProcurer(Request<TVechicleProcurer> param) {
        TVechicleProcurer body = param.getBody();
        log.info("DISP20210348 minio更新 车辆进出管理body=" + body);
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }
        List<TVechicleProcurer> vechicleProcurerList = customInfoManagerDao.selectVechicleProcurerList(body);
        if (CollectionUtils.isEmpty(vechicleProcurerList)){
            log.info("DISP20210348更新车辆进出管理未查询到相关数据");
            return Result.build("", ResultCodeEnum.DATA_NO_ERROR);
        }

        try {
            if (!minioUtil.bucketExists("goodsreporting")){
                // 创建存储桶
                minioUtil.makeBucket("goodsreporting");
            }

            URL url;
            // 后缀名 .png
            String suffixName = "";
            String bucketName = "goodsreporting";
            HttpURLConnection conn;
            for (TVechicleProcurer vechicleProcurer : vechicleProcurerList) {
                String vehicleUrl = vechicleProcurer.getVehicleUrl();
                if (!StringUtils.isEmpty(vehicleUrl)) {
                    url = new URL(vehicleUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置请求方式
                    conn.setRequestMethod("GET");
                    // 超时响应时间5秒
                    conn.setConnectTimeout(5 * 1000);

                    String objectName = sdf.format(new Date()) + "/" + System.currentTimeMillis() + suffixName;
                    boolean b = httpConnection(conn, suffixName, bucketName, objectName);
                    if (b) {
                        String objectUrl = minioUtil.getObjectUrl(bucketName, objectName);
                        vechicleProcurer.setVehicleUrl(objectUrl);
                    }else {
                        vechicleProcurer.setVehicleUrl("");
                    }
                }
            }
        } catch (Exception e) {
            log.error("更新车辆进出管理异常", e);
            return Result.fail();
        }

        int num = customInfoManagerDao.updateVechicleProcurerList(vechicleProcurerList);
        if (num > 0){
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     *
     * @param conn HttpURLConnection
     * @param suffixName 后缀名
     * @param bucketName 桶名
     * @param objectName 文件名
     * @return boolean
     */
    private boolean httpConnection(HttpURLConnection conn, String suffixName, String bucketName, String objectName) {
        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                String imageName = "updateustomInfoManager" + suffixName;
                File imageFile = new File(imageName);
                // 创建输出流
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                byte[] data = readInputStream(inputStream);
                // 写入数据
                outputStream.write(data);
                // 关闭输出流
                outputStream.close();

                FileInputStream fileInputStream = new FileInputStream(imageName);

                minioUtil.putObject(bucketName, objectName, fileInputStream, suffixName);
                inputStream.close();
                return true;
            }
        } catch (IOException e) {
            log.error("httpConnection异常", e);
            return false;
        }
        return false;
    }

    /**
     * @param inputStream InputStream
     * @return byte[]
     */
    private static byte[] readInputStream(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            // 每次读取的长度,如果为-1代表全部读取完毕
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            // 关闭输入流
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 把outputStream里的数据写入内存
        return outputStream.toByteArray();
    }
}
