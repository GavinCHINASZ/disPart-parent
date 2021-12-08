package com.dispart.controller;

import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dto.dataquery.Disp20210069InDto;
import com.dispart.dto.dataquery.Disp20210069OutDto;
import com.dispart.dto.dataquery.Disp20210335InDto;
import com.dispart.dto.dataquery.Disp20210335OutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.OrderInfoQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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
public class OrderQueryController {

  @Resource
  private OrderInfoQueryService orderInfoQueryService;

  @PostMapping("/securityCenter/DISP20210069")
  @ApiOperation(value = "订单查询")
  public  Result<Disp20210069OutDto> quryProductInventory(@RequestBody Request<Disp20210069InDto> param){
    return orderInfoQueryService.quryOrderInfo(param.getBody());
  }

  @PostMapping("/securityCenter/DISP20210335")
  @ApiOperation(value = "订单商品查询")
  public  Result<BaseSelectionOutDto> getOrderGoods(@RequestBody Request<Disp20210335InDto> param){
    return orderInfoQueryService.getOrderGoods(param.getBody());
  }

  @PostMapping("/securityCenter/DISP20210352")
  @ApiOperation(value = "订单导出")
  public Result<String> exportOrderInfo(@RequestBody Request<Disp20210069InDto> param){
    return orderInfoQueryService.exportData(param.getBody());
  }

}
