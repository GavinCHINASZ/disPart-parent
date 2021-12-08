package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractHSBService;
import com.dispart.vo.DISP20210293InVo;
import com.dispart.vo.DISP20210293OutVo;
import com.dispart.vo.DISP20210293ReqVo;
import com.dispart.vo.DISP20210293RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 订单退款结果通知
 */
@Service
public class DISP20210293ServiceImpl extends AbstractHSBService {

    private final Logger logger = LoggerFactory.getLogger(DISP20210293ServiceImpl.class);

    @Value("${service.url.DISP20210264}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210293";
    }

    /**
     * 获取物流园的请求地址
     *
     * @return 请求的URL
     */
    @Override
    protected String getURL() {
        logger.debug("获取退款结果通知的URL地址:{}", url);

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

        DISP20210293ReqVo reqVo = JSONObject.parseObject(reqJson, DISP20210293ReqVo.class);

        DISP20210293InVo inVo = new DISP20210293InVo();
        BeanUtils.copyProperties(reqVo, inVo);

        String json = JSON.toJSONString(inVo);

        logger.debug("惠市宝退款结果通知,组装到业务的请求报文:{}", json);

        return json;
    }

    /**
     * 组装惠市宝响应报文
     *
     * @param respJson 物流园的响应报文
     * @return 转换后的惠市宝的响应报文
     */
    @Override
    protected JSONObject parseRespJson(String respJson) {

        DISP20210293OutVo outVo = JSONObject.parseObject(respJson, DISP20210293OutVo.class);

        DISP20210293RespVo respVo = new DISP20210293RespVo();
        BeanUtils.copyProperties(outVo, respVo);

        return ((JSONObject) JSON.toJSON(respVo));
    }
}
