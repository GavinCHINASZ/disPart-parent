package com.dispart.service;

import com.dispart.dto.imageInfoDto.ImageSelectInVo;
import com.dispart.model.ImagesInfo;
import com.dispart.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ImagesInfoService
 * @creat 2021/6/11 19:50
 * @Copyright 2020-2021
 */
public interface ImagesInfoService{


    Result deleteByPrimaryKey(String imageId);

    Result insert(ImagesInfo record);

    Result select(ImageSelectInVo inVo);

    Result updateByPrimaryKey(ImagesInfo record);

    Result selectAppHomeImages(ImageSelectInVo inVo);

}
