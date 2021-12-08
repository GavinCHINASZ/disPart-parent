package com.dispart.controller;

import com.dispart.dto.empdto.QuryEmpInDto;
import com.dispart.dto.empdto.QuryEmpOutDto;
import com.dispart.dto.userdto.UserRegAcctInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.QuryEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhongfei
 * @version 1.0.0:
 * @title QuryEmpController
 * @Description TODO
 * @dateTime 2021/6/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@CrossOrigin
@Api(tags = "用户查询")
//@RequestMapping("/securityCenter")
@Slf4j
public class QuryEmployeeController {

  @Autowired
  private QuryEmployeeService service;

  @PostMapping("/securityCenter/DISP20210011")
  @ApiOperation(value = "查询用户信息")
    public Result<QuryEmpOutDto> quryEmployee(@RequestBody Request<QuryEmpInDto>  param){
      return service.quryEmployeeInfo(param.getBody());
    }
}
