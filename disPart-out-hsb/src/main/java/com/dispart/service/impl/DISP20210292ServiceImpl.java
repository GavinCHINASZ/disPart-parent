package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.service.AbstractOutHSBService;
import com.dispart.vo.DISP20210292InVo;
import com.dispart.vo.DISP20210292OutVo;
import com.dispart.vo.DISP20210292ReqVo;
import com.dispart.vo.DISP20210292RespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


/**
 * 订单退款
 */
@Service
public class DISP20210292ServiceImpl extends AbstractOutHSBService {

    private final Logger logger = LoggerFactory.getLogger(DISP20210292ServiceImpl.class);

    @Value("${service.url.DISP20210292}")
    private String url;

    /**
     * 获取服务ID
     *
     * @return 服务ID
     */
    @Override
    public String getServiceId() {
        return "DISP20210292";
    }

    /**
     * 获取服务方的URL
     *
     * @return 服务方的URL
     */
    @Override
    public String getURL() {
        logger.debug("获取订单退款的服务器地址:{}", url);

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

        DISP20210292InVo inVo = JSON.parseObject(reqJson, DISP20210292InVo.class);
        DISP20210292ReqVo reqVo = new DISP20210292ReqVo();
        BeanUtils.copyProperties(inVo, reqVo);

        ArrayList<DISP20210292InVo.SubOrdr> list = inVo.getList();
        ArrayList<DISP20210292ReqVo.SubOrdr> subOrdrlist = new ArrayList<>();
        for (DISP20210292InVo.SubOrdr subOrdr : list) {
            DISP20210292ReqVo.SubOrdr reqSubOrdr = new DISP20210292ReqVo.SubOrdr();
            reqSubOrdr.setSubOrderNo(subOrdr.getSubOrderNo());
            reqSubOrdr.setRefundAmt(subOrdr.getRefundAmt());

            ArrayList<DISP20210292InVo.ParList> parList = subOrdr.getParList();
            ArrayList<DISP20210292ReqVo.ParList> parLists = new ArrayList<>();
            if(parList != null && parList.size() > 0) {
                for (DISP20210292InVo.ParList parList1 : parList) {
                    DISP20210292ReqVo.ParList parList2 = new DISP20210292ReqVo.ParList();
                    parList2.setAmt(parList1.getAmt());
                    parList2.setProvId(parList1.getProvId());

                    parLists.add(parList2);

                }
            }

            ArrayList<DISP20210292InVo.PrjList> prjList = subOrdr.getPrjList();
            ArrayList<DISP20210292ReqVo.PrjList> prjLists = new ArrayList<>();
            if(prjList != null && prjList.size() > 0) {
                for (DISP20210292InVo.PrjList prjList1 : prjList) {
                    DISP20210292ReqVo.PrjList prjList2 = new DISP20210292ReqVo.PrjList();
                    prjList2.setItemNo(prjList1.getItemNo());
                    prjList2.setRefundAmt(prjList1.getRefundAmt());

                    prjLists.add(prjList2);
                }
            }
            reqSubOrdr.setParList(parLists);
            reqSubOrdr.setPrjList(prjLists);
            subOrdrlist.add(reqSubOrdr);
        }

        reqVo.setVersion("3");
        reqVo.setList(subOrdrlist);

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
        DISP20210292RespVo respVo = JSONObject.parseObject(respJson, DISP20210292RespVo.class);

        DISP20210292OutVo outVo = new DISP20210292OutVo();
        BeanUtils.copyProperties(respVo, outVo);

        return JSON.toJSONString(outVo);
    }
}
