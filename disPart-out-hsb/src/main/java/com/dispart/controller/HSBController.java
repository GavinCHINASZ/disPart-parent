package com.dispart.controller;

import com.dispart.service.impl.DISP20210094ServiceImpl;
import com.dispart.service.impl.DISP20210095ServiceImpl;
import com.dispart.service.impl.DISP20210097ServiceImpl;
import com.dispart.service.impl.DISP20210293ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/outHsb/securityCenter")
public class HSBController {

    @Autowired
    DISP20210094ServiceImpl disp20210094Service;

    @Autowired
    DISP20210095ServiceImpl disp20210095Service;

    @Autowired
    DISP20210097ServiceImpl disp20210097Service;

    @Autowired
    DISP20210293ServiceImpl disp20210293Service;

    /**
     * 惠市宝签约客户信息变更
     * @param reqJson 接收到的惠市宝的请求报文
     * @return 给惠市宝的响应报文
     */
    @RequestMapping("/DISP20210094")
    public String notifyCustomerInfo(@RequestBody String reqJson) {
        return disp20210094Service.service(reqJson);
    }

    /**
     * 惠市宝支付结果通知
     * @param reqJson 接收到惠市宝的请求报文
     * @return 给惠市宝的响应报文
     */
    @RequestMapping("/DISP20210097")
    public String notifyPayResult(@RequestBody String reqJson) {
        return disp20210097Service.service(reqJson);
    }

    /**
     * 惠市宝推送对账文件
     */

    @RequestMapping("/DISP20210095")
    public void receiveUploadFile(HttpServletRequest request) {
        disp20210095Service.service(request);
    }

    /**
     * 订单退款结果通知
     */
    @RequestMapping("/DISP20210293")
    public String notifyRefundResult(@RequestBody String reqJson) {
        return disp20210293Service.service(reqJson);
    }

}
