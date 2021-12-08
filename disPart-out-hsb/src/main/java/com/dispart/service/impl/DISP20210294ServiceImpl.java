package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractOutHSBService;
import com.dispart.vo.DISP20210294InVo;
import com.dispart.vo.DISP20210294OutVo;
import com.dispart.vo.DISP20210294ReqVo;
import com.dispart.vo.DISP20210294RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 订单退款结果查询
 */
@Service
public class DISP20210294ServiceImpl extends AbstractOutHSBService {

    private final Logger logger = LoggerFactory.getLogger(DISP20210294ServiceImpl.class);

    @Value("${service.url.DISP20210294}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210294";
    }

    /**
     * 获取服务方的URL
     *
     * @return 服务方的URL
     */
    @Override
    public String getURL() {
        logger.debug("获取退款结果查询服务的URL地址:" + url);

        return url;
    }

    /**
     * 解析收到的物流园请求报文,并把报文转换成惠市宝需要的报文
     *
     * @param reqJson 物流园的请求报文
     * @return 惠市宝没有加签的请求报文
     */
    @Override
    public JSONObject buildReqJson(String reqJson) {
        DISP20210294InVo inVo = JSONObject.parseObject(reqJson, DISP20210294InVo.class);

        DISP20210294ReqVo reqVo = new DISP20210294ReqVo();
        BeanUtils.copyProperties(inVo, reqVo);
        reqVo.setVno("4");

        return (JSONObject)JSON.toJSON(reqVo);
    }

    /**
     * 解析惠市宝的相应报文,并转换成物流园的字段
     *
     * @param respJson 惠市宝的响应报文
     * @return 物流园的响应报文
     */
    @Override
    public String parseRespJson(String respJson) {
        DISP20210294RespVo respVo = JSONObject.parseObject(respJson, DISP20210294RespVo.class);

        DISP20210294OutVo outVo = new DISP20210294OutVo();
        BeanUtils.copyProperties(respVo, outVo);

        return JSON.toJSONString(outVo);
    }
}
