package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractHSBService;
import com.dispart.vo.DISP20210065ReqVo;
import com.dispart.vo.DISP20210065RespVo;
import com.dispart.vo.DISP20210097ReqVo;
import com.dispart.vo.DISP20210097RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 惠市宝支付结果回调服务
 */
@Service
public class DISP20210097ServiceImpl extends AbstractHSBService {

    private final Logger logger = LoggerFactory.getLogger(DISP20210097ServiceImpl.class);

    @Value("${service.url.DISP20210065}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210097";
    }

    /**
     * 获取物流园的请求地址
     *
     * @return 请求的URL
     */
    @Override
    protected String getURL() {
       logger.debug("获取支付结果通知的请求地址url:{}", url);

        return url;
    }

    /**
     * 组装到物流园的请求报文
     *
     * @param reqJson 接受到的惠市宝的请求报文
     * @return 组装好的物流园请求报文
     */
    @Override
    protected String buildReqJson(String reqJson) {


        DISP20210097ReqVo disp20210097ReqVo = JSON.parseObject(reqJson, DISP20210097ReqVo.class);
        DISP20210065ReqVo reqVo = new DISP20210065ReqVo();
        BeanUtils.copyProperties(disp20210097ReqVo, reqVo);
        if (reqVo.getOrderSt().equals("4")) {  //通知交易中失效状态码根查询不一致,做一次转换
            logger.debug("订单状态码转换");
            reqVo.setOrderSt("6");
        }
        String req = JSON.toJSONString(reqVo);

        logger.debug("惠市宝支付结果通知, 组装的请求报文:{}", req);

        return req;
    }

    /**
     * 组装惠市宝响应报文
     *
     * @param respJson 物流园的响应报文
     * @return 转换后的惠市宝的响应报文
     */
    @Override
    protected JSONObject parseRespJson(String respJson) {

        DISP20210065RespVo disp20210065RespVo = JSON.parseObject(respJson, DISP20210065RespVo.class);

        DISP20210097RespVo respVo = new DISP20210097RespVo();
        BeanUtils.copyProperties(disp20210065RespVo, respVo);

        return ((JSONObject) JSON.toJSON(respVo));
    }
}
