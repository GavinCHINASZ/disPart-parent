package com.dispart.controller;

import com.dispart.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outHsb/securityCenter")
public class LogisticsParkController {

    @Autowired
    DISP20210093ServiceImpl disp20210079Service;

    @Autowired
    DISP20210096ServiceImpl disp20210082Service;

    @Autowired
    DISP20210098ServiceImpl disp20210084Service;

    @Autowired
    DISP20210292ServiceImpl disp20210292Service;

    @Autowired
    DISP20210294ServiceImpl disp20210294Service;

    /**
     * 商户签约信息查询
     */
    @PostMapping("/DISP20210093")
    public String queryMerchantInfo(@RequestBody String reqJson) {

        return disp20210079Service.service(reqJson);
    }

    /**
     * 生成支付订单
     */
    @PostMapping("/DISP20210096")
    public String buildPayOrder(@RequestBody String reqJson) {

        return disp20210082Service.service(reqJson);
    }

    /**
     * 支付结果查询
     */
    @PostMapping("/DISP20210098")
    public String queryPayResultInfo(@RequestBody String reqJson) {

        return disp20210084Service.service(reqJson);
    }

    /**
     * 订单退款
     */
    @PostMapping("/DISP20210292")
    public String refund(@RequestBody String reqJson) {

        return disp20210292Service.service(reqJson);
    }

    /**
     * 订单退款结果查询
     */
    @PostMapping("/DISP20210294")
    public String refundResultQuery(@RequestBody String reqJson) {

        return disp20210294Service.service(reqJson);
    }
}
