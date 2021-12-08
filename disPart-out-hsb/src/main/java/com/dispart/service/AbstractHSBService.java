package com.dispart.service;

import com.alibaba.fastjson.JSONObject;
import com.dispart.httpclient.HttpClient;
import com.dispart.utils.RSASignUtils;
import com.dispart.utils.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 作为服务方,接受惠市宝请求的抽象类
 */
public abstract class AbstractHSBService {

    private final Logger logger = LoggerFactory.getLogger(AbstractHSBService.class);
    /**
     * 获取服务ID
     * @return 服务ID
     */
    public abstract String getServiceId();

    /**
     * 获取物流园的请求地址
     * @return 请求的URL
     */
    protected abstract String getURL();

    /**
     * 组装到物流园的请求报文
     * @param reqJson 接受到的惠市宝的请求报文
     * @return 组装好的物流园请求报文
     */
    protected abstract String buildReqJson(String reqJson);

    /**
     * 组装惠市宝响应报文
     * @param respJson 物流园的响应报文
     * @return 转换后的惠市宝的响应报文
     */
    protected abstract JSONObject parseRespJson(String respJson);

    /**
     * 接受惠市宝请求的入口
     * @param reqJson 惠市宝的请求报文字符串
     * @return 返回给惠市宝的响应报文
     */
    public String service(String reqJson) {

        MDC.put("traceId", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        MDC.put("serviceId", getServiceId());

        logger.debug("接惠市宝的请求报文:{}", reqJson);

        JSONObject jsonObject = null;

        try {
            //验签
            RSASignUtils.verify(reqJson);

            //请求报文转换,获得发物流园的请求报文
            String req = buildReqJson(reqJson);

            //外呼请求物流园
            String respJson = HttpClient.post(getURL(), req, true);

            //响应报文转换,转换成惠市宝需要的想要报文
            jsonObject = parseRespJson(respJson);

        }catch (RuntimeException e) {
            logger.error("处理惠市宝请求出现异常", e);
            jsonObject = new JSONObject();
            jsonObject.put("Svc_Rsp_St", "01");
            jsonObject.put("Rsp_Inf", StringUtils.isEmpty(e.getMessage())? "交易出现异常":e.getMessage());
        } finally {
            //加签
            String sign = SignUtils.getSign(jsonObject.toJSONString());
            jsonObject.put("Sign_Inf", sign);

            logger.debug("返回惠市宝的报文:{}", jsonObject.toJSONString());

            MDC.clear();
        }

        return jsonObject.toJSONString();
    }
}
