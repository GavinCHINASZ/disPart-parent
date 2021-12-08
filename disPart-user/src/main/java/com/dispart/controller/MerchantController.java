package com.dispart.controller;

import com.dispart.dto.empdto.AddEmpInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.UserMerchantService;
import com.dispart.vo.basevo.BaseMerchantVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "客户二维码管理")
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class MerchantController {
    @Autowired
    UserMerchantService userMerchantService;

    @PostMapping("/DISP20210113")
    @ApiOperation(value = "获取客户二维码下载路径")
    public Result findMeQrUrl(@RequestBody Request<BaseMerchantVo> baseMerchantVoRequest){
        log.info("获取客户二维码下载路径-入参： "+baseMerchantVoRequest.getBody());
        return userMerchantService.findMeQrcodeUrl(baseMerchantVoRequest.getBody());
    }
}