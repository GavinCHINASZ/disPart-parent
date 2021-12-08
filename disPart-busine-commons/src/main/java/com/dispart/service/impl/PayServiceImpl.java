package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.config.BusinessException;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.PayJrn;
import com.dispart.result.Result;
import com.dispart.service.BaseService;
import com.dispart.service.PayService;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TPayJrn;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Value("${wly.provId}")
    private String wlyProvId;

    @Autowired
    private BaseService baseService;

    @Transactional
    @Override
    public Result<Object> payByHSB(InsertPayJrnDTO params) {
        params.setStatus(UNPAY.getCardInfoStatus());
        params.setPayeeNum(wlyProvId);

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        //更新业务账单支付状态
        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(tPayJrn,pay);
        pay.setStatus(UNPAY.getCardInfoStatus());
        baseService.payInc(pay);

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> payByCash(InsertPayJrnDTO params) {
        // 新增流水 状态为成功
        params.setStatus(SUCCESS.getCardInfoStatus());
        params.setPayeeNum(wlyProvId);

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        //更新业务账单支付状态
        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(tPayJrn,pay);
        pay.setStatus(SUCCESS.getCardInfoStatus());
        baseService.payInc(pay);

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> payByPos(InsertPayJrnDTO params) {
        // pos结果由前端传上来
//        params.setStatus(SUCCESS.getCardInfoStatus());

        if (StringUtil.isNullOrEmpty(params.getStatus())) {
            log.error("缺少参数status" + JSON.toJSONString(params));
            throw new BusinessException("缺少参数status");
        }

        params.setPayeeNum(wlyProvId);

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        if (params.getStatus().equals("2")) {
            //更新业务账单支付状态
            PayJrn pay = new PayJrn();
            BeanUtils.copyProperties(tPayJrn,pay);
            pay.setStatus(SUCCESS.getCardInfoStatus());
            baseService.payInc(pay);
        }

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> payByCard(InsertPayJrnDTO params) {
        // 根据收款人卡号寻找帐号
        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayCard());
        TAccnoInfo acct = baseService.findAcct(findAcctDTO);
        params.setPayAcct(acct.getAccount());
        params.setPayeeNum(wlyProvId);
//        params.setPayeeAcct(acct.getAccount());
//        TAccnoInfo acct = baseService.findAcctByAcct(tPayJrn.getPayAcct());

        // 新增流水 状态为成功
        params.setStatus(SUCCESS.getCardInfoStatus());
        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        // 寻找付款方卡
//        FindAcctDTO findAcctDTO = new FindAcctDTO();
//        findAcctDTO.setPayCard(params.getPayCard());

        // 检查付款放帐户的钱
        baseService.checkAcctAmt(acct,params.getTxnAmt());

        // 减少付款放帐户的钱
        baseService.updateAcctAmt(acct,params.getTxnAmt().negate(),tPayJrn);

        // 查找收款方账号
        // 直接物流园帐号
        TAccnoInfo acctByAcct = baseService.findAcctByAcct(params.getPayeeAcct());

        // 收款方账户增加钱
        baseService.updateAcctAmt(acctByAcct,params.getTxnAmt(),tPayJrn);

        //更新业务账单支付状态
        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(tPayJrn,pay);
        pay.setStatus(SUCCESS.getCardInfoStatus());
        baseService.payInc(pay);

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> callBackByHSBPay(TPayJrn params) {
//        Boolean aBoolean = baseService.updatePayJrn(params);

        //更新业务账单支付状态
        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(params,pay);

        if (params.getStatus().equals(SUCCESS.getCardInfoStatus())) {
            pay.setStatus(SUCCESS.getCardInfoStatus());
        } else {
            pay.setStatus("9");
        }

        baseService.payInc(pay);

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> subsidy(InsertPayJrnDTO params) {
        String payAcct = params.getPayeeAcct();
        String payeeCard = params.getPayCard();
        String payeeAcct = params.getPayAcct();
        params.setPayCard(null);

        params.setPayeeNum(params.getPayerNo());
        params.setPayerNo(wlyProvId);

        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(payeeCard);
        TAccnoInfo acct1 = baseService.findAcct(findAcctDTO);

        // 新增流水 状态为成功
//        params.setPayAcct(payAcct);
        params.setPayeeCard(payeeCard);
        params.setPayeeAcct(acct1.getAccount());
        params.setStatus(SUCCESS.getCardInfoStatus());
        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        // 寻找付款方卡
//        TAccnoInfo acct = baseService.findAcctByAcct(tPayJrn.getPayAcct());

        // 检查付款账户的钱
//        baseService.checkAcctAmt(acct,params.getTxnAmt());

        // 减少付款帐户的钱
//        baseService.updateAcctAmt(acct,params.getTxnAmt().negate());

        // 收款方账户增加钱
        baseService.updateAcctAmt(acct1,params.getTxnAmt(),tPayJrn);

        return Result.ok();
    }
}
