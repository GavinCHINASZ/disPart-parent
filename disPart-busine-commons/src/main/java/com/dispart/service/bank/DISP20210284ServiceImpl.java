package com.dispart.service.bank;

import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.utils.ConvertUtil;
import com.dispart.utils.SequenceUtils;
import com.dispart.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DISP20210284ServiceImpl extends AbstractInternetBankService<DISP20210284RepVo> {

    private final Logger logger = LoggerFactory.getLogger(DISP20210284ServiceImpl.class);

    @Autowired
    private BankNacosConfig config;

    /**
     * 接收物流园请求报文转换成对公网银的请求报文
     *
     * @param reqVo 请求报文对象
     * @return 返回转换后的xml报文
     */
    @Override
    protected RequestMsg buildRequestMsg(DISP20210284RepVo reqVo) {

        RequestMsg msg = new RequestMsg();

        msg.setCustId(config.getCustId());
        msg.setPassword(config.getPassword());
        msg.setUserId(config.getUserId());

        msg.setTxCode("6W0101");
        msg.setRequestSn(SequenceUtils.sequence());
        msg.setLanguage("CN");

        W0101ReqVo w0101ReqVo = new W0101ReqVo();
        w0101ReqVo.setAccBranchCode(config.getBranchCode());
        w0101ReqVo.setAccNo(reqVo.getAccNo());
        w0101ReqVo.setAccNo1(config.getAccno());

        msg.setBody(w0101ReqVo);

        return msg;
    }

    /**
     * 把网银响应报文转换成物流园的响应报文
     *
     * @param respXml 网银响应报文
     * @return 转换后的物流园响应报文
     */
    @Override
    protected Result<ResponseBody> convertToJson(String respXml) {
        W0101RespMsg respMsg = ConvertUtil.xmlToBean(respXml, W0101RespMsg.class);

        if("000000".equals(respMsg.getReturnCode())) {
            logger.info("网银返回成功报文");
            return Result.ok(respMsg.getBody());
        }

        return new Result<ResponseBody>().message(respMsg.getReturnMsg()).code(ResultCodeEnum.FAIL.getCode());
    }

}
