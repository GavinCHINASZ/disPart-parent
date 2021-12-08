package com.dispart.controller;

import com.alibaba.fastjson.JSON;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.DISP20210604ReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * 贵农云贷业务控制器
 */
@RestController
@RequestMapping("/securityCenter")
@Slf4j
public class LoanController {

    @PostMapping("DISP20210604")
    public Result<Object> addLoansInfo(@RequestBody Request<DISP20210604ReqVo> request) {

        log.debug("贵农云贷，接收到的请求报文:{}", JSON.toJSONString(request));


        return null;
    }


}
