package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210066InDto;
import com.dispart.dto.dataquery.Disp20210066OutDto;
import com.dispart.dto.dataquery.Disp20210067InDto;
import com.dispart.dto.dataquery.Disp20210067OutDto;
import com.dispart.dto.dataquery.Disp20210068InDto;
import com.dispart.dto.dataquery.Disp20210068OutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.AmountQuerySettlementService;
import com.dispart.service.AmountQueryTransactionService;
import com.dispart.service.AmountQueryUnSettlementService;
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
 * @Description 金额查询
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "")
@Slf4j
public class AmountQueryController {

  @Autowired
  private AmountQueryTransactionService amountQueryTransactionService;

  @PostMapping("/securityCenter/DISP20210066")
  @ApiOperation(value = "交易金额查询")
  public  Result<Disp20210066OutDto> quryTransactionAmount(@RequestBody Request<Disp20210066InDto> param){
    return amountQueryTransactionService.quryTransactionAmount(param.getBody());
  }


  @Autowired
  private AmountQuerySettlementService amountQuerySettlementService;

  @PostMapping("/securityCenter/DISP20210067")
  @ApiOperation(value = "结算金额查询")
  public  Result<Disp20210067OutDto> qurySettlementAmount(@RequestBody Request<Disp20210067InDto> param){
    return amountQuerySettlementService.qurySettlementAmount(param.getBody());
  }


  @Autowired
  private AmountQueryUnSettlementService amountQueryUnSettlementService;

  @PostMapping("/securityCenter/DISP20210068")
  @ApiOperation(value = "待结算金额查询")
  public  Result<Disp20210068OutDto> quryUnSettlementAmount(@RequestBody Request<Disp20210068InDto> param){
    return amountQueryUnSettlementService.quryUnSettlementAmount(param.getBody());
  }

}
