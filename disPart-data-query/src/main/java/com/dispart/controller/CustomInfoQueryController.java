package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210073InDto;
import com.dispart.dto.dataquery.Disp20210073OutDto;
import com.dispart.dto.dataquery.Disp20210074InDto;
import com.dispart.dto.dataquery.Disp20210074OutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CustomInfoQueryService;
import com.dispart.service.CustomRegistInfoQueryService;
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
 * @Description 客户信息查询
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "")
@Slf4j
public class CustomInfoQueryController {

  @Autowired
  private CustomRegistInfoQueryService customRegistInfoQueryService;

  @PostMapping("/securityCenter/DISP20210073")
  @ApiOperation(value = "客户注册信息查询")
  public  Result<Disp20210073OutDto> quryCustomRegistInfo(@RequestBody Request<Disp20210073InDto> param){
    return customRegistInfoQueryService.quryCustomRegistInfo(param.getBody());
  }


  @Autowired
  private CustomInfoQueryService customInfoQueryService;

  @PostMapping("/securityCenter/DISP20210074")
  @ApiOperation(value = "客户信息查询")
  public  Result<Disp20210074OutDto> quryCustomInfo(@RequestBody Request<Disp20210074InDto> param){
    return customInfoQueryService.quryCustomInfo(param.getBody());
  }



}
