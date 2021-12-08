package com.dispart.controller;

import com.dispart.dto.imageInfoDto.ImageIdInVo;
import com.dispart.dto.imageInfoDto.ImageSelectInVo;
import com.dispart.model.ImagesInfo;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ImagesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ImagesInfoController
 * @creat 2021/6/11 19:50
 * @Copyright 2020-2021
 */

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class ImagesInfoController {

    @Autowired
    private ImagesInfoService service;

    @PostMapping("/DISP20210048")
    public Result addImagesInfo(@RequestBody Request<ImagesInfo> imagesInfo) {
        return service.insert(imagesInfo.getBody());
    }

    @PostMapping("/DISP20210049")
    public Result selectImagesInfo(@RequestBody Request<ImageSelectInVo> inVoRequest){
        return service.select(inVoRequest.getBody());
    }

    @PostMapping("/DISP20210050")
    public Result updateImagesInfo(@RequestBody Request<ImagesInfo> imagesInfo){
        return service.updateByPrimaryKey(imagesInfo.getBody());
    }

    @PostMapping("/DISP20210051")
    public Result deleteImagesInfo(@RequestBody Request<ImageIdInVo> inVoRequest){
        return service.deleteByPrimaryKey(inVoRequest.getBody().getImageId());
    }
}
