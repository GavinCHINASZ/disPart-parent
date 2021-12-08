package com.dispart.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.httpclient.HttpClient;
import com.dispart.utils.RSASignUtils;
import com.dispart.utils.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 外呼惠市宝抽象方法
 */
public abstract class AbstractOutHSBService {

    private final Logger logger = LoggerFactory.getLogger(AbstractOutHSBService.class);

    /**
     * 获取服务ID
     * @return 服务ID
     */
    public abstract String getServiceId();

    /**
     * 获取服务方的URL
     * @return 服务方的URL
     */
    public abstract String getURL();

    /**
     * 解析收到的物流园请求报文,并把报文转换成惠市宝需要的报文
     * @param reqJson 物流园的请求报文
     * @return 惠市宝没有加签的请求报文
     */
    public abstract JSONObject buildReqJson(String reqJson);

    /**
     * 解析惠市宝的相应报文,并转换成物流园的字段
     * @param respJson 惠市宝的响应报文
     * @return 物流园的响应报文
     */
    public abstract String parseRespJson(String respJson);

    /**
     * 外呼惠市宝的方法入口
     * @param reqJson 接收的物流园请求报文
     * @return 给物流园的响应报文
     */
    public String service(String reqJson) {

        JSONObject object = JSON.parseObject(reqJson);
        MDC.put("traceId", object.get("sndTraceId").toString());
        MDC.put("serviceId", getServiceId());

        logger.debug("收到的请求报文:{}", reqJson);

        String resp = null;
        try {
            //获得请求报文
            JSONObject jsonObject = buildReqJson(reqJson);
            jsonObject.put("Ittparty_Stm_Id", "00000"); //发起渠道号
            jsonObject.put("Py_Chnl_Cd", "0000000000000000000000000"); //支付渠道代码

            //加签
            String sign = SignUtils.getSign(jsonObject.toJSONString());
            jsonObject.put("Sign_Inf", RSASignUtils.sign(sign));

            //获取请求地址
            String url = getURL();

            //外呼获取响应报文
            String respJson = HttpClient.post(url, jsonObject.toJSONString());

            //验签
            RSASignUtils.verify(respJson);

            //解析响应报文
             resp = parseRespJson(respJson);

        }catch (RuntimeException e) {
            logger.error("请求惠市宝出现异常,请求报文:{}", reqJson);
            logger.error("请求惠市宝出现异常", e);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("txnSt", "01");
            jsonObject.put("errCode", "HSBWLPT00001");
            jsonObject.put("errMsg", "外呼惠市宝出现异常");

            resp = jsonObject.toJSONString();
        }finally {
            logger.info("转换后的响应报文:{}", resp);
            MDC.clear();
        }

        return resp;
    }

}
