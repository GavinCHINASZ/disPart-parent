package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dispart.config.BusinessException;
import com.dispart.dao.*;
import com.dispart.dto.busineCommon.*;
import com.dispart.model.MCardInfo;
import com.dispart.model.PayJrn;
import com.dispart.model.VechicleMonthPayDetails;
import com.dispart.model.order.OrderStatusEnum;
import com.dispart.service.BaseService;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TMembershipInfo;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.tmp.TAccnoChangeDetails;
import com.dispart.utils.MacUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;
import static com.dispart.model.businessCommon.CardInfoStatusEnum.WIHDRAW_FAIL;
import static com.dispart.model.businessCommon.TxnTypeEnum.*;
import static com.dispart.model.businessCommon.TxnTypeEnum.CARFEE;


@Service
@Slf4j
public class BaseServiceImpl implements BaseService {

    @Autowired
    private TAccnoInfoMapper tAccnoInfoMapper;

    @Autowired
    private TPayJrnMapper tPayJrnMapper;

    @Autowired
    private TMembershipInfoMapper tMembershipInfoMapper;

    @Autowired
    private IPayIncDao payIncDao;

    @Autowired
    private TAccnoChangeDetailsMapper tAccnoChangeDetailsMapper;

    @Override
    public TPayJrn insertPayJrn(InsertPayJrnDTO params) {

        TPayJrn tPayJrn = new TPayJrn();
        try {
            BeanUtils.copyProperties(params, tPayJrn);
            Map map = tPayJrnMapper.queryJnrlNum();
            Integer jrnlNum = (Integer) map.get("jrnlNum");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
            LocalDateTime localDateTime = LocalDateTime.now();
            String newLocalDateTime = localDateTime.format(dtf);
            String rightAppend = String.valueOf(jrnlNum);
            if (rightAppend.length() < 7) {
                rightAppend = String.format("%7d", jrnlNum).replace(" ", "0");
            } else {
                rightAppend = rightAppend.substring(rightAppend.length() - 7, rightAppend.length());
            }

            String jrnl = newLocalDateTime + rightAppend;
            tPayJrn.setJrnlNum(jrnl);

            Date date = new Date();
            tPayJrn.setUpTime(date);
            tPayJrn.setCreatTime(date);
            tPayJrn.setTxnTm(date);
            tPayJrn.setChanlNo(params.getChanlNo());

            int insert = tPayJrnMapper.insert(tPayJrn);
            if (insert != 1) {
                log.error("插入数据库失败 + " + JSON.toJSONString(params));
                throw new BusinessException("新增流水失败");
            }

            return tPayJrn;

        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统异常");
        }
    }

    @Override
    public TPayJrn findPayJrn(FindPayJrnDTO params) {
        log.debug("查找流水 +" + JSON.toJSONString(params));

        QueryWrapper<TPayJrn> queryWrapper = new QueryWrapper<>();
        if (!StringUtil.isNullOrEmpty(params.getMainOrderId())) {
            queryWrapper.eq("MAIN_ORDER_ID", params.getMainOrderId());
        }

        if (!StringUtil.isNullOrEmpty(params.getBusinessNo())) {
            queryWrapper.eq("BUSINESS_NO", params.getBusinessNo());
        }

        if (!StringUtil.isNullOrEmpty(params.getJrnlNum())) {
            queryWrapper.eq("JRNL_NUM", params.getJrnlNum());
        }

        TPayJrn tPayJrn = null;
        try {
            tPayJrn = tPayJrnMapper.selectOne(queryWrapper);

            return tPayJrn;
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统异常");
        }
    }

    @Override
    public Boolean updatePayJrn(TPayJrn params) {
        log.debug("更新流水状态 +" + JSON.toJSONString(params));

        if (StringUtil.isNullOrEmpty(params.getStatus())) {
            throw new BusinessException("业务状态不能为空");
        }

        UpdateWrapper<TPayJrn> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("JRNL_NUM", params.getJrnlNum());
        if (params.getStatus().equals(OrderStatusEnum.SUCCESS.getOrderStatus())) {
            params.setStatus(SUCCESS.getCardInfoStatus());
        } else {
            params.setStatus(FAIL.getCardInfoStatus());
        }

        params.setTxnTm(new Date());

        // 更新流水表
        try {
            int update = tPayJrnMapper.update(params, updateWrapper);
            if (update != 1) {
                log.error("更新流水失败 + " + JSON.toJSONString(params));
                throw new BusinessException("更新流水失败");
            }

            return true;
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统异常");
        }
    }

    @Override
    public TAccnoInfo findAcct(FindAcctDTO params) {
        log.debug("查找账户 +" + JSON.toJSONString(params));
        if (StringUtil.isNullOrEmpty(params.getPayCard())) {
            throw new BusinessException("卡号不能为空");
        }

        TMembershipInfo tMembershipInfo = null;
        QueryWrapper<TMembershipInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("CARD_NO", params.getPayCard());
        try {
            tMembershipInfo = tMembershipInfoMapper.selectOne(queryWrapper);
            if (tMembershipInfo == null || StringUtil.isNullOrEmpty(tMembershipInfo.getAccount())) {
                log.error("查找会员卡错误 + " + JSON.toJSONString(params));
                throw new BusinessException("查找会员卡错误");
            }

            if (!tMembershipInfo.getStatus().equals("0")) {
                log.error("会员卡状态错误 + " + JSON.toJSONString(params));
                throw new BusinessException("会员卡状态错误");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统异常");
        }

        TAccnoInfo tAccnoInfo = null;
        QueryWrapper<TAccnoInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ACCOUNT", tMembershipInfo.getAccount());
        try {
            tAccnoInfo = tAccnoInfoMapper.selectOne(queryWrapper1);

            if (tAccnoInfo == null) {
                log.error("查找账户错误 + " + JSON.toJSONString(params));
                throw new BusinessException("查找账户错误");
            }

            return tAccnoInfo;
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统错误");
        }
    }

    @Override
    public Boolean updateAcctAmt(TAccnoInfo params, BigDecimal txnAmt,TPayJrn tPayJrn) {
        UpdateWrapper<TAccnoInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ACCOUNT", params.getAccount());
        updateWrapper.eq("AVAIL_BAL", params.getAvailBal());

        String mac;
        String mac1;
        try {
            mac = MacUtil.getMac(params.getProvId(), params.getAccount(), params.getAcctBal(), params.getAvailBal(), params.getFreezeAmt());
            log.debug(mac);
            mac1 = MacUtil.getMac(params.getProvId(), params.getAccount(), params.getAcctBal().add(txnAmt), params.getAvailBal().add(txnAmt), params.getFreezeAmt());
            log.debug(mac1);
            if (StringUtil.isNullOrEmpty(mac)) {
                throw new BusinessException("mac为空");
            }
            if (StringUtil.isNullOrEmpty(mac1)) {
                throw new BusinessException("mac为空");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统错误");
        }

        updateWrapper.eq("MAC", mac);
        TAccnoInfo newAccnoInfo = new TAccnoInfo();

        try {
            TAccnoChangeDetails tAccnoChangeDetails = new TAccnoChangeDetails();

            // 付款方
            if (params.getAccount().equals(tPayJrn.getPayAcct())) {
                tAccnoChangeDetails.setCardNo(tPayJrn.getPayCard());
                tAccnoChangeDetails.setProvId(tPayJrn.getPayerNo());
            } else if (params.getAccount().equals(tPayJrn.getPayeeAcct())) {
                tAccnoChangeDetails.setCardNo(tPayJrn.getPayeeCard());
                tAccnoChangeDetails.setProvId(tPayJrn.getPayeeNum());
            } else {
                log.error("没有找到匹配的账号 + " + JSON.toJSONString(params) );
                throw new BusinessException("没有找到匹配的账号");
            }

            tAccnoChangeDetails.setJrnlNum(tPayJrn.getJrnlNum());
            tAccnoChangeDetails.setTxnType(tPayJrn.getTxnType());
            tAccnoChangeDetails.setTransMd(tPayJrn.getTransMd());

            tAccnoChangeDetails.setBeforeAmt(params.getAvailBal());
            tAccnoChangeDetails.setAfterAmt(params.getAvailBal().add(txnAmt));
            tAccnoChangeDetails.setTxnAmt(tPayJrn.getTxnAmt());
            tAccnoChangeDetails.setTxnTm(tPayJrn.getTxnTm());
            tAccnoChangeDetails.setSummary(tPayJrn.getRemark());
            tAccnoChangeDetails.setOperId(tPayJrn.getOperId());
            tAccnoChangeDetails.setCreatTime(new Date());
            tAccnoChangeDetails.setUpTime(new Date());

            // 账户钱多了借+
            // 账户钱少了贷+
            if (txnAmt.compareTo(new BigDecimal("0.00")) > 0) {
                newAccnoInfo.setDdebitAmt(params.getDdebitAmt().add(txnAmt.abs()));
                tAccnoChangeDetails.setIncomeTp("0");
            } else {
                newAccnoInfo.setDcreditAmt(params.getDcreditAmt().add(txnAmt.abs()));
                tAccnoChangeDetails.setIncomeTp("1");
            }

            newAccnoInfo.setAvailBal(params.getAvailBal().add(txnAmt));
            newAccnoInfo.setAcctBal(params.getAcctBal().add(txnAmt));
            newAccnoInfo.setUpTime(new Date());
            newAccnoInfo.setMac(mac1);

            int update = tAccnoInfoMapper.update(newAccnoInfo, updateWrapper);
            if (update != 1) {
                log.error("账户余额更新错误 + " + JSON.toJSONString(newAccnoInfo));
                throw new BusinessException("账户余额更新错误");
            }

            int insert = tAccnoChangeDetailsMapper.insert(tAccnoChangeDetails);
            if (insert != 1) {
                log.error("账户明细新增失败 + " + JSON.toJSONString(tAccnoChangeDetails));
                throw new BusinessException("账户明细新增失败");
            }

            return true;
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统错误");
        }
    }

    @Override
    public Boolean checkAcctAmt(TAccnoInfo params, BigDecimal txnAmt) {
        try {
            if (params.getAvailBal().compareTo(txnAmt) < 0) {
                log.error("账户余额不足 + " + JSON.toJSONString(params));
                throw new BusinessException("账户余额不足");
            }

            return true;
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统异常");
        }
    }

    @Override
    public TAccnoInfo findAcctByAcct(String acct) {
        TAccnoInfo tAccnoInfo = null;
        QueryWrapper<TAccnoInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ACCOUNT", acct);
        try {
            tAccnoInfo = tAccnoInfoMapper.selectOne(queryWrapper1);

            if (tAccnoInfo == null) {
                log.error("查找账户错误 + " + JSON.toJSONString(acct));
                throw new BusinessException("查找账户错误");
            }

            return tAccnoInfo;
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            log.error("系统异常", e);
            throw new BusinessException("系统错误");
        }
    }

    /**
     * 支付回调方法，用于支付前后的状态控制
     *
     * @author zhaoshihao
     * @date 2021/8/28
     */
    public void payInc(PayJrn payJrn) {

        log.info("支付回调，参数：" + JSON.toJSONString(payJrn));

        try {
            //去中间状态
            if (payJrn.getStatus().equals(FAIL.getCardInfoStatus())) {
                payJrn.setStatus(NOT_PAY.getCardInfoStatus());
            }
            if (payJrn.getStatus().equals(WIHDRAW_FAIL.getCardInfoStatus())) {
                payJrn.setStatus(SUCCESS.getCardInfoStatus());
            }
            if (payJrn.getStatus().equals(NOT_PAY.getCardInfoStatus())){
                payJrn.setTransMd("");
            }
            if (BILL.getTxnType().equals(payJrn.getTxnType()) || WITHDRAW.getTxnType().equals(payJrn.getTxnType())) {
                log.info("更新账单支付状态");
                payIncDao.updateBillStatus(payJrn);
            }
            if (MCARD.getTxnType().equals(payJrn.getTxnType()) || MCARD_WITHDRAW.getTxnType().equals(payJrn.getTxnType())) {
                log.info("更新月卡账单支付状态");
                payIncDao.updateMCardStatus(payJrn);
                //支付成功时，更新月卡生效时间
                if (payJrn.getStatus().equals(SUCCESS.getCardInfoStatus())){
                    log.info("月卡支付成功，更新月卡生效时间");
                    VechicleMonthPayDetails payDetails = payIncDao.selectMCardPayDetail(payJrn);
                    //续费时若续费开始日期在月卡结束日期之前，则不更新月卡开始时间
                    MCardInfo mcardInfo = payIncDao.getMcardInfo(payDetails);
                    if (payDetails.getPayStDt().compareTo(mcardInfo.getDueDt())<=0){
                        mcardInfo.setStartDt(null);
                    }else {
                        mcardInfo.setStartDt(payDetails.getPayStDt());
                    }
                    mcardInfo.setMcardNum(payDetails.getMcardNum());
                    mcardInfo.setStatus(payDetails.getPaymentSt());
                    mcardInfo.setDueDt(payDetails.getPayDeadline());
                    mcardInfo.setOperId(payJrn.getOperId());
                    payIncDao.updateMCardInfo(mcardInfo);
                }
                if (payJrn.getStatus().equals(WIHDRAW_SUCCESS.getCardInfoStatus())){
                    log.info("月卡退款成功，更新月卡状态为作废");
                    VechicleMonthPayDetails payDetails = payIncDao.selectMCardPayDetail(payJrn);
                    MCardInfo mcardInfo = new MCardInfo();
                    mcardInfo.setMcardNum(payDetails.getMcardNum());
                    mcardInfo.setStatus("8");
                    mcardInfo.setOperId(payJrn.getOperId());
                    payIncDao.updateMCardInfo(mcardInfo);
                }
            }
            if (ENTRYFEENTR.getTxnType().equals(payJrn.getTxnType()) || CARFEE_PRE.getTxnType().equals(payJrn.getTxnType())) {
                log.info("更新进场支付状态");
                payIncDao.updateInStatus(payJrn);
            }
            if (CARFEE.getTxnType().equals(payJrn.getTxnType())
                    || IN_WITHDAW.getTxnType().equals(payJrn.getTxnType())
                    || ENTRYFEENTR_PRE.getTxnType().equals(payJrn.getTxnType())) {
                if (payJrn.getStatus().equals("0")) {
                    return;
                }
                log.info("更新出场支付状态");
                payIncDao.updateOutStatus(payJrn);
            }
        } catch (Exception e) {
            log.error("更新支付前后账单状态失败", e);
            throw new BusinessException("系统错误");
        }
    }
}
