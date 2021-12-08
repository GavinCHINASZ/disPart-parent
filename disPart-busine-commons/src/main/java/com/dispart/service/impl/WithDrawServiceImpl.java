package com.dispart.service.impl;

import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.PayJrn;
import com.dispart.result.Result;
import com.dispart.service.BaseService;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.service.WithDrawService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;
import static com.dispart.model.businessCommon.TxnTypeEnum.*;

@Service
@Slf4j
public class WithDrawServiceImpl implements WithDrawService {

    @Autowired
    private BaseService baseService;

    @Value("${wly.provId}")
    private String wlyProvId;

    @Transactional
    @Override
    public Result<Object> withdrawByHSB(InsertPayJrnDTO params) {
        // 新增流水
        if (params.getStatus().equals("00")){
            params.setStatus(SUCCESS.getCardInfoStatus());
            params.setPayeeNum(params.getPayerNo());
            params.setPayerNo(wlyProvId);

            TPayJrn tPayJrn = baseService.insertPayJrn(params);

            PayJrn pay = new PayJrn();
            BeanUtils.copyProperties(tPayJrn,pay);
            pay.setStatus(WIHDRAW_SUCCESS.getCardInfoStatus());
            baseService.payInc(pay);

            return Result.ok();
        } else if (params.getStatus().equals("01")) {
            params.setStatus(FAIL.getCardInfoStatus());
            params.setPayeeNum(params.getPayerNo());
            params.setPayerNo(wlyProvId);

            TPayJrn tPayJrn = baseService.insertPayJrn(params);

            PayJrn pay = new PayJrn();
            BeanUtils.copyProperties(tPayJrn,pay);
            pay.setStatus(SUCCESS.getCardInfoStatus());
            baseService.payInc(pay);

            return Result.ok();
        } else {
            params.setStatus(WIHDRAW_WAIT.getCardInfoStatus());
            params.setPayeeNum(params.getPayerNo());
            params.setPayerNo(wlyProvId);

            TPayJrn tPayJrn = baseService.insertPayJrn(params);

            PayJrn pay = new PayJrn();
            BeanUtils.copyProperties(tPayJrn,pay);
            pay.setStatus(WIHDRAW_WAIT.getCardInfoStatus());
            baseService.payInc(pay);

            return Result.ok();
        }

    }

    @Transactional
    @Override
    public Result<Object> withdrawByCash(InsertPayJrnDTO params) {
        // 新增流水
        params.setStatus(SUCCESS.getCardInfoStatus());

        params.setPayeeNum(params.getPayerNo());
        params.setPayerNo(wlyProvId);

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(tPayJrn,pay);
        pay.setStatus(WIHDRAW_SUCCESS.getCardInfoStatus());
        baseService.payInc(pay);

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> withdrawByPos(InsertPayJrnDTO params) {
        // 新增流水
        params.setStatus(SUCCESS.getCardInfoStatus());
        params.setPayeeNum(params.getPayerNo());
        params.setPayerNo(wlyProvId);

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(tPayJrn,pay);
        pay.setStatus(WIHDRAW_SUCCESS.getCardInfoStatus());
        baseService.payInc(pay);

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> withdrawByCard(InsertPayJrnDTO params) {
        String provId = params.getPayeeNum();
        // 新增流水 状态为成功

        String tmpStatus = null;
        if (StringUtil.isNullOrEmpty(params.getStatus())) {
            params.setStatus(SUCCESS.getCardInfoStatus());
        } else {
            tmpStatus = params.getStatus();
            params.setStatus(params.getStatus());
        }

        params.setPayeeNum(params.getPayerNo());
        params.setPayerNo(wlyProvId);

//        if (params.getTxnType().equals(CALCELSUBSIDY.getTxnType())) {
//            params.setPayerNo(provId);
//            params.setPayeeNum(wlyProvId);
//        }

        FindAcctDTO findAcctDto = new FindAcctDTO();
        findAcctDto.setPayCard(params.getPayeeCard());
        TAccnoInfo acctByAcct = baseService.findAcct(findAcctDto);
        params.setPayeeAcct(acctByAcct.getAccount());

        // 无论传上来的status如何，流水都成功
        params.setStatus(SUCCESS.getCardInfoStatus());
        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        if (!StringUtil.isNullOrEmpty(tmpStatus)) {
            params.setStatus(tmpStatus);
        }

        if (params.getStatus().equals(SUCCESS.getCardInfoStatus())) {

            // 寻找付款方卡
            TAccnoInfo acct = baseService.findAcctByAcct(tPayJrn.getPayAcct());

            // 检查付款账户的钱
            baseService.checkAcctAmt(acct,params.getTxnAmt());

            // 减少付款帐户的钱
            baseService.updateAcctAmt(acct,params.getTxnAmt().negate(),tPayJrn);

            // 超找收款方账户
//        TAccnoInfo acctByAcct = baseService.findAcctByAcct(params.getPayeeAcct());

            // 收款方账户增加钱
            baseService.updateAcctAmt(acctByAcct,params.getTxnAmt(),tPayJrn);

            PayJrn pay = new PayJrn();
            BeanUtils.copyProperties(tPayJrn,pay);
            pay.setStatus(WIHDRAW_SUCCESS.getCardInfoStatus());
            baseService.payInc(pay);
        }

        if (!params.getStatus().equals(SUCCESS.getCardInfoStatus())) {
            PayJrn pay = new PayJrn();
            BeanUtils.copyProperties(tPayJrn,pay);
            baseService.payInc(pay);
        }

        return Result.ok(tPayJrn);
    }

    @Transactional
    @Override
    public Result<Object> callBackByHSBWithdraw(TPayJrn params) {
//        baseService.updatePayJrn(params);

        //更新业务账单支付状态
        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(params,pay);
        if (params.getStatus().equals(SUCCESS.getCardInfoStatus())) {
            pay.setStatus(WIHDRAW_SUCCESS.getCardInfoStatus());
        } else {
            pay.setStatus(SUCCESS.getCardInfoStatus());
        }

        baseService.payInc(pay);
        return Result.ok();
    }

    @Override
    public Result<Object> withdrawMoney(InsertPayJrnDTO params) {
        String provId = params.getPayeeNum();
        // 新增流水 状态为成功

        String tmpStatus = null;
        if (StringUtil.isNullOrEmpty(params.getStatus())) {
            params.setStatus(SUCCESS.getCardInfoStatus());
        } else {
            tmpStatus = params.getStatus();
            params.setStatus(params.getStatus());
        }

        params.setPayeeNum(params.getPayerNo());
        params.setPayerNo(wlyProvId);
        params.setPayeeCard(params.getPayCard());
        params.setPayeeAcct(params.getPayAcct());

        if (params.getTxnType().equals(SUPPLY_CALCELSUBSIDY.getTxnType()) || params.getTxnType().equals(PURCH_CALCELSUBSIDY)) {
            params.setPayerNo(provId);
            params.setPayeeNum(wlyProvId);
        }

        FindAcctDTO findAcctDto = new FindAcctDTO();
        findAcctDto.setPayCard(params.getPayeeCard());
        TAccnoInfo acctByAcct = baseService.findAcct(findAcctDto);
        params.setPayeeAcct(acctByAcct.getAccount());

        // 无论传上来的status如何，流水都成功
        params.setStatus(SUCCESS.getCardInfoStatus());
        params.setPayeeAcct(null);
        params.setPayeeCard(null);

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        if (!StringUtil.isNullOrEmpty(tmpStatus)) {
            params.setStatus(tmpStatus);
        }

        if (params.getStatus().equals(SUCCESS.getCardInfoStatus())) {

            // 寻找付款方卡
            TAccnoInfo acct = baseService.findAcctByAcct(tPayJrn.getPayAcct());

            // 检查付款账户的钱
            baseService.checkAcctAmt(acct,params.getTxnAmt());

            // 减少付款帐户的钱
            baseService.updateAcctAmt(acct,params.getTxnAmt().negate(),tPayJrn);

            // 超找收款方账户
//        TAccnoInfo acctByAcct = baseService.findAcctByAcct(params.getPayeeAcct());

            // 收款方账户增加钱
//            baseService.updateAcctAmt(acctByAcct,params.getTxnAmt());

//            PayJrn pay = new PayJrn();
//            BeanUtils.copyProperties(tPayJrn,pay);
//            pay.setStatus(WIHDRAW_SUCCESS.getCardInfoStatus());
//            baseService.payInc(pay);
        }

//        if (!params.getStatus().equals(SUCCESS.getCardInfoStatus())) {
//            PayJrn pay = new PayJrn();
//            BeanUtils.copyProperties(tPayJrn,pay);
//            baseService.payInc(pay);
//        }

        return Result.ok(tPayJrn);
    }
}
