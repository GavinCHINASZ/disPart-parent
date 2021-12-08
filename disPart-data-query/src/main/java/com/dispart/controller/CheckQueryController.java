package com.dispart.controller;

import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dto.dataquery.*;
import com.dispart.model.TReconciliationResult;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CheckDetailQueryService;
import com.dispart.service.CheckResultQueryService;
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
 * @Description 对账查询
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "")
@Slf4j
public class CheckQueryController {

  @Autowired
  private CheckDetailQueryService checkDetailQueryService;

  @Autowired
  private CheckResultQueryService checkResultQueryService;

  @PostMapping("/securityCenter/DISP20210071")
  @ApiOperation(value = "对账明细查询")
  public  Result<Disp20210071OutDto> quryCheckDetail(@RequestBody Request<Disp20210071InDto> param){
    return checkDetailQueryService.quryCheckDetail(param.getBody());
  }

  @PostMapping("/securityCenter/DISP20210363")
  @ApiOperation(value = "对账明细导出")
  public Result exportCheckDetail(@RequestBody Request<Disp20210071InDto> param){
    return checkDetailQueryService.exportCheckDetail(param.getBody());
  }

  @PostMapping("/securityCenter/DISP20210072")
  @ApiOperation(value = "对账结果明细查询")
  public  Result<Disp20210072OutDto> quryCheckResult(@RequestBody Request<Disp20210072InDto> param){
    return checkResultQueryService.quryCheckResult(param.getBody());
  }

  @PostMapping("/securityCenter/DISP20210334")
  @ApiOperation(value = "对账结果查询")
  public  Result<BaseSelectionOutDto> quryMainCheckResult(@RequestBody Request<Disp20210334InDto> param){
    return checkResultQueryService.quryMainCheckResult(param.getBody());
  }

  @PostMapping("/securityCenter/DISP20210528")
  @ApiOperation(value = "对账结果导出")
  public  Result<BaseSelectionOutDto> exportMainCheckResult(@RequestBody Request<Disp20210334InDto> param){
    return checkResultQueryService.exportMainCheckResult(param.getBody());
  }

}
