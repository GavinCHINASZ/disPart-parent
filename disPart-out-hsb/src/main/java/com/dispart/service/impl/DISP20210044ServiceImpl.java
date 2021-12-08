package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dispart.httpclient.HttpClient;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.vo.DISP20210044ReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 参数查询, 从物流园参数管理查询惠市宝公钥及自己的私钥
 */
@Service("disp20210044Service")
public class DISP20210044ServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(DISP20210044ServiceImpl.class);

    @Value("${service.url.DISP20210044}")
    private String url;

    private String getURL() {
        logger.debug("获取参数管理的请求URL地址:{}", url);

        return url;
    }

    public String service(String type, String paramName) {

        // 请求报文
        String reqJson = buildReqJson(type, paramName);

        // 外呼请求
        String respJson = HttpClient.post(getURL(), reqJson, true);

        JSONObject jsonObject = JSON.parseObject(respJson);

        JSONObject data = jsonObject.getJSONObject("data");

        String paramVal = null;
        if(data != null) {
            JSONArray jsonArray = data.getJSONArray("parmeterList");
            JSONObject object = jsonArray.getJSONObject(0);
            paramVal = object.getObject("paramVal", String.class);
        }

        if(StringUtils.isEmpty(paramVal)) {
            logger.error("获取的RSA秘钥为空, 请求报文:{}", reqJson);
            throw new RuntimeException("RSA秘钥为空");
        }

        return paramVal;
    }

    /**
     * 构造参数管理的请求报文
     * @param type 参数类型
     * @param paramName 参数名称
     * @return 参数管理的请求报文
     */
    private String buildReqJson(String type, String paramName) {
        DISP20210044ReqVo reqVo = new DISP20210044ReqVo();
        reqVo.setParamType(type);
        reqVo.setParamNm(paramName);

        RequestHead head = new RequestHead();
        head.setReqDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        head.setReqSeqNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        head.setReqStamp(Instant.now().toString());
        head.setVersionNo("1.0");

        Request<DISP20210044ReqVo> request = new Request<>();
        request.setBody(reqVo);
        request.setHead(head);


        return JSON.toJSONString(request);
    }
}
