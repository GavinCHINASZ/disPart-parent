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

import javax.annotation.Resource;

/**
 * 行内提现服务
 */
@Service
public class DISP20210282ServiceImpl extends AbstractInternetBankService<DISP20210282ReqVo> {

    private final Logger logger = LoggerFactory.getLogger(DISP20210282ServiceImpl.class);

    @Resource
    BankNacosConfig config;

    @Override
    protected RequestMsg buildRequestMsg(DISP20210282ReqVo reqVo) {

        RequestMsg msg = new RequestMsg();

        msg.setCustId(config.getCustId());
        msg.setPassword(config.getPassword());
        msg.setUserId(config.getUserId());
        msg.setTxCode("6W1303");
        msg.setRequestSn(SequenceUtils.sequence());
        msg.setLanguage("CN");

        W1303ReqVo w1303ReqVo = new W1303ReqVo();
        w1303ReqVo.setAccNo1(config.getAccno());
        w1303ReqVo.setBillCode(config.getBillCode());
        w1303ReqVo.setAccNo2(reqVo.getProvAcct());
        w1303ReqVo.setOtherName(reqVo.getProvNm());
        w1303ReqVo.setAmount(reqVo.getAmount());
        w1303ReqVo.setUseofCode(config.getUseOfCode());
        w1303ReqVo.setFlowFlag("1");
        w1303ReqVo.setUbankNo(reqVo.getUbankNo());
        w1303ReqVo.setRem1(reqVo.getRemark());
        w1303ReqVo.setRem2(reqVo.getRemark2());

        msg.setBody(w1303ReqVo);

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

        W1303RespMsg respMsg = ConvertUtil.xmlToBean(respXml, W1303RespMsg.class);

        if("000000".equals(respMsg.getReturnCode())) {
            logger.info("网银返回成功报文");
            return Result.ok(respMsg.getBody());
        }

        return new Result<ResponseBody>().message(respMsg.getReturnMsg()).code(ResultCodeEnum.FAIL.getCode());
    }

}
