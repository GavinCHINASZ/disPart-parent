package com.dispart.config;


import com.dispart.dao.BaseMerchantMapper;
import com.dispart.result.Result;
import com.dispart.service.MinIOService;
import com.dispart.util.MultipartFileDto;
import com.dispart.vo.basevo.BaseMerchantVo;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/19 3:53
 * @description 客户信息自动任务
 * @modified by:
 * @version: 1.0
 */
@Component
@Slf4j
@RefreshScope
public class QrCodeFileTask {

    @Value("${qrCode.width}")
    private String width;
    @Value("${qrCode.height}")
    private String height;
    @Value("${qrCode.format}")
    private String format;
    @Value("${qrCode.url}")
    private String url;
    @Value("${qrCode.logPath}")
    private String logPath;
    @Value("${qrCode.savaPath}")
    private String savaPath;
    @Value("${qrCode.reqUrl}")
    private String reqUrl;

    @Autowired
    private BaseMerchantMapper baseMerchantMapper;

    @Autowired
    private MinIOService minIOService;

    //上一次执行开始时间点之后30分钟在执行
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void createQRCode() {
        //扫描商户数据
        List<BaseMerchantVo> merchantVos = baseMerchantMapper.findAll();
        log.info("维护客户二维码-开始 :" + merchantVos);
        log.info("维护客户二维码-格式 :" + format);
        log.info("维护客户二维码-内容 :" + url);
        if (merchantVos.size() > 0) {
            //满足条件进行生成二维码
            HashMap hashMap = new HashMap();
            hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hashMap.put(EncodeHintType.MARGIN, 2);

            for (BaseMerchantVo baseMerchantVo : merchantVos) {
                String meUrl = reqUrl + baseMerchantVo.getMerchantcode() + "." + format; //二维码请求URL
                String saveUrl = savaPath + baseMerchantVo.getMerchantcode() + "." + format;//二维码存放URL
                String qrWul = url + baseMerchantVo.getMerchantcode() + "&busineId=" + baseMerchantVo.getMerchantcode();//二维码内容
                log.info("维护客户二维码-内容 :" + qrWul);
                baseMerchantVo.setQrcodeUrl(meUrl);
                File file = new File(saveUrl);
                try {
                    BitMatrix bitMatrix = new MultiFormatWriter().encode(qrWul, BarcodeFormat.QR_CODE, Integer.parseInt(width), Integer.parseInt(height), hashMap);
                    //导出到指定目录

                    try {

                        MatrixToImageWriter.writeToPath(bitMatrix, format, file.toPath());
                        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
                        BufferedImage matrixImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
                        //添加log
                        File logoFile = new File(logPath);
                        /**
                         * 读取二维码图片，并构建绘图对象
                         */
                        Graphics2D g2 = matrixImage.createGraphics();
                        int matrixWidth = matrixImage.getWidth();
                        int matrixHeigh = matrixImage.getHeight();
                        /**
                         * 读取Logo图片
                         */
                        BufferedImage logo = ImageIO.read(logoFile);
                        //开始绘制图片
                        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
                        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                        g2.setStroke(stroke);// 设置笔画对象
                        //指定弧度的圆角矩形
                        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
                        g2.setColor(Color.white);
                        g2.draw(round);// 绘制圆弧矩形
                        //设置logo 有一道灰色边框
                        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                        g2.setStroke(stroke2);// 设置笔画对象
                        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
                        g2.setColor(new Color(128, 128, 128));
                        g2.draw(round2);// 绘制圆弧矩形
                        g2.dispose();
                        matrixImage.flush();
                        ImageIO.write(matrixImage, format, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                //上传图片
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    MultipartFile upFile = new MultipartFileDto(baseMerchantVo.getMerchantcode() + "." + format,fileInputStream);

                    log.info("维护客户二维码-开始上传图片 ： ");
                    Result result = minIOService.minIOUpload(upFile, "qrcode");
                    log.info("上传路径 ： "+result);
                    String upUrl = result.getData().toString();
                    baseMerchantVo.setQrcodeUrl(upUrl);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //登记图片
                try {
                    if (baseMerchantMapper.updateUrlByKey(baseMerchantVo) > 0) {
                        log.info("维护客户二维码-成功，客户： " + baseMerchantVo.getMerchantcode() + " 二维码路径： " + baseMerchantVo.getQrcodeUrl());
                    } else {
                        log.error("维护客户二维码-失败，客户： " + baseMerchantVo.getMerchantcode() + " 二维码路径： " + baseMerchantVo.getQrcodeUrl());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("维护客户二维码-失败，客户： " + baseMerchantVo.getMerchantcode() + " 二维码路径： " + baseMerchantVo.getQrcodeUrl());
                }
            }


        }


    }
}