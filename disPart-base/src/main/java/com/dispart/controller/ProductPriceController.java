package com.dispart.controller;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ProductPriceInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class ProductPriceController {

    @Resource
    private ProductPriceInfoService service;

    @PostMapping("DISP20210047")
    public Result pushProductPriceInfo(@RequestParam("file") MultipartFile file){
        return service.addProductInfo(file);
    }
}
