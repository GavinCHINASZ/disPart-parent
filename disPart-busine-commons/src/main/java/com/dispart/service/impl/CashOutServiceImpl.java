package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dispart.config.BusinessException;
import com.dispart.dao.TCustomWithdrawTmpMapper;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TCustomWithdrawTmp;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.result.Result;
import com.dispart.service.BaseService;
import com.dispart.service.CashOutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
@Slf4j
public class CashOutServiceImpl implements CashOutService {

    @Value("${wly.provId}")
    private String wlyProvId;

    @Autowired
    private BaseService baseService;

    @Autowired
    private TCustomWithdrawTmpMapper tCustomWithdrawTmpMapper;

    @Transactional
    @Override
    public Result<Object> cashout(InsertPayJrnDTO params) {
        // 付款方帐号为自己，收款方帐号为银行卡号
        // 新增流水
        params.setStatus(SUCCESS.getCardInfoStatus());
//        params.setPayerNo(wlyProvId);

        params.setPayerNo(params.getPayeeNum());
        params.setPayCard(params.getPayeeCard());
        params.setPayAcct(params.getPayAcct());

        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());

        // 检查付款放帐号的钱
        TAccnoInfo acctByAcct = baseService.findAcct(findAcctDTO);
        params.setPayeeAcct(acctByAcct.getAccount());

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        baseService.checkAcctAmt(acctByAcct,tPayJrn.getTxnAmt());

        baseService.updateAcctAmt(acctByAcct,tPayJrn.getTxnAmt().negate(),tPayJrn);

        try {
            // 先查询有没有tCustom
            QueryWrapper<TCustomWithdrawTmp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PROV_ID",params.getPayeeNum());
            TCustomWithdrawTmp tCustomWithdrawTmp = tCustomWithdrawTmpMapper.selectOne(queryWrapper);

            if (tCustomWithdrawTmp == null) {
                TCustomWithdrawTmp tCustomWithdrawTmp1 = new TCustomWithdrawTmp();
                tCustomWithdrawTmp1.setProvId(params.getPayeeNum());
                tCustomWithdrawTmp1.setAccruAmt(params.getTxnAmt());
                Date date = new Date();
                tCustomWithdrawTmp1.setCreatTime(date);
                tCustomWithdrawTmp1.setUpTime(date);

                int insert = tCustomWithdrawTmpMapper.insert(tCustomWithdrawTmp1);
                if (insert == 1) {
                    return Result.ok();
                } else {
                    log.error("插入失败" + JSON.toJSONString(params));
                    throw new BusinessException("插入失败");
                }
            } else {
                TCustomWithdrawTmp tCustomWithdrawTmp1 = new TCustomWithdrawTmp();
                UpdateWrapper<TCustomWithdrawTmp> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("PROV_ID",params.getPayeeNum());
                tCustomWithdrawTmp1.setUpTime(new Date());
                tCustomWithdrawTmp1.setAccruAmt(tCustomWithdrawTmp.getAccruAmt().add(params.getTxnAmt()));
                int update = tCustomWithdrawTmpMapper.update(tCustomWithdrawTmp1, updateWrapper);

                if (update == 1) {
                    return Result.ok();
                } else {
                    log.error("更新失败" + JSON.toJSONString(params));
                    throw new BusinessException("更新失败");
                }
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统错误");
        }
    }

    @Override
    public Result<Object> cashoutByCash(InsertPayJrnDTO params) {
        // 付款方帐号为自己，收款方帐号为银行卡号
        // 新增流水
        params.setStatus(SUCCESS.getCardInfoStatus());
//        params.setPayerNo(wlyProvId);
        params.setPayerNo(params.getPayeeNum());
        params.setPayCard(params.getPayeeCard());
        params.setPayAcct(params.getPayAcct());

        FindAcctDTO findAcctDTO = new FindAcctDTO();
        findAcctDTO.setPayCard(params.getPayeeCard());

        // 检查付款放帐号的钱
        TAccnoInfo acctByAcct = baseService.findAcct(findAcctDTO);
        params.setPayeeAcct(acctByAcct.getAccount());

        TPayJrn tPayJrn = baseService.insertPayJrn(params);

        baseService.checkAcctAmt(acctByAcct,tPayJrn.getTxnAmt());

        baseService.updateAcctAmt(acctByAcct,tPayJrn.getTxnAmt().negate(),tPayJrn);

        try {
            // 先查询有没有tCustom
            QueryWrapper<TCustomWithdrawTmp> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PROV_ID",params.getPayeeNum());
            TCustomWithdrawTmp tCustomWithdrawTmp = tCustomWithdrawTmpMapper.selectOne(queryWrapper);

            if (tCustomWithdrawTmp == null) {
                TCustomWithdrawTmp tCustomWithdrawTmp1 = new TCustomWithdrawTmp();
                tCustomWithdrawTmp1.setProvId(params.getPayeeNum());
                tCustomWithdrawTmp1.setAccruAmt(params.getTxnAmt());
                Date date = new Date();
                tCustomWithdrawTmp1.setCreatTime(date);
                tCustomWithdrawTmp1.setUpTime(date);

                int insert = tCustomWithdrawTmpMapper.insert(tCustomWithdrawTmp1);
                if (insert == 1) {
                    return Result.ok();
                } else {
                    log.error("插入失败" + JSON.toJSONString(params));
                    throw new BusinessException("插入失败");
                }
            } else {
                TCustomWithdrawTmp tCustomWithdrawTmp1 = new TCustomWithdrawTmp();
                UpdateWrapper<TCustomWithdrawTmp> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("PROV_ID",params.getPayeeNum());
                tCustomWithdrawTmp1.setUpTime(new Date());
                tCustomWithdrawTmp1.setAccruAmt(tCustomWithdrawTmp.getAccruAmt().add(params.getTxnAmt()));
                int update = tCustomWithdrawTmpMapper.update(tCustomWithdrawTmp1, updateWrapper);

                if (update == 1) {
                    return Result.ok();
                } else {
                    log.error("更新失败" + JSON.toJSONString(params));
                    throw new BusinessException("更新失败");
                }
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统错误");
        }
    }


}
