package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractHSBService;
import com.dispart.vo.DISP20210077ReqVo;
import com.dispart.vo.DISP20210077RespVo;
import com.dispart.vo.DISP20210094ReqVo;
import com.dispart.vo.DISP20210097RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 惠市宝签约客户信息变更回调服务
 */
@Service
public class DISP20210094ServiceImpl extends AbstractHSBService {

    private final Logger logger = LoggerFactory.getLogger(DISP20210094ServiceImpl.class);

    @Value("${service.url.DISP20210077}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210094";
    }

    /**
     * 获取物流园的请求地址
     *
     * @return 请求的URL
     */
    @Override
    protected String getURL() {
        logger.debug("获取签约客户信息变更通知的URL地址:{}", url);

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

        DISP20210094ReqVo reqVo = JSON.parseObject(reqJson, DISP20210094ReqVo.class);
        DISP20210077ReqVo reqVo1 = new DISP20210077ReqVo();

        BeanUtils.copyProperties(reqVo, reqVo1);
        String req = JSON.toJSONString(reqVo1);

        logger.debug("惠市宝签约客户信息变更通知,组装到业务的请求报文:{}", req);

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

        DISP20210077RespVo disp20210077RespVo = JSON.parseObject(respJson, DISP20210077RespVo.class);

        DISP20210097RespVo respVo = new DISP20210097RespVo();
        BeanUtils.copyProperties(disp20210077RespVo, respVo);

        return ((JSONObject) JSON.toJSON(respVo));
    }
}
