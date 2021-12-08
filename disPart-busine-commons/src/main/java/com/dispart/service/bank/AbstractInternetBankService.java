package com.dispart.service.bank;

import com.alibaba.fastjson.JSONObject;
import com.dispart.result.Result;
import com.dispart.utils.ConvertUtil;
import com.dispart.utils.SpringUtil;
import com.dispart.vo.RequestMsg;
import com.dispart.vo.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractInternetBankService<R> {
    private final Logger logger = LoggerFactory.getLogger(AbstractInternetBankService.class);


    /**
     * 接收物流园请求报文转换成对公网银的请求报文
     * @param reqVo 请求的报文对象
     * @return 返回转换后的xml报文
     */
    protected abstract RequestMsg buildRequestMsg(R reqVo);

    /**
     * 把网银响应报文转换成物流园的响应报文
     * @param respXml 网银响应报文
     * @return 转换后的物流园响应报文
     */
    protected abstract Result<ResponseBody> convertToJson(String respXml) ;

    public Result<ResponseBody> service(R reqJson) {

        logger.debug("接受到请求报文:{}", JSONObject.toJSONString(reqJson));

        try {
            //请求报文转换成xml
            RequestMsg reqMsg = buildRequestMsg(reqJson);
            String reqXml = ConvertUtil.beanToXml(reqMsg);
            String tmp = reqXml.replaceAll("\n", "").replaceAll("> *<", "><");
            String xml = "<?xml version=\"1.0\" encoding=\"GB18030\"?>" + tmp;

            logger.debug("转换后的xml请求报文:{}", xml);

            //外呼网银前置机
            SocketClient socketClient = SpringUtil.getBean("socketClient", SocketClient.class);
            String respXml = socketClient.send(xml);

            //响应报文转化成json
            return convertToJson(respXml);

        }catch (Exception e) {
            logger.error("对公网银交易出现异常", e);
            return Result.build(600, "外呼网银交易失败");
        }

    }
}
