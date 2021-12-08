package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractOutHSBService;
import com.dispart.vo.DISP20210076ReqVo;
import com.dispart.vo.DISP20210076RespVo;
import com.dispart.vo.DISP20210093ReqVo;
import com.dispart.vo.DISP20210093RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 查询惠市宝签约客户信息
 */
@Service
public class DISP20210093ServiceImpl extends AbstractOutHSBService {

    private final Logger logger = LoggerFactory.getLogger(DISP20210093ServiceImpl.class);

    @Value("${service.url.DISP20210093}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210093";
    }

    /**
     * 获取服务方的URL
     *
     * @return 服务方的URL
     */
    @Override
    public String getURL() {
        logger.debug("获取惠市宝的服务URL地址:" + url);

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
        DISP20210076ReqVo disp20210076ReqVo = JSON.parseObject(reqJson, DISP20210076ReqVo.class);

        DISP20210093ReqVo reqVo = new DISP20210093ReqVo();
        BeanUtils.copyProperties(disp20210076ReqVo, reqVo);

        return ((JSONObject) JSON.toJSON(reqVo));
    }

    /**
     * 解析惠市宝的相应报文,并转换成物流园的字段
     *
     * @param respJson 惠市宝的响应报文
     * @return 物流园的响应报文
     */
    @Override
    public String parseRespJson(String respJson) {
        DISP20210093RespVo disp20210093RespVo = JSON.parseObject(respJson, DISP20210093RespVo.class);

        logger.debug("解析后的响应报文:{}", JSON.toJSONString(disp20210093RespVo));

        DISP20210076RespVo respVo = new DISP20210076RespVo();
        BeanUtils.copyProperties(disp20210093RespVo, respVo);

        if(disp20210093RespVo.getList() != null) {
            ArrayList<DISP20210076RespVo.CustomInfo> list = new ArrayList<>();
            for (DISP20210093RespVo.CustomInfo customInfo : disp20210093RespVo.getList()) {
                DISP20210076RespVo.CustomInfo custom = new DISP20210076RespVo.CustomInfo();

                BeanUtils.copyProperties(customInfo, custom);
                list.add(custom);
            }

            respVo.setList(list);
        }

        return JSON.toJSONString(respVo);
    }


}
