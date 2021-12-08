package com.dispart.controller;

import com.dispart.dto.producttypedto.AddProductType;
import com.dispart.dto.producttypedto.PrdctTypeKeyDto;
import com.dispart.dto.producttypedto.SelectPrdctTypeDto;
import com.dispart.model.ProductTypeInfo;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ProductTypeInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ProductTypeInfoController
 * @creat 2021/6/10 20:03
 * @Copyright 2020-2021
 */
@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class ProductTypeInfoController {

    @Resource
    private ProductTypeInfoService service;

    @PostMapping("/DISP20210039")
    public Result<Object> findProductTypeInfo(@RequestBody Request<SelectPrdctTypeDto> param){
        return service.findProductTypeInfo(param.getBody().getIsTop());
    }

    @PostMapping("/DISP20210041")
    public Result<Object> deleteProductTypeById(@RequestBody Request<PrdctTypeKeyDto> prdctTypeId){
        return service.deleteByPrimaryKey(prdctTypeId.getBody().getPrdctTypeId());
    }

    @PostMapping("/DISP20210040")
    public Result<Object> updateProductType(@RequestBody Request<ProductTypeInfo> productTypeUpdate){
        return service.updateByPrimaryKey(productTypeUpdate.getBody());
    }

    @PostMapping("/DISP20210042")
    public Result<Object> insertProductType(@RequestBody Request<AddProductType> addProductType) {
        return service.insert(addProductType.getBody());
    }

}
