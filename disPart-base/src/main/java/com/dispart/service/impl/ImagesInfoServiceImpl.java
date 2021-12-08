package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dto.imageInfoDto.ImageSelectInVo;
import com.dispart.dto.imageInfoDto.ImageSelectOutVo;
import com.dispart.result.Result;
import com.dispart.enums.ImageInfoResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.dispart.model.ImagesInfo;
import com.dispart.dao.mapper.ImagesInfoMapper;
import com.dispart.service.ImagesInfoService;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ImagesInfoServiceImpl
 * @creat 2021/6/11 19:50
 * @Copyright 2020-2021
 */
@Service
@Slf4j
public class ImagesInfoServiceImpl implements ImagesInfoService {

    @Resource
    private ImagesInfoMapper imagesInfoMapper;

    @Override
    public Result deleteByPrimaryKey(String imageId) {
        log.info("删除广告信息，开始执行，传入参数: {}",imageId);
        if (org.springframework.util.StringUtils.isEmpty(imageId)) {
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), "广告图片ID不能为空");
        }
        int i;
        try {
            i = imagesInfoMapper.deleteByPrimaryKey(imageId);
        } catch (Exception e) {
            log.error("数据库删除异常",e);
            throw new RuntimeException("数据库删除异常");
        }
        if (i < 1) {
            return Result.build(ImageInfoResultCodeEnum.DELETE_NULL.getCode(), ImageInfoResultCodeEnum.DELETE_NULL.getMessage());
        }
        log.info("删除广告信息，执行结束");
        return Result.ok();
    }

    @Override
    public Result select(ImageSelectInVo inVo) {
        log.info("查询所有图片信息，传入参数：{}", JSON.toJSONString(inVo));
        //分页参数处理
        if (inVo.getPageNum() != null && inVo.getPageSize() != null) {
            inVo.setPageNum((inVo.getPageNum() - 1) * inVo.getPageSize());
        }
        List<ImagesInfo> allImages;
        Integer tolPageSize;
        try {
            allImages = imagesInfoMapper.selectImagesInfo(inVo);
            tolPageSize = imagesInfoMapper.selectImagesInfoCount(inVo);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }
        ImageSelectOutVo outVo = new ImageSelectOutVo();
        outVo.setTolPageNum(tolPageSize);
        outVo.setImagesList(allImages);
        log.info("广告信息查询结束,返回:{}",JSON.toJSONString(outVo));
        return Result.ok(outVo);
    }

    @Override
    public Result updateByPrimaryKey(ImagesInfo record) {
        log.info("更新广告信息，开始执行，传入参数：{}",JSON.toJSONString(record));
        if (StringUtils.isEmpty(record.getTitle())) {
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), "图片标题不能为空");
        }
        if (StringUtils.isEmpty(record.getImageUrl())) {
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), "图片URL不能为空");
        }

        if (StringUtils.isEmpty(record.getImageId())) {
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), "广告图片ID不能为空");
        }
        if(!StringUtils.isEmpty(record.getPriority())){
            if(Integer.valueOf(record.getPriority()) < 1 || Integer.valueOf(record.getPriority()) > 9){
                return Result.build(ImageInfoResultCodeEnum.INVALID_PARM.getCode(),"优先级参数不合法,请输入1-9");
            }
        }
        record.setUpdateDt(new Date());
        int i;
        try {
            i = imagesInfoMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (i < 1) {
            return Result.build(ImageInfoResultCodeEnum.UPDATE_NULL.getCode(), ImageInfoResultCodeEnum.UPDATE_NULL.getMessage());
        }
        log.info("更新广告信息，执行成功");
        return Result.ok();
    }

    @Override
    public Result selectAppHomeImages(ImageSelectInVo inVo) {
        log.info("开始查询App首页广告");
        List<ImagesInfo> imagesInfoList;
        try{
            imagesInfoList = imagesInfoMapper.selectImagesInfo(inVo);
        }catch (Exception e){
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        log.info("查询App首页广告成功，返回数据：{}",JSON.toJSONString(imagesInfoList));
        return Result.ok(imagesInfoList);
    }

    /**
     * 执行数据插入
     *
     * @author zhaoshihao
     * @date 2021/6/17
     */
    @Override
    public Result insert(ImagesInfo imagesInfo) {
        log.info("新增广告信息开始执行，传入参数：{}",JSON.toJSONString(imagesInfo));
        if (StringUtils.isEmpty(imagesInfo.getTitle())) {
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), "图片标题不能为空");
        }
        if (StringUtils.isEmpty(imagesInfo.getImageUrl())) {
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), "图片URL不能为空");
        }
        if(StringUtils.isEmpty(imagesInfo.getBelong())){
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(),"图片归属不能为空");
        }
        if(!StringUtils.isEmpty(imagesInfo.getPriority())){
            if(Integer.valueOf(imagesInfo.getPriority()) < 1 || Integer.valueOf(imagesInfo.getPriority()) > 9){
                return Result.build(ImageInfoResultCodeEnum.INVALID_PARM.getCode(),"优先级参数不合法,请输入1-9");
            }
        }
        imagesInfo.setUpdateDt(new Date());
        String imageId;
        try {
            imageId = String.format("%07d", imagesInfoMapper.getImageId("imageId"));
        } catch (Exception e) {
            log.error("获取imageId失败，请检查序列表是否含该记录",e);
            throw new RuntimeException("系统错误，生成主键失败");
        }
        imagesInfo.setImageId(imageId);
        int i;
        try {
            i = imagesInfoMapper.insert(imagesInfo);
        } catch (Exception e) {
            log.error("数据库插入异常", e);
            throw new RuntimeException("系统错误，数据库插入异常");
        }
        if (i < 1) {
            log.error("插入数据失败");
            return Result.build(ImageInfoResultCodeEnum.INSERT_FAIL.getCode(), ImageInfoResultCodeEnum.INSERT_FAIL.getMessage());
        }
        log.info("新增广告信息执行成功");
        return Result.ok();
    }
}
