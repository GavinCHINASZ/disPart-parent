package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractOutHSBService;
import com.dispart.vo.DISP20210061ReqVo;
import com.dispart.vo.DISP20210061RespVo;
import com.dispart.vo.DISP20210096ReqVo;
import com.dispart.vo.DISP20210096RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 惠市宝支付下单
 */
@Service
public class DISP20210096ServiceImpl extends AbstractOutHSBService {

    Logger logger = LoggerFactory.getLogger(DISP20210096ServiceImpl.class);

    @Value("${service.url.DISP20210096}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210096";
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

        DISP20210061ReqVo disp20210061ReqVo = JSON.parseObject(reqJson, DISP20210061ReqVo.class);
        DISP20210096ReqVo reqVo = new DISP20210096ReqVo();
        BeanUtils.copyProperties(disp20210061ReqVo, reqVo);

        ArrayList<DISP20210061ReqVo.Order> list = disp20210061ReqVo.getList();
        ArrayList<DISP20210096ReqVo.OrderList> orderLists = new ArrayList<>();
        for (DISP20210061ReqVo.Order order : list) {
            DISP20210096ReqVo.OrderList orderList = new DISP20210096ReqVo.OrderList();
            BeanUtils.copyProperties(order, orderList);
            orderLists.add(orderList);

            List<DISP20210061ReqVo.PrjList> prjList = order.getPrjList();
            ArrayList<DISP20210096ReqVo.PrjList> list1 = new ArrayList<>();
            if(prjList != null ) {
                for (DISP20210061ReqVo.PrjList prjList1 : prjList) {
                    DISP20210096ReqVo.PrjList var = new DISP20210096ReqVo.PrjList();
                    BeanUtils.copyProperties(prjList1, var);
                    list1.add(var);
                }
                orderList.setPrjList(list1);
            }
        }

        reqVo.setList(orderLists);

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
        DISP20210096RespVo disp20210096RespVo = JSON.parseObject(respJson, DISP20210096RespVo.class);
        DISP20210061RespVo respVo = new DISP20210061RespVo();
        BeanUtils.copyProperties(disp20210096RespVo, respVo);



        ArrayList<DISP20210096RespVo.OrderList> orderLists = disp20210096RespVo.getOrderLists();
        if(orderLists != null && orderLists.size()>0) {
            ArrayList<DISP20210061RespVo.OrderList> list = new ArrayList<>();
            for (DISP20210096RespVo.OrderList orderList : orderLists) {
                DISP20210061RespVo.OrderList order = new DISP20210061RespVo.OrderList();
                BeanUtils.copyProperties(orderList, order);
                list.add(order);
            }
            respVo.setOrderLists(list);
        }

        return JSON.toJSONString(respVo);
    }

}
