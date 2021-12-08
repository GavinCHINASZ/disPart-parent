package com.dispart.appRequestController;

import com.dispart.dto.imageInfoDto.ImageSelectInVo;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ImagesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@CrossOrigin
public class ImageInfoController {

    @Autowired
    ImagesInfoService service;

    /**
     * app首页广告查询
     * @author  zhaoshihao
     * @date 2021/7/1
    */
    @PostMapping("/DISP20210043")
    public Result DISP20210043Controller(@RequestBody Request<ImageSelectInVo> inVo){
        return service.selectAppHomeImages(inVo.getBody());
    }
}
