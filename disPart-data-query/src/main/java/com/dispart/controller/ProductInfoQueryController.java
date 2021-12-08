package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210075InDto;
import com.dispart.dto.dataquery.Disp20210075OutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ProductInventoryInfoQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title QuryEmpController
 * @Description 商品信息查询
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "")
@Slf4j
public class ProductInfoQueryController {

  @Autowired
  private ProductInventoryInfoQueryService productInventoryInfoQueryService;

  @PostMapping("/securityCenter/DISP20210075")
  @ApiOperation(value = "货品库存信息查询")
  public  Result<Disp20210075OutDto> quryProductInventory(@RequestBody Request<Disp20210075InDto> param){
    return productInventoryInfoQueryService.quryProductInventory(param.getBody());
  }


}
