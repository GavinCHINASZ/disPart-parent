package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.config.BusinessException;
import com.dispart.dao.IPayIncDao;
import com.dispart.dao.TPayJrnMapper;
import com.dispart.dto.busineCommon.DISP20210333InDto;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.FindPayJrnDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.enums.BaseEnum;
import com.dispart.enums.PayStatusEnum;
import com.dispart.model.PayJrn;
import com.dispart.model.businessCommon.CardInfoStatusEnum;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.result.Result;
import com.dispart.service.*;
import com.dispart.vo.busineCommon.TPushNotesVo;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;
import static com.dispart.model.businessCommon.TransMdEnum.*;
import static com.dispart.model.businessCommon.TransMdEnum.CHONGZHENG;
import static com.dispart.model.businessCommon.TxnTypeEnum.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@Slf4j
public class AllOrderServiceImpl implements AllOrderService {

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private PayService payService;

    @Autowired
    private CashOutService cashOutService;

    @Autowired
    private WithDrawService withDrawService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private CallGeTui callGeTui;

    @Autowired
    private TPayJrnMapper payJrnMapper;

    @Transactional
    @Override
    public Result<Object> insertPayJrn(InsertPayJrnDTO params) {
        log.debug("新增流水 +" + JSON.toJSONString(params));
        if (StringUtil.isNullOrEmpty(params.getTxnType())) {
            throw new BusinessException("交易类型不能为空");
        }

        if (StringUtil.isNullOrEmpty(params.getTransMd())) {
            throw new BusinessException("交易方式不能为空");
        }

        if (params.getTxnAmt() == null || params.getTxnAmt().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("交易金额异常");
        }

        if (StringUtil.isNullOrEmpty(params.getPayerNo())) {
            throw new BusinessException("付款人编号不能为空");
        }


//        if (StringUtil.isNullOrEmpty(params.getPayeeAcct())) {
//            throw new BusinessException("收款人账号不能为空");
//        }

//        if (params.getTxnType().equals(CHONGZHENG.getTxnType())) {
//            Result<Object> chongzheng = chargeService.chongzheng(params);
//            return chongzheng;
//        }

        // 设置备注
        params.setRemark(TxnTypeEnum.getDesc(params.getTxnType()) + "-" + TransMdEnum.getDesc(params.getTransMd()));

        // 冲正
        if (params.getTransMd().equals(CHONGZHENG.getTransMDStatus())) {
            Result<Object> chongzheng = chargeService.chongzheng(params);

            return chongzheng;
        }

        // 代充值
        if (params.getTxnType().equals(ENTRYFEENTR_PRE.getTxnType())) {
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result<Object> objectResult = chargeService.chargeByPrePay(params);

                // 需要推送
                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("充值");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                }catch (Exception e) {
                    log.error("系统错误" + e);
                    // 推送结果有问题不管，直接返回成功
                    return objectResult;
                }
                return objectResult;
            }
        }

        // 充值
        if (params.getTxnType().equals(CHARGE.getTxnType())) {
            // hsb充值
            if (params.getTransMd().equals(HSB.getTransMDStatus())) {
                Result<Object> objectResult = chargeService.chargeByHSB(params);

                // 需要推送
//                try {
//                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
//                    tPushNotesVo.setBusineNo(params.getBusinessNo());
//                    tPushNotesVo.setProvId(params.getPayerNo());
//                    tPushNotesVo.setParameterType(3);
//                    ArrayList<String> arrayList = new ArrayList<>();
//                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
//                    arrayList.add("充值");
//                    arrayList.add(params.getTxnAmt().toString());
//                    tPushNotesVo.setParameterList(arrayList);
//                    callGeTui.callGeTui(tPushNotesVo);
//                }catch (Exception e) {
//                    log.error("系统错误" + e);
//                    // 推送结果有问题不管，直接返回成功
//                    return objectResult;
//                }

                // 只是生成了主订单,不需要推送
                return objectResult;
            }

//            if (params.getTransMd().equals(PRE_PAY.getTransMDStatus())) {
//                Result<Object> objectResult = chargeService.chargeByPrePay(params);
//
//                // 需要推送
//                try {
//                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
//                    tPushNotesVo.setBusineNo(params.getBusinessNo());
//                    tPushNotesVo.setProvId(params.getPayerNo());
//                    tPushNotesVo.setParameterType(3);
//                    ArrayList<String> arrayList = new ArrayList<>();
//                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
//                    arrayList.add("充值");
//                    arrayList.add(params.getTxnAmt().toString());
//                    tPushNotesVo.setParameterList(arrayList);
//                    callGeTui.callGeTui(tPushNotesVo);
//                }catch (Exception e) {
//                    log.error("系统错误" + e);
//                    // 推送结果有问题不管，直接返回成功
//                    return objectResult;
//                }
//
//                return objectResult;
//            }

            // 其他充值
            // 其他充值
            if (params.getTransMd().equals(OTHER.getTransMDStatus())) {
                Result<Object> objectResult = chargeService.chargeByCash(params);

                // 需要推送
                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("充值");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                }catch (Exception e) {
                    log.error("系统错误" + e);
                    // 推送结果有问题不管，直接返回成功
                    return objectResult;
                }

                return objectResult;
            }

            // 现金充值
            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                Result<Object> objectResult = chargeService.chargeByCash(params);

                // 需要推送
                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("充值");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                }catch (Exception e) {
                    log.error("系统错误" + e);
                    // 推送结果有问题不管，直接返回成功
                    return objectResult;
                }

                return objectResult;
            }

            // pos充值 pos记得主动调用通知噢
            if (params.getTransMd().equals(POS_CARD.getTransMDStatus())) {
                params.setRemark("充值/POS银行卡");
                Result<Object> objectResult = chargeService.chargeByPos(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("充值");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    return objectResult;
                }

                return objectResult;
            }

            // pos充值 pos记得主动调用通知噢
            if (params.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
                params.setRemark("充值/pos扫码");
                Result<Object> objectResult = chargeService.chargeByPos(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("充值");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    return objectResult;
                }

                return objectResult;
            }
        }

        // 进场费
        if (params.getTxnType().equals(ENTRYFEENTR.getTxnType())) {
            // hsb缴费
            if (params.getTransMd().equals(HSB.getTransMDStatus())) {
                return payService.payByHSB(params);
            }

            // 现金缴费
            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                return payService.payByCash(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_CARD.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // 一卡通缴费
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = payService.payByCard(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayCard().substring(params.getPayCard().length() - 6));
                    arrayList.add("缴费");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    return result;
                }

                return result;
            }

        }

        //出场费
        if (params.getTxnType().equals(CARFEE.getTxnType())) {
            // hsb缴费
            if (params.getTransMd().equals(HSB.getTransMDStatus())) {
                return payService.payByHSB(params);
            }

            // 现金缴费
            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                return payService.payByCash(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_CARD.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // 一卡通缴费
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = payService.payByCard(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayCard().substring(params.getPayCard().length() - 6));
                    arrayList.add("缴费");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    throw new RuntimeException(e);
                }

                return result;
            }

        }

        // 账单费
        if (params.getTxnType().equals(BILL.getTxnType()) ) {
            // hsb缴费
            if (params.getTransMd().equals(HSB.getTransMDStatus())) {
                return payService.payByHSB(params);
            }

            // 现金缴费
            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                return payService.payByCash(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_CARD.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // 一卡通缴费
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = payService.payByCard(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayCard().substring(params.getPayCard().length() - 6));
                    arrayList.add("缴费");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    throw new RuntimeException(e);
                }

                return result;
            }
        }

        // 月卡账单
        if (params.getTxnType().equals(MCARD.getTxnType()) ) {
            // hsb缴费
            if (params.getTransMd().equals(HSB.getTransMDStatus())) {
                return payService.payByHSB(params);
            }

            // 现金缴费
            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                return payService.payByCash(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_CARD.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // pos缴费
            if (params.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
                return payService.payByPos(params);
            }

            // 一卡通缴费
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = payService.payByCard(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayCard().substring(params.getPayCard().length() - 6));
                    arrayList.add("缴费");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    throw new RuntimeException(e);
                }

                return result;
            }
        }

        // 供应商补贴发放
        if (params.getTxnType().equals(SUPPLY_SUBSIDY.getTxnType())) {
            // 补贴发放
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = payService.subsidy(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("补贴发放");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    return result;
                }

                return result;
            }
        }

        // 供应商撤回补贴
        if (params.getTxnType().equals(SUPPLY_CALCELSUBSIDY.getTxnType())) {

            // card
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = withDrawService.withdrawMoney(params);
                return result;
            }
        }

        // 采购商补贴发放
        if (params.getTxnType().equals(PURCH_SUBSIDY.getTxnType())) {
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                // 补贴发放
                if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                    Result result = payService.subsidy(params);

                    try {
                        TPushNotesVo tPushNotesVo = new TPushNotesVo();
                        tPushNotesVo.setBusineNo(params.getBusinessNo());
                        tPushNotesVo.setProvId(params.getPayerNo());
                        tPushNotesVo.setParameterType(3);
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                        arrayList.add("补贴发放");
                        arrayList.add(params.getTxnAmt().toString());
                        tPushNotesVo.setParameterList(arrayList);
                        callGeTui.callGeTui(tPushNotesVo);
                    } catch (Exception e){
                        log.error("系统错误" + e);
                        return result;
                    }

                    return result;
                }
            }
        }

        // 采购商撤回补贴
        if (params.getTxnType().equals(PURCH_CALCELSUBSIDY.getTxnType())) {
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result result = withDrawService.withdrawMoney(params);
                return result;
            }
        }

        // 账单退款或者进场退款
        if (params.getTxnType().equals(WITHDRAW.getTxnType()) || params.getTxnType().equals(IN_WITHDAW.getTxnType()) || params.getTxnType().equals(MCARD_WITHDRAW.getTxnType()) ||
        params.getTxnType().equals(CARFEE_PRE.getTxnType())) {

            // hsb退款
            if (params.getTransMd().equals(HSB.getTransMDStatus())) {
                return withDrawService.withdrawByHSB(params);
            }

            // 现金退款
            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                return withDrawService.withdrawByCash(params);
            }

            // pos退款
            if (params.getTransMd().equals(POS_CARD.getTransMDStatus())) {
                return withDrawService.withdrawByPos(params);
            }

            // pos退款
            if (params.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
                return withDrawService.withdrawByPos(params);
            }

            // card
            if (params.getTransMd().equals(CARD.getTransMDStatus())) {
                Result<Object> result = withDrawService.withdrawByCard(params);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayeeCard().substring(params.getPayeeCard().length() - 6));
                    arrayList.add("退费");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    return result;
                }

                return result;
            }
        }

        // 提现
        if (params.getTxnType().equals(CASHOUT.getTxnType())) {

            if (params.getTransMd().equals(CASH.getTransMDStatus())) {
                Result<Object> cashout = cashOutService.cashoutByCash(params);
                return cashout;
            }

            if (params.getTransMd().equals(BANK.getTransMDStatus())) {
                Result<Object> cashout = cashOutService.cashout(params);
                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(params.getBusinessNo());
                    tPushNotesVo.setProvId(params.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(params.getPayCard().substring(params.getPayCard().length() - 6));
                    arrayList.add("提现");
                    arrayList.add(params.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    return cashout;
                }

                return cashout;
            }
        }

        return Result.build(-333,"没有对应的交易类型");
    }

    @Transactional
    @Override
    public Result<Object> updateAcctAmt(FindAcctDTO params) {
        log.debug("更新流水及记帐 +" + JSON.toJSONString(params));
//        if (StringUtil.isNullOrEmpty(params.getMainOrderId())) {
//            throw new BusinessException("主订单编号不能为空");
//        }

        FindPayJrnDTO findPayJrnDTO = new FindPayJrnDTO();

        // 根据主订单ID查找原来的流水
        findPayJrnDTO.setMainOrderId(params.getMainOrderId());
        findPayJrnDTO.setJrnlNum(params.getJrnlNum());
        TPayJrn payJrn = baseService.findPayJrn(findPayJrnDTO);

        if (payJrn == null) {
            log.error("没有查到主订单id对应的流水" + JSON.toJSONString(params));
            return Result.ok();
//            throw new BusinessException("没查询到主订单编号对应的流水记录");
        }

        // 不是成功的全部先改为失败
        if (!params.getStatus().equals("2")) {
            payJrn.setStatus(CardInfoStatusEnum.FAIL.getCardInfoStatus());
        }

        if (params.getStatus().equals("00") || params.getStatus().equals("2")) {
            payJrn.setStatus(SUCCESS.getCardInfoStatus());
        }

        if (params.getStatus().equals("02") || params.getStatus().equals("03")) {
            payJrn.setStatus(WIHDRAW_WAIT.getCardInfoStatus());
        }

//        // 更新流水表
        baseService.updatePayJrn(payJrn);
//
        //更新业务账单支付状态
        PayJrn pay = new PayJrn();
        BeanUtils.copyProperties(payJrn,pay);
        baseService.payInc(pay);

        // 订单成功的情况才需要记账
        if(params.getStatus().equals("2") || params.getStatus().equals("00")) {
            // HSB线上充值回调
            if (payJrn.getTxnType().equals(CHARGE.getTxnType())) {
                Result<Object> objectResult = chargeService.callBackByHSBCharge(payJrn);

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(payJrn.getBusinessNo());
                    tPushNotesVo.setProvId(payJrn.getPayerNo());
                    tPushNotesVo.setParameterType(3);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(payJrn.getPayeeCard().substring(payJrn.getPayeeCard().length() - 6));
                    arrayList.add("充值");
                    arrayList.add(payJrn.getTxnAmt().toString());
                    tPushNotesVo.setParameterList(arrayList);
                    callGeTui.callGeTui(tPushNotesVo);
                } catch (Exception e){
                    log.error("系统错误" + e);
                    throw new RuntimeException(e);
                }

                return objectResult;
            }

            if (payJrn.getTxnType().equals(CARFEE.getTxnType()) || payJrn.getTxnType().equals(ENTRYFEENTR.getTxnType())
            || payJrn.getTxnType().equals(BILL.getTxnType()) || payJrn.getTxnType().equals(MCARD.getTxnType())) {
                return payService.callBackByHSBPay(payJrn);
            }

            // 退款回调
            if (payJrn.getTxnType().equals(WITHDRAW.getTxnType())) {
                Result<Object> objectResult = withDrawService.callBackByHSBWithdraw(payJrn);

//                TPushNotesVo tPushNotesVo = new TPushNotesVo();
//                tPushNotesVo.setBusineNo(payJrn.getBusinessNo());
//                tPushNotesVo.setProvId(payJrn.getPayerNo());
//                tPushNotesVo.setParameterType(3);
//                ArrayList<String> arrayList = new ArrayList<>();
//                arrayList.add(payJrn.getPayeeCard().substring(payJrn.getPayeeCard().length() - 6));
//                arrayList.add("退款");
//                arrayList.add(payJrn.getTxnAmt().toString());
//                tPushNotesVo.setParameterList(arrayList);
//                callGeTui.callGeTui(tPushNotesVo);

                return objectResult;
            }

        }

        return Result.ok();
    }

    @Override
    public Result updatePayStatus(DISP20210333InDto inDto) {
        log.info("未知状态修改，传入参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getJrnlNum())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"交易流水号不能为空");
        }
        if (StringUtils.isEmpty(inDto.getStatus())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"支付状态不能为空");
        }
        if (StringUtils.isEmpty(inDto.getOperId())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"操作员ID不能为空");
        }
        if (!PayStatusEnum.SUCCESS.getCode().equals(inDto.getStatus()) && !PayStatusEnum.FAIL.getCode().equals(inDto.getStatus())){
            return Result.build(BaseEnum.INVALID_PARAM.getCode(),"支付状态不合法");
        }

        try{
            payJrnMapper.updatePayStatus(inDto);
        }catch (Exception e){
            log.error("更新支付状态异常");
            throw new RuntimeException(e);
        }
        log.info("未知状态修改结束");
        return Result.ok();
    }
}
