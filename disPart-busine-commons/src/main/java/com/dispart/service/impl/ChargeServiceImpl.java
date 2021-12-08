package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dispart.config.BusinessException;
import com.dispart.dao.TPayJrnMapper;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.FindPayJrnDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.result.Result;
import com.dispart.service.BaseService;
import com.dispart.service.CallGeTui;
import com.dispart.service.ChargeService;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.vo.busineCommon.TPushNotesVo;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;

@Service
@Slf4j
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private BaseService baseService;

    @Autowired
    private TPayJrnMapper tPayJrnMapper;



    // hsb
    @Transactional
    @Override
    public Result<Object> chargeByHSB(InsertPayJrnDTO params) {
        // 根据收款人卡号寻找帐号
        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());
        TAccnoInfo acct = baseService.findAcct(findAcctDTO);
        params.setPayeeAcct(acct.getAccount());

        // 新增流水
        params.setStatus(UNPAY.getCardInfoStatus());

        // 收款人编号也要设置
        params.setPayeeNum(params.getPayerNo());

        TPayJrn tPayJrn = baseService.insertPayJrn(params);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> chargeByCash(InsertPayJrnDTO params) {
        // 根据收款人卡号寻找帐号
        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());
        TAccnoInfo acct = baseService.findAcct(findAcctDTO);
        params.setPayeeAcct(acct.getAccount());

        // 新增流水 状态为成功
        params.setStatus(SUCCESS.getCardInfoStatus());

        // 设置收款人编号
        params.setPayeeNum(params.getPayerNo());

        TPayJrn tPayJrn = baseService.insertPayJrn(params);
        baseService.updateAcctAmt(acct, params.getTxnAmt(),tPayJrn);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> chargeByPrePay(InsertPayJrnDTO params) {
        // 根据收款人卡号寻找帐号
        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());
        TAccnoInfo acct = baseService.findAcct(findAcctDTO);
        params.setPayeeAcct(acct.getAccount());

        // 新增流水 状态为成功
        params.setStatus(SUCCESS.getCardInfoStatus());

        // 设置收款人编号
        params.setPayeeNum(params.getPayerNo());

        TPayJrn tPayJrn = baseService.insertPayJrn(params);
        baseService.updateAcctAmt(acct, params.getTxnAmt(),tPayJrn);

//        if (insert != 1) {
//            log.error("插入数据库失败 + " + JSON.toJSONString(params));
//            throw new BusinessException("新增流水失败");
//        }
//
//        return tPayJrn;
//
//    } catch (BusinessException e) {
//        throw new BusinessException(e.getMessage());
//    } catch (Exception e) {
//        log.error("系统异常", e);
//        throw new BusinessException("系统异常");

        try {
            int i = tPayJrnMapper.updateTCard(params.getTaskId(),new Date(),tPayJrn.getJrnlNum());
            if (i != 1) {
                log.error("更新代充值表状态失败 " + JSON.toJSONString(params));
                throw new BusinessException("更新代充值表状态失败");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常",e);
            throw new BusinessException(e.getMessage());
        }

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> chongzheng(InsertPayJrnDTO params) {
        if (StringUtil.isNullOrEmpty(params.getJrnlNum())) {
            log.error("要冲正的流水号不能为空");
            throw new BusinessException("要冲正的流水号不能为空");
        }

        FindPayJrnDTO findPayJrnDTO = new FindPayJrnDTO();
        findPayJrnDTO.setJrnlNum(params.getJrnlNum());
        TPayJrn payJrn = baseService.findPayJrn(findPayJrnDTO);

        UpdateWrapper<TPayJrn> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("JRNL_NUM",params.getJrnlNum());
//        TPayJrn tPayJrn = new TPayJrn();
//        tPayJrn.setBusinessNo(payJrn.getJrnlNum());
//        tPayJrn.setStatus(CHONGZHENG.getCardInfoStatus());
//        tPayJrn.setRemark(payJrn.getRemark() + "-冲正");
//        try {
//            int update = tPayJrnMapper.update(tPayJrn, updateWrapper);
//            if (update != 1) {
//                throw new BusinessException("冲正异常");
//            }
//        } catch (BusinessException e) {
//            throw new BusinessException(e.getMessage());
//        } catch (Exception e) {
//            log.error("系统异常",e);
//            throw new BusinessException(e.getMessage());
//        }

        // 根据收款人卡号寻找帐号
        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());
        TAccnoInfo acct = baseService.findAcct(findAcctDTO);

        baseService.checkAcctAmt(acct, BigDecimal.ZERO);

        params.setPayeeAcct(acct.getAccount());

        // 新增流水 状态为成功
        params.setStatus(SUCCESS.getCardInfoStatus());

        // 设置收款人编号
        params.setPayeeNum(params.getPayerNo());
        params.setRemark(payJrn.getRemark() + "-冲正");
        params.setBusinessNo(payJrn.getJrnlNum());

        TPayJrn tPayJrn1 = baseService.insertPayJrn(params);
        baseService.updateAcctAmt(acct, params.getTxnAmt().negate(),tPayJrn1);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> chargeByPos(InsertPayJrnDTO params) {
        // 根据收款人卡号寻找帐号
        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());
        TAccnoInfo acct = baseService.findAcct(findAcctDTO);
        params.setPayeeAcct(acct.getAccount());
        params.setPayeeNum(params.getPayerNo());
        // 新增流水 状态为成功
//        params.setStatus(SUCCESS.getCardInfoStatus());
        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        // 收款方加钱
//        TAccnoInfo acct = baseService.findAcctByAcct(tPayJrn.getPayeeAcct());

        if (StringUtil.isNullOrEmpty(params.getStatus())) {
            log.error("没有状态" + JSON.toJSONString(params));
            throw new BusinessException("没有状态");
        }

        if (params.getStatus().equals("2")) {
            baseService.updateAcctAmt(acct, params.getTxnAmt(),tPayJrn);
        }

        return Result.ok();
    }

    @Transactional
    @Override
    public Result<Object> callBackByHSBCharge(TPayJrn tPayJrn) {
//        baseService.updatePayJrn(tPayJrn);
        TAccnoInfo acctByAcct = baseService.findAcctByAcct(tPayJrn.getPayeeAcct());
        // 增加付款放帐户的钱
        baseService.updateAcctAmt(acctByAcct, tPayJrn.getTxnAmt(),tPayJrn);
        return Result.ok();
    }
}
