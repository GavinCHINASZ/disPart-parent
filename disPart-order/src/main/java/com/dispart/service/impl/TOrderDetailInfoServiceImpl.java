package com.dispart.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dispart.dao.*;
import com.dispart.dao.mapper.TFileInfoMapper;
import com.dispart.dao.mapper.TPartModeTypeMapper;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.dto.orderdto.*;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.model.order.OrderStatusEnum;
import com.dispart.model.order.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultToHSBOut;
import com.dispart.service.AutoTransction;
import com.dispart.service.PlaySignService;
import com.dispart.model.order.TCardReturnTask;
import com.dispart.tmp.AddOrdersByParams;
import com.dispart.tmp.TAccnoReverseApply;
import com.dispart.tmp.TVechicleProcurer;
import com.dispart.util.IdWorker;
import com.dispart.vo.order.*;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.io.*;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.service.TOrderDetailInfoService;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import static com.dispart.model.order.OrderStatusEnum.*;
import static com.dispart.result.ResultCodeOrderEnum.*;

import static com.dispart.model.businessCommon.TxnTypeEnum.*;
import static com.dispart.model.businessCommon.TransMdEnum.*;

@Slf4j
@Service
public class TOrderDetailInfoServiceImpl extends ServiceImpl<TOrderDetailInfoMapper, TOrderDetailInfo> implements TOrderDetailInfoService {

    @Value("${pushUrl}")
    private String SERVER_URL;

    @Value("${wlyAcct}")
    private String wlyAcct;

    @Value("${wlyCard}")
    private String wlyCard;

    @Autowired
    private PlaySignService playSignService;

    @Autowired
    private AutoTransction autoTransction;

    @Autowired
    @Qualifier("restTemplate1")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Autowired
    private TDiscountsUserMapper tDiscountsUserMapper;

    @Autowired
    private TOrderInfoMapper tOrderInfoMapper;

    @Autowired
    private TVechicleProcurerMapper tVechicleProcurerMapper;

    @Autowired
    private TPayJrnMapper tPayJrnMapper;

    @Autowired
    private TOrderGoodsInfoMapper tOrderGoodsInfoMapper;;

    @Autowired
    private TProductInventoryInfoMapper tProductInventoryInfoMapper;

    @Autowired
    private TOrderRelevancyInfoMapper tOrderRelevancyInfoMapper;

    @Autowired
    private TPalceOrderTypeMapper tPalceOrderTypeMapper;

    @Autowired
    private TApportionDetailsInfoMapper tApportionDetailsInfoMapper;

    @Autowired
    private TCardReturnTaskMapper tCardReturnTaskMapper;

    @Autowired
    private TApportionSumInfoMapper tApportionSumInfoMapper;

    @Autowired
    private TFileInfoMapper tFileInfoMapper;

    @Autowired
    private TReconciliationDetailsInfoMapper tReconciliationDetailsInfoMapper;

    @Autowired
    private TWithholdDetailsInfoMapper tWithholdDetailsInfoMapper;

    @Autowired
    private TDictDataMapper tDictDataMapper;

    @Autowired
    private TPartModeTypeMapper tPartModeTypeMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager1;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TTraceIdDao tTraceIdDao;

    @Autowired
    private TAccnoReverseApplyMapper tAccnoReverseApplyMapper;

    @Transactional
    @Override
    public Result<Object> checkhasOrder(CheckHasOrderVo checkHasOrderVo) {
        log.debug("查重复订单 + " + JSON.toJSONString(checkHasOrderVo));
        if (StringUtil.isNullOrEmpty(checkHasOrderVo.getPrdctId())) {
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "prdctId不能为空");
        }

        if (StringUtil.isNullOrEmpty(checkHasOrderVo.getUserId())) {
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "userId不能为空");
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 半小时前的时间
            Date beforeDate = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
            String beforeTime = simpleDateFormat.format(beforeDate);

            // 现在的时间
            String nowTime = simpleDateFormat.format(new Date());

            QueryWrapper<TOrderDetailInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PURCH_ID", checkHasOrderVo.getUserId());
            queryWrapper.eq("PRDCT_ID", checkHasOrderVo.getPrdctId());
            queryWrapper.eq("ORDER_ST", OrderStatusEnum.UNPAYS.getOrderStatus());
            queryWrapper.apply("date_format(ORDER_TM,'%Y-%m-%d %H:%i:%s') >= date_format('" + beforeTime + "','%Y-%m-%d %H:%i:%s')");
            queryWrapper.apply("date_format(ORDER_TM,'%Y-%m-%d %H:%i:%s') <= date_format('" + nowTime + "','%Y-%m-%d %H:%i:%s')");

            List<TOrderDetailInfo> tOrderDetailInfos = baseMapper.selectList(queryWrapper);
            if (tOrderDetailInfos.size() == 0) {
                return Result.ok();
            } else {
                log.info("有重复订单");
                return Result.build(HAS_ORDER_IN_HALF_HOUR.getCode(), HAS_ORDER_IN_HALF_HOUR.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Result<Object> payOrdersBySelect(Request<AddOrdersByParam> addOrdersByParams) {
        log.debug("充值接口" + JSON.toJSONString(addOrdersByParams));

        AddOrdersByParam addOrdersByParam = addOrdersByParams.getBody();
        addOrdersByParam.setOperId(addOrdersByParams.getHead().getOperator());
        addOrdersByParam.setChanlNo(addOrdersByParams.getHead().getChanlNo());

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getTransMd())) {
            log.error("交易方式不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易方式不能为空");
        }

        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(new BigDecimal("5000.00")) > 0 || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
            log.error("金额不能大于5000" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额不能大于5000");
        }

//        if (!addOrdersByParam.getTxnType().equals(CHARGE.getTxnType())) {
//            log.error("交易类型不为充值" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型不为充值");
//        }

        if (addOrdersByParam.getTransMd().equals(HSB.getTransMDStatus())) {
            Result<Object> objectResult = autoTransction.commitToHSB(addOrdersByParam);

            if (200 != objectResult.getCode()) {
                return objectResult;
            }

            Result<Object> objectResult1 = autoTransction.returnToHSB((HashMap) objectResult.getData());

            if (200 != objectResult1.getCode()) {
                return objectResult1;
            }

            return autoTransction.thirdStep((HashMap) objectResult.getData());
        } else if (addOrdersByParam.getTransMd().equals(POS_CARD.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(CASH.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(OTHER.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else {
//            log.error("交易类型错误" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型错误");
            return payOrdersByCash(addOrdersByParam);
        }
    }

    @Transactional
    @Override
    public Result<Object> payOrdersBySelect3(Request<AddOrdersByParam> addOrdersByParams) {
        log.debug("退款接口" + JSON.toJSONString(addOrdersByParams));

        AddOrdersByParam addOrdersByParam = addOrdersByParams.getBody();
        addOrdersByParam.setOperId(addOrdersByParams.getHead().getOperator());
        addOrdersByParam.setChanlNo(addOrdersByParams.getHead().getChanlNo());


        if (StringUtil.isNullOrEmpty(addOrdersByParam.getBusinessNo())) {
            log.error("业务号不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "业务号不能为空");
        }

        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(new BigDecimal(5000.00)) > 0 || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
            log.error("金额不能大于5000" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额不能大于5000");
        }

        if (addOrdersByParam.getTxnAmt() == null) {
            log.error("退款金额不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "退款金额不能为空");
        }

        try {
            // 拿以前的流水记录
            QueryWrapper<TPayJrn> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("BUSINESS_NO", addOrdersByParam.getBusinessNo());
            queryWrapper.eq("status","2");

            if (!StringUtil.isNullOrEmpty(addOrdersByParam.getTxnType())) {
                queryWrapper.eq("TXN_TYPE",addOrdersByParam.getTxnType());
            }

            TPayJrn tPayJrn = tPayJrnMapper.selectOne(queryWrapper);

            // 查出来的tPayjrn为空
            if (tPayJrn == null) {
                log.error("tPayJrn查询为空" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的退款记录");
            }

            if (tPayJrn.getTxnType().equals(ENTRYFEENTR.getTxnType())) {
                String status = baseMapper.getVechicleProurerInStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(CARFEE.getTxnType())) {
                String status = baseMapper.getVechicleProurerOutStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(BILL.getTxnType())) {
                String status = baseMapper.getBillStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(MCARD.getTxnType())) {
                String status = baseMapper.getMCardStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(SUPPLY_SUBSIDY.getTxnType())) {

            } else if (tPayJrn.getTxnType().equals(PURCH_SUBSIDY.getTxnType())) {

            } else {
                log.error("交易类型不为缴费" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型不为缴费");
            }

            String txnType = addOrdersByParam.getNewTxnType();
            if (tPayJrn.getTxnType().equals(BILL.getTxnType())) {
                addOrdersByParam.setTxnType(WITHDRAW.getTxnType());
            } else if (tPayJrn.getTxnType().equals(SUPPLY_SUBSIDY.getTxnType())) {
                addOrdersByParam.setTxnType(SUPPLY_CALCELSUBSIDY.getTxnType());
            } else if (tPayJrn.getTxnType().equals(PURCH_SUBSIDY.getTxnType())) {
                addOrdersByParam.setTxnType(PURCH_CALCELSUBSIDY.getTxnType());
            } else if (tPayJrn.getTxnType().equals(MCARD.getTxnType())){
                addOrdersByParam.setTxnType(MCARD_WITHDRAW.getTxnType());
            } else {
                addOrdersByParam.setTxnType(IN_WITHDAW.getTxnType());
            }

            if (!StringUtil.isNullOrEmpty(txnType) && txnType.equals(CARFEE_PRE.getTxnType())) {
                addOrdersByParam.setTxnType(CARFEE_PRE.getTxnType());
            }

            addOrdersByParam.setTransMd(tPayJrn.getTransMd());
            addOrdersByParam.setPayerNo(tPayJrn.getPayerNo());

            if (addOrdersByParam.getTxnAmt().compareTo(tPayJrn.getTxnAmt()) > 0) {
                log.error("退款金额大于账单金额" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "退款金额大于账单金额");
            }

            addOrdersByParam.setTxnAmt(addOrdersByParam.getTxnAmt());
            addOrdersByParam.setBusinessNo(tPayJrn.getBusinessNo());
            addOrdersByParam.setPayeeNum(tPayJrn.getPayeeNum());

            // 付款账号改为收款帐号
            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayAcct())) {
                addOrdersByParam.setPayeeAcct(tPayJrn.getPayAcct());
            }

            // 收款帐号改为付款账号
            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayeeAcct())) {
                addOrdersByParam.setPayAcct(tPayJrn.getPayeeAcct());
            }
            // 付款卡号改为收款卡号
            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayCard())) {
                addOrdersByParam.setPayeeCard(tPayJrn.getPayCard());
            }

            // 收款帐号改为付款账号
            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayeeCard())) {
                addOrdersByParam.setPayCard(tPayJrn.getPayeeCard());
            }

            if (!StringUtil.isNullOrEmpty(tPayJrn.getMainOrderId())) {
                addOrdersByParam.setRefundMainOrderId(tPayJrn.getMainOrderId());
            }

            if (addOrdersByParam.getTransMd().equals(HSB.getTransMDStatus())) {
                ArrayList<TOrderDetailInfo> list = new ArrayList<>();
                TOrderDetailInfo tOrderDetailInfo = new TOrderDetailInfo();
                tOrderDetailInfo.setPurchId(tPayJrn.getPayerNo());
                list.add(tOrderDetailInfo);
                addOrdersByParam.setOrderList(list);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 设置
//        addOrdersByParam.setPayAcct(wlyAcct);

        if (addOrdersByParam.getTransMd().equals(HSB.getTransMDStatus())) {

            Result<Object> objectResult = autoTransction.commitToHSB(addOrdersByParam);

            if (200 != objectResult.getCode()) {
                return objectResult;
            }

            HashMap map = (HashMap) objectResult.getData();
            ResultHSBCallBack result = (ResultHSBCallBack) map.get("result");
            log.info("返回的数据" + JSON.toJSONString(result));
            Result<Object> objectResult1 = autoTransction.withDrawToHSB((HashMap) objectResult.getData());

            if (200 != objectResult1.getCode()) {
                return objectResult1;
            }

            if (result.getTxnSt().equals("00") && result.getRefundSt().equals("01")) {
                return Result.build(-309,"退款失败");
            }

            return autoTransction.thirdStep((HashMap) objectResult.getData());

        }
        else if (addOrdersByParam.getTransMd().equals(POS_CARD.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(CASH.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(CARD.getTransMDStatus())) {
//            addOrdersByParam.setPayAcct(wlyAcct);
            return payOrdersByCard(addOrdersByParam);
        } else {
            log.error("交易类型错误" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型错误");
        }
    }

    @Override
    public Result<Object> payOrdersBySelect4(AddOrdersByParam addOrdersByParam) {
//                Result result = comfirmOrders();

        return null;
    }

    @Transactional
    @Override
    public Result<Object> payOrdersBySelect2(Request<AddOrdersByParam> addOrdersByParams) {
        log.debug("缴费接口" + JSON.toJSONString(addOrdersByParams));

        AddOrdersByParam addOrdersByParam = addOrdersByParams.getBody();
        addOrdersByParam.setOperId(addOrdersByParams.getHead().getOperator());
        addOrdersByParam.setChanlNo(addOrdersByParams.getHead().getChanlNo());

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getTransMd())) {
            log.error("交易方式不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易方式不能为空");
        }

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getBusinessNo())) {
            log.error("业务编号不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "业务编号不能为空");
        }

//        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(new BigDecimal(5000.00)) > 0 || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
//            log.error("金额不能大于5000" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额不能大于5000");
//        }

        if (addOrdersByParam.getTxnType().equals(SUPPLY_SUBSIDY.getTxnType()) || addOrdersByParam.getTxnType().equals(PURCH_SUBSIDY)) {
            if (addOrdersByParam.getTxnAmt() != null && addOrdersByParam.getTxnAmt().compareTo(new BigDecimal(5000.00)) > 0) {
                log.error("金额不能大于5000" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额不能大于5000");
            }
        }

//        if (!addOrdersByParam.getTxnType().equals(ENTRYFEENTR.getTxnType()) &&
//                !addOrdersByParam.getTxnType().equals(CARFEE.getTxnType()) &&
//                !addOrdersByParam.getTxnType().equals(BILL.getTxnType()) &&
//                !addOrdersByParam.getTxnType().equals(MCARD.getTxnType()) &&
//                !addOrdersByParam.getTxnType().equals(CALCELSUBSIDY.getTxnType())) {
//            log.error("交易类型不为缴费" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型不为缴费");
//        }


        try {
            if (addOrdersByParam.getTxnType().equals(ENTRYFEENTR.getTxnType())) {
                String status = baseMapper.getVechicleProurerInStatus(addOrdersByParam.getBusinessNo());
                if (!"9".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (addOrdersByParam.getTxnType().equals(CARFEE.getTxnType())) {
                String status = baseMapper.getVechicleProurerOutStatus(addOrdersByParam.getBusinessNo());
                if ("2".equals(status)) {
                    log.warn("已缴出场费" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "已缴出场费");
                }
            } else if (addOrdersByParam.getTxnType().equals(BILL.getTxnType())) {
                String status = baseMapper.getBillStatus(addOrdersByParam.getBusinessNo());
                if (!"9".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (addOrdersByParam.getTxnType().equals(MCARD.getTxnType())) {
                String status = baseMapper.getMCardStatus(addOrdersByParam.getBusinessNo());
                if (!"9".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (addOrdersByParam.getTxnType().equals(SUPPLY_SUBSIDY.getTxnType())) {

            } else if (addOrdersByParam.getTxnType().equals(PURCH_SUBSIDY.getTxnType())) {

            } else {
                log.error("交易类型不为缴费" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型不为缴费");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        addOrdersByParam.setPayeeAcct(wlyAcct);

        if (addOrdersByParam.getTransMd().equals(HSB.getTransMDStatus())) {
            Result<Object> objectResult = autoTransction.commitToHSB(addOrdersByParam);

            if (200 != objectResult.getCode()) {
                return objectResult;
            }

            Result<Object> objectResult1 = autoTransction.returnToHSB((HashMap) objectResult.getData());

            if (200 != objectResult1.getCode()) {
                return objectResult1;
            }

            return autoTransction.thirdStep((HashMap) objectResult.getData());
        } else if (addOrdersByParam.getTransMd().equals(POS_CARD.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(POS_ERWEIMA.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(CASH.getTransMDStatus())) {
            return payOrdersByCash(addOrdersByParam);
        } else if (addOrdersByParam.getTransMd().equals(CARD.getTransMDStatus())) {
            addOrdersByParam.setPayeeAcct(wlyAcct);
            addOrdersByParam.setPayeeCard(wlyCard);
            return payOrdersByCard(addOrdersByParam);
        } else {
            log.error("交易类型错误" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型错误");
        }
    }

//    @Transactional
    @Override
    public Result<Object> payOrdersByCash(AddOrdersByParam addOrdersByParam) {
//        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayCard())) {
//            log.error("卡号不能为空" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "卡号不能为空");
//        }

        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
            log.error("交易金额异常 + " + JSON.toJSONString(addOrdersByParam));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易金额异常");
        }

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayerNo())) {
            log.error("付款人编号不能为空 + " + JSON.toJSONString(addOrdersByParam));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "付款人编号不能为空");
        }

//        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayeeAcct())) {
//            log.error("收款人账号不能为空 + " + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "收款人账号不能为空");
//        }

        try {
            InsertPayJrnDTO insertPayJrnDTO = new InsertPayJrnDTO();
            BeanUtils.copyProperties(addOrdersByParam, insertPayJrnDTO);
            Result<Object> result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210275", insertPayJrnDTO, Result.class);

            if (result1 != null && result1.getCode() == 200) {
                log.info("充值成功");
                return result1;
            } else {
                log.error("调用新增流水失败 + " + JSON.toJSONString(insertPayJrnDTO));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return result1;
            }
        } catch (Exception e) {
            log.error("充值出现异常", e);
            throw new RuntimeException(e);
        }
    }

//    @Transactional
    @Override
    public Result<Object> payOrdersByCard(AddOrdersByParam addOrdersByParam) {
//        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayCard())) {
//            log.error("付款卡号不能为空" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "付款卡号不能为空");
//        }

        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
            log.error("交易金额异常 + " + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易金额异常");
        }

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayerNo())) {
            log.error("付款人编号不能为空 + " + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "付款人编号不能为空");
        }

//        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayeeAcct())) {
//            log.error("收款人账号不能为空 + " + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "收款人账号不能为空");
//        }

        try {
            InsertPayJrnDTO insertPayJrnDTO = new InsertPayJrnDTO();
            BeanUtils.copyProperties(addOrdersByParam, insertPayJrnDTO);
            Result<Object> result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210275", insertPayJrnDTO, Result.class);

            if (result1 != null && result1.getCode() == 200) {
                return result1;
            } else {
                log.error("调用新增流水失败 + " + JSON.toJSONString(insertPayJrnDTO));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return result1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    @Override
    public Result<Object> withDrawByOffline(AddOrdersByParam addOrdersByParam) {
        try {
            // 根据交易类型更新状态状态与需不需要记帐
            LocalDateTime localDate = LocalDateTime.now();
            String yyyyMMddHHmmssSSS = localDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            IdWorker idWorker = new IdWorker("1", "1");
            long nextId = idWorker.nextId();

            WithDrawOrderByParam withDrawOrderByParam = new WithDrawOrderByParam();

            withDrawOrderByParam.setSndDtTm(yyyyMMddHHmmssSSS);
            withDrawOrderByParam.setSndTraceId(String.valueOf(nextId));
            withDrawOrderByParam.setMarketId(baseMapper.getMarketId());

            String paymentTraceId = baseMapper.getPaymentTraceId(addOrdersByParam.getBusinessNo());
            if (StringUtil.isNullOrEmpty(paymentTraceId)) {

            }

            withDrawOrderByParam.setRefNum(addOrdersByParam.getRefNum());
            withDrawOrderByParam.setRefundSt(addOrdersByParam.getRefundSt());
            withDrawOrderByParam.setRefundTraceId(addOrdersByParam.getRefundTraceId());
            withDrawOrderByParam.setPaymentTraceId(paymentTraceId);

            ResultHSBCallBack result = restTemplate.postForObject(SERVER_URL + "/outHsb/securityCenter/DISP20210280", withDrawOrderByParam, ResultHSBCallBack.class);
            log.debug("收到的响应报文:" + JSON.toJSONString(result));

            if (result.getTxnSt().equals("00")) {
                // 新增流水
                FindAcctDTO findAcctDTO = new FindAcctDTO();
                findAcctDTO.setMainOrderId(addOrdersByParam.getMainOrderId());
                if (addOrdersByParam.getStatus().equals("00")) {
                    findAcctDTO.setStatus("2");
                } else {
                    findAcctDTO.setStatus("1");
                }

//                Result<Object> result1 = Result.fail();
                ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210276", findAcctDTO, ReturnFromBusiness.class);

                if (result1 != null && result1.getCode() == 200) {
                    return Result.ok();
                } else {
                    log.error("调用更新流水失败 + " + JSON.toJSONString(findAcctDTO));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(REMOTE_INSERT_PAYJRN.getCode(), REMOTE_INSERT_PAYJRN.getMessage());
                }

            } else {
                return Result.build(-278, result.getErrMsg());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public ResultToHSBOut resultToHSBOfflinePay(AddOrdersByParam addOrdersByParam) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String dateNow = dateTimeFormatter.format(LocalDateTime.now());

            // 查主订单表
            QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PAYMENT_TRACE_ID", addOrdersByParam.getPaymentTraceId());
            TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
            if (null == tOrderInfo) {
                log.error("查询不到主订单 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(MAIN_ORDER_UPDATE_NOTNESS_ERROR.getCode()), MAIN_ORDER_UPDATE_NOTNESS_ERROR.getMessage());
            }

            if (tOrderInfo.getOrderSt().equals("00") || tOrderInfo.getOrderSt().equals("01")) {
                log.error("退款订单状态已经是成功或者失败 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("00", dateNow, String.valueOf(MAIN_ORDER_UPDATE_NOTNESS_ERROR.getCode()), "退款订单状态已经是成功或者失败");
            }

            // 更新主订单
            tOrderInfo.setOrderSt(addOrdersByParam.getRefundSt());

            UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("PAYMENT_TRACE_ID", addOrdersByParam.getPaymentTraceId());
            int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
            if (1 != update) {
                log.error("更新主订单表失败 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(MAIN_ORDER_UPDATE_NOTNESS_ERROR.getCode()), MAIN_ORDER_UPDATE_NOTNESS_ERROR.getMessage());
            }

            // 查子订单表
            QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("MAIN_ORDER_ID", tOrderInfo.getMainOrderId());
            TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
            if (null == tOrderDetailInfo) {
                log.error("查不到子订单表 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(ORDER_SELECT_ERROR.getCode()), ORDER_SELECT_ERROR.getMessage());
            }

            // 更新子订单表
            UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
            updateWrapper2.eq("MAIN_ORDER_ID", tOrderInfo.getMainOrderId());
            tOrderDetailInfo.setOrderSt(addOrdersByParam.getRefundSt());
            int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
            if (1 != update2) {
                log.error("更新子订单表失败 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(ORDER_UPDATE_ERROR.getCode()), ORDER_UPDATE_ERROR.getMessage());

            }

            // 更新流水记录
            // 惠市宝回来的时候新增
            FindAcctDTO findAcctDTO = new FindAcctDTO();
            findAcctDTO.setMainOrderId(tOrderInfo.getMainOrderId());
            findAcctDTO.setStatus(addOrdersByParam.getRefundSt());
            ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210276", findAcctDTO, ReturnFromBusiness.class);
            if (result1 != null && result1.getCode() == 200) {
                return ResultToHSBOut.build("00", dateNow, null, null);
            } else {
                log.error("调用更新流水及记帐失败 + " + JSON.toJSONString(result1));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                String dateNow1 = dateTimeFormatter1.format(LocalDateTime.now());
                return ResultToHSBOut.build("01", dateNow1, "204", "调用更新流水及记帐失败");
            }

        } catch (Exception e) {
            log.error("系统错误", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String dateNow = dateTimeFormatter.format(LocalDateTime.now());
            return ResultToHSBOut.build("01", dateNow, "203", "系统异常");
        }

    }

    @Transactional
    @Override
    public Result<Object> resultToHSBOfflineWithDraw(AddOrdersByParam addOrdersByParam) {
        try {
            // 根据交易类型更新状态状态与需不需要记帐
            LocalDateTime localDate = LocalDateTime.now();
            String yyyyMMddHHmmssSSS = localDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            IdWorker idWorker = new IdWorker("1", "1");
            long nextId = idWorker.nextId();

            WithDrawOrderByParam withDrawOrderByParam = new WithDrawOrderByParam();

            withDrawOrderByParam.setSndDtTm(String.valueOf(yyyyMMddHHmmssSSS));
            withDrawOrderByParam.setSndTraceId(String.valueOf(nextId));
            withDrawOrderByParam.setMarketId(baseMapper.getMarketId());

            String paymentTraceId = baseMapper.getPaymentTraceId(addOrdersByParam.getBusinessNo());
            if (StringUtil.isNullOrEmpty(paymentTraceId)) {

            }

            String ccbOrderDetailId = baseMapper.getSubOrderNo(addOrdersByParam.getBusinessNo());
            if (StringUtil.isNullOrEmpty(ccbOrderDetailId)) {

            }

            withDrawOrderByParam.setPaymentTraceId(paymentTraceId);
            withDrawOrderByParam.setRefundAmt(addOrdersByParam.getTxnAmt());
            withDrawOrderByParam.setCustomRefundTraceId(String.valueOf(idWorker.nextId()));
            SubOrderListParams subOrderListParams = new SubOrderListParams();
            subOrderListParams.setSubOrderNo(ccbOrderDetailId);
            subOrderListParams.setRefundAmt(addOrdersByParam.getTxnAmt());

            ParListParams parListParams = new ParListParams();
            parListParams.setAmt(addOrdersByParam.getTxnAmt());
            parListParams.setProvId(addOrdersByParam.getOrderList().get(0).getProvId());
            ArrayList<ParListParams> objects2 = new ArrayList<>();
            objects2.add(parListParams);
            subOrderListParams.setParList(objects2);

            ArrayList<SubOrderListParams> objects1 = new ArrayList<>();
            objects1.add(subOrderListParams);
            withDrawOrderByParam.setList(objects1);

            ResultHSBCallBack result = restTemplate.postForObject(SERVER_URL + "/outHsb/securityCenter/DISP20210292", withDrawOrderByParam, ResultHSBCallBack.class);
            log.debug("收到的响应报文:" + JSON.toJSONString(result));

            if (result.getTxnSt().equals("00")) {
                // 新增流水
                InsertPayJrnDTO insertPayJrnDTO = new InsertPayJrnDTO();
                BeanUtils.copyProperties(addOrdersByParam, insertPayJrnDTO);
                insertPayJrnDTO.setTxnAmt(addOrdersByParam.getTxnAmt());
                insertPayJrnDTO.setMainOrderId(result.getMainOrderNo());

//                Result<Object> result1 = Result.fail();
                ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210275", insertPayJrnDTO, ReturnFromBusiness.class);

                if (result1 != null && result1.getCode() == 200) {
                    return Result.ok();
                } else {
                    log.error("调用新增流水失败 + " + JSON.toJSONString(insertPayJrnDTO));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(REMOTE_INSERT_PAYJRN.getCode(), REMOTE_INSERT_PAYJRN.getMessage());
                }

            } else {
                return Result.build(-292, result.getErrMsg());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result<Object> moneyWithDraw(AddOrdersByParam getOrderByParam) {
        return null;
    }

    @Transactional
    @Override
    public Result<Object> cashOut(Request<CashOutParam> getOrderByParams) {
        log.debug("提现" + JSON.toJSONString(getOrderByParams));

        CashOutParam getOrderByParam = getOrderByParams.getBody();

        if (StringUtil.isNullOrEmpty(getOrderByParam.getProvId())) {
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "商户编号不能为空");
        }

        if (StringUtil.isNullOrEmpty(getOrderByParam.getPayCard())) {
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "卡号不能为空");
        }

//        if (StringUtil.isNullOrEmpty(getOrderByParam.getProvAcct()))
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "账户不能为空");
//
//        if (StringUtil.isNullOrEmpty(getOrderByParam.getProvNm()))
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "姓名不能为空");
//
//        if (StringUtil.isNullOrEmpty(getOrderByParam.getUbankNo()))
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "银行联行号不能为空");

        if (StringUtil.isNullOrEmpty(getOrderByParam.getTransMd()))
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "提现交易方式不能为空");

        if (getOrderByParam.getAmount() == null) {
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额不能为空");
        }

        try {
            if (getOrderByParam.getTransMd().equals(CASH.getTransMDStatus())) {
                InsertPayJrnDTO insertPayJrnDTO = new InsertPayJrnDTO();
                insertPayJrnDTO.setPayCard(wlyCard);
                insertPayJrnDTO.setPayAcct(wlyAcct);
                insertPayJrnDTO.setTxnAmt(getOrderByParam.getAmount());
                insertPayJrnDTO.setPayerNo(getOrderByParam.getProvId());
                insertPayJrnDTO.setPayeeCard(getOrderByParam.getPayCard());
                insertPayJrnDTO.setPayeeNum(getOrderByParam.getProvId());
                insertPayJrnDTO.setTxnType(CASHOUT.getTxnType());
                insertPayJrnDTO.setTransMd(CASH.getTransMDStatus());
//                insertPayJrnDTO.setPayeeAcct(getOrderByParam.getProvAcct());
                insertPayJrnDTO.setOperId(getOrderByParams.getHead().getOperator());
                insertPayJrnDTO.setChanlNo(getOrderByParams.getHead().getChanlNo());

                Result<Object> result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210275", insertPayJrnDTO, Result.class);

                if (result1 != null && result1.getCode() == 200) {
                    return Result.ok();
                } else {
                    log.error("调用新增流水失败 + " + JSON.toJSONString(insertPayJrnDTO));
                    return result1;
                }
            } else {
                Request request = new Request();
                request.setBody(getOrderByParam);

                Result<Object> result2 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210282", request, Result.class);
                log.debug("收到的响应报文:" + JSON.toJSONString(result2));

                if (result2 != null && result2.getCode() == 200) {
                    InsertPayJrnDTO insertPayJrnDTO = new InsertPayJrnDTO();
                    insertPayJrnDTO.setPayCard(wlyCard);
                    insertPayJrnDTO.setPayAcct(wlyAcct);
                    insertPayJrnDTO.setTxnAmt(getOrderByParam.getAmount());
                    insertPayJrnDTO.setPayerNo(getOrderByParam.getProvId());
                    insertPayJrnDTO.setPayeeNum(getOrderByParam.getProvId());
                    insertPayJrnDTO.setTxnType(CASHOUT.getTxnType());
                    insertPayJrnDTO.setTransMd(BANK.getTransMDStatus());
                    insertPayJrnDTO.setPayeeAcct(getOrderByParam.getProvAcct());
                    insertPayJrnDTO.setPayeeCard(getOrderByParam.getPayCard());
                    insertPayJrnDTO.setOperId(getOrderByParams.getHead().getOperator());
                    insertPayJrnDTO.setChanlNo(getOrderByParams.getHead().getChanlNo());
                    HashMap map = (HashMap) result2.getData();
                    insertPayJrnDTO.setCreditNo((String) map.get("creditNo"));

                    Result<Object> result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210275", insertPayJrnDTO, Result.class);

                    if (result1 != null && result1.getCode() == 200) {
                        return Result.ok();
                    } else {
                        log.error("调用新增流水失败 + " + JSON.toJSONString(insertPayJrnDTO));
                        return result1;
                    }

                } else {
//                return Result.ok();
                    return Result.build(result2.getCode(), result2.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Result<Object> getOrderStatus(ZOrderId zOrderId) {
        try {
            log.debug("电子秤查订单状态 + " + JSON.toJSONString(zOrderId));
            if (StringUtil.isNullOrEmpty(zOrderId.getOrderId()))
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "子订单ID不能为空");

            QueryWrapper<TOrderDetailInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ORDER_ID",zOrderId.getOrderId());

            TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper);
            if (tOrderDetailInfo == null) {
                log.error("没有查询到相对应的电子秤子订单 + " + JSON.toJSONString(zOrderId));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(-233,"没有查询到对应的子订单");
            }

            if (StringUtil.isNullOrEmpty(tOrderDetailInfo.getMainOrderId())) {
                log.error("没有查询到相对应子订单的主订单号 + " + JSON.toJSONString(zOrderId));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(-234,"没有擦汗寻到相对应子订单的主订单号");
            }

            QueryWrapper<TOrderInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("MAIN_ORDER_ID",tOrderDetailInfo.getMainOrderId());

            TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper1);
            if (tOrderInfo == null) {
                log.error("没有查询到子订单对应的主订单 + " + JSON.toJSONString(zOrderId));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(-235,"没有查询到子订单对应的主订单");
            }

            return Result.ok(tOrderInfo.getOrderSt());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Result<Object> withDrawToCard(Request<AddOrdersByParam> addOrdersByParams) {
        log.info("退款到一卡通接口" + JSON.toJSONString(addOrdersByParams));

        AddOrdersByParam addOrdersByParam = addOrdersByParams.getBody();
        addOrdersByParam.setOperId(addOrdersByParams.getHead().getOperator());
        addOrdersByParam.setChanlNo(addOrdersByParams.getHead().getChanlNo());

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getBusinessNo())) {
            log.error("业务号不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "业务号不能为空");
        }

//        if (addOrdersByParam.getTxnAmt() == null) {
//            log.error("退款金额不能为空" + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "退款金额不能为空");
//        }

        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(new BigDecimal(5000.00)) > 0 || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
            log.error("金额不能大于5000" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额不能大于5000");
        }

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayeeCard())) {
            log.error("收款人卡号不能为空" + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "收款人卡号不能为空");
        }

        try {
            // 拿以前的流水记录
            QueryWrapper<TPayJrn> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("BUSINESS_NO", addOrdersByParam.getBusinessNo());
            queryWrapper.eq("status","2");

            if (!StringUtil.isNullOrEmpty(addOrdersByParam.getTxnType())) {
                queryWrapper.eq("TXN_TYPE",addOrdersByParam.getTxnType());
            }

            TPayJrn tPayJrn = tPayJrnMapper.selectOne(queryWrapper);

            // 查出来的tPayjrn为空
            if (tPayJrn == null) {
                log.error("tPayJrn查询为空" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的退款记录");
            }

            if (tPayJrn.getTxnType().equals(ENTRYFEENTR.getTxnType())) {
                String status = baseMapper.getVechicleProurerInStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(CARFEE.getTxnType())) {
                String status = baseMapper.getVechicleProurerOutStatus(addOrdersByParam.getBusinessNo());

                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(BILL.getTxnType())) {
                String status = baseMapper.getBillStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else if (tPayJrn.getTxnType().equals(MCARD.getTxnType())) {
                String status = baseMapper.getMCardStatus(addOrdersByParam.getBusinessNo());
                if (!"2".equals(status)) {
                    log.error("状态不对" + JSON.toJSONString(addOrdersByParam));
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不对");
                }
            } else {
                log.error("交易类型不为缴费" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型不为缴费");
            }

            // 退进场费到一卡通
            addOrdersByParam.setTxnType(IN_WITHDAW.getTxnType());
            addOrdersByParam.setTransMd(CARD.getTransMDStatus());
            addOrdersByParam.setPayerNo(tPayJrn.getPayerNo());

            if (addOrdersByParam.getTxnAmt().compareTo(tPayJrn.getTxnAmt()) > 0) {
                log.error("退款金额大于账单金额" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "退款金额大于账单金额");
            }

            addOrdersByParam.setTxnAmt(addOrdersByParam.getTxnAmt());
            addOrdersByParam.setBusinessNo(tPayJrn.getBusinessNo());
            addOrdersByParam.setPayeeNum(tPayJrn.getPayeeNum());

            // 付款账号改为收款帐号
//            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayAcct())) {
//                addOrdersByParam.setPayeeAcct(tPayJrn.getPayAcct());
//            }

            // 收款帐号改为付款账号
            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayeeAcct())) {
                addOrdersByParam.setPayAcct(tPayJrn.getPayeeAcct());
            }

            // 付款卡号改为收款卡号
//            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayCard())) {
//                addOrdersByParam.setPayeeCard(tPayJrn.getPayCard());
//            }

            // 收款帐号改为付款账号
            if (!StringUtil.isNullOrEmpty(tPayJrn.getPayeeCard())) {
                addOrdersByParam.setPayCard(tPayJrn.getPayeeCard());
            }

            addOrdersByParam.setPayeeCard(addOrdersByParam.getPayeeCard());
            addOrdersByParam.setStatus("666");

            if (!StringUtil.isNullOrEmpty(tPayJrn.getMainOrderId())) {
                addOrdersByParam.setRefundMainOrderId(tPayJrn.getMainOrderId());
            }

            Result<Object> objectResult = payOrdersByCard(addOrdersByParam);
            if (objectResult.getCode() != 200) {
                return objectResult;
            }

            HashMap data = (HashMap) objectResult.getData();
            TCardReturnTask tCardReturnTask = new TCardReturnTask();
            tCardReturnTask.setTaskDt(new Date());

            // 拿以前的流水记录
            QueryWrapper<TVechicleProcurer> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("IN_ID", addOrdersByParam.getBusinessNo());

            TVechicleProcurer tVechicleProcurer = tVechicleProcurerMapper.selectOne(queryWrapper1);

            // 查出来的tPayjrn为空
            if (tVechicleProcurer == null) {
                log.error("tVechicleProcurer" + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的进出场纪录");
            }

            tCardReturnTask.setOutOperId(tVechicleProcurer.getOutOperId());
            tCardReturnTask.setOutTime(tVechicleProcurer.getOutTime());
            tCardReturnTask.setOperId(addOrdersByParam.getOperId());
            tCardReturnTask.setUpTime(new Date());
            tCardReturnTask.setProvId((String)data.get("payeeNum"));
            tCardReturnTask.setCardNo(addOrdersByParam.getPayeeCard());
            tCardReturnTask.setAccount((String)data.get("payeeAcct"));
            tCardReturnTask.setAmount(new BigDecimal(String.valueOf(data.get("txnAmt"))));
            tCardReturnTask.setJrnlNum((String)data.get("jrnlNum"));
            tCardReturnTask.setStatus("0");
            tCardReturnTask.setCreatTime(new Date());

            int insert = tCardReturnTaskMapper.insert(tCardReturnTask);
            if (insert != 1) {
                log.error("插入tcardRETURN数据库失败 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(-209,"数据库错误");
            }

            return Result.ok();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result<Object> updatePosOrderStatus(FindAcctDTO findAcctDTO) {
        try {
            if (StringUtil.isNullOrEmpty(findAcctDTO.getJrnlNum())) {
                log.error("流水号为空" + JSON.toJSONString(findAcctDTO));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "流水号为空");
            }

            if (StringUtil.isNullOrEmpty(findAcctDTO.getStatus())) {
                log.error("状态为空" + JSON.toJSONString(findAcctDTO));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态为空");
            }

            ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210276", findAcctDTO, ReturnFromBusiness.class);
            if (result1 != null && result1.getCode() == 200) {
                return Result.ok();
            } else {
                log.error("调用更新流水及记帐失败 + " + JSON.toJSONString(result1));
                return Result.build(result1.getCode(),result1.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result<Object> chongzheng(Request<AddOrdersByParam> params) {
        log.info("冲正交易" + JSONObject.toJSONString(params));

        if (StringUtil.isNullOrEmpty(params.getBody().getJrmlNum())) {
            log.error("流水号不能为空" + JSON.toJSONString(params));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "流水号不能为空");
        }

        try {
            QueryWrapper<TPayJrn> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("JRNL_NUM",params.getBody().getJrmlNum());

            TPayJrn tPayJrn = tPayJrnMapper.selectOne(queryWrapper);

            // 查出来的tPayjrn为空
            if (tPayJrn == null) {
                log.error("tPayJrn查询为空" + JSON.toJSONString(params));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的冲正记录");
            }

            AddOrdersByParam param = new AddOrdersByParam();
            BeanUtils.copyProperties(tPayJrn,param);
            String operator = params.getHead().getOperator();
            param.setChanlNo(params.getHead().getChanlNo());
            param.setOperId(operator);
            param.setJrnlNum(tPayJrn.getJrnlNum());
            param.setTransMd(CHONGZHENG.getTransMDStatus());

            return payOrdersByCash(param);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result<Object> preCharge(Request<AddOrdersByParams> params) {
        log.info("代充值" + JSONObject.toJSONString(params));

        for (AddOrdersByParam addOrdersByParam:params.getBody().getChargeList()) {
            addOrdersByParam.setOperId(params.getHead().getOperator());
            addOrdersByParam.setChanlNo(params.getHead().getChanlNo());
            Result<Object> objectResult = payOrdersByCash(addOrdersByParam);
            if (objectResult == null || objectResult.getCode() != 200) {
                return objectResult;
            }
        }

        return Result.ok();
    }

    @Override
    public Result<Object> applyChongzheng(Request<AddOrdersByParam> params) {
        log.info("冲正申请" + JSONObject.toJSONString(params));

        if (StringUtil.isNullOrEmpty(params.getBody().getJrmlNum())) {
            log.error("流水号不能为空" + JSON.toJSONString(params));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "流水号不能为空");
        }

        try {
            QueryWrapper<TPayJrn> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("JRNL_NUM",params.getBody().getJrmlNum());

            TPayJrn tPayJrn = tPayJrnMapper.selectOne(queryWrapper);

            // 查出来的tPayjrn为空
            if (tPayJrn == null) {
                log.error("tPayJrn查询为空" + JSON.toJSONString(params));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的冲正记录");
            }

            TAccnoReverseApply tAccnoReverseApply = new TAccnoReverseApply();
            tAccnoReverseApply.setJrnlNum(tPayJrn.getJrnlNum());
            tAccnoReverseApply.setOperId(params.getHead().getOperator());
            tAccnoReverseApply.setCheckSt("0");
            tAccnoReverseApply.setSubOrg(params.getBody().getSubOrg());
            tAccnoReverseApply.setDepId(params.getBody().getDepId());
            tAccnoReverseApply.setRemark(tPayJrn.getRemark());
            tAccnoReverseApply.setCreatTime(new Date());
            tAccnoReverseApply.setOperTm(new Date());

            int insert = tAccnoReverseApplyMapper.insert(tAccnoReverseApply);

            if (insert != 1){
                log.error("新增冲正失败" + JSON.toJSONString(params));
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "调用失败");
            }

            return Result.ok();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Result<Object> updateChongzheng(Request<AddOrdersByParam> params) {
        log.info("冲正更新" + JSONObject.toJSONString(params));

        if (params.getBody().getId() == null) {
            log.error("Id不能为空" + JSON.toJSONString(params));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "Id不能为空");
        }

        if (StringUtil.isNullOrEmpty(params.getBody().getStatus())) {
            log.error("状态不能为空" + JSON.toJSONString(params));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "状态不能为空");
        }

        try {
            UpdateWrapper<TAccnoReverseApply> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("ID",params.getBody().getId());

            TAccnoReverseApply tAccnoReverseApply = new TAccnoReverseApply();
            tAccnoReverseApply.setCheckTm(new Date());
            tAccnoReverseApply.setCheckSt(params.getBody().getStatus());
            tAccnoReverseApply.setCheckId(params.getHead().getOperator());
            tAccnoReverseApply.setUpTime(new Date());

            int update = tAccnoReverseApplyMapper.update(tAccnoReverseApply, updateWrapper);

            // 查出来的tPayjrn为空
            if (update != 1) {
                log.error("tPayJrn查询为空" + JSON.toJSONString(params));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的冲正记录");
            }

            if (params.getBody().getStatus().equals("2")) {
                QueryWrapper<TPayJrn> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("JRNL_NUM",params.getBody().getJrmlNum());

                TPayJrn tPayJrn = tPayJrnMapper.selectOne(queryWrapper1);

                // 查出来的tPayjrn为空
                if (tPayJrn == null) {
                    log.error("tPayJrn查询为空" + JSON.toJSONString(params));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "没有查到以前的冲正记录");
                }

                AddOrdersByParam param = new AddOrdersByParam();
                BeanUtils.copyProperties(tPayJrn,param);
                String operator = params.getHead().getOperator();
                param.setChanlNo(params.getHead().getChanlNo());
                param.setOperId(operator);
                param.setJrnlNum(tPayJrn.getJrnlNum());
                param.setTransMd(CHONGZHENG.getTransMDStatus());

                return payOrdersByCash(param);
            }

            return Result.ok();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public Result<Object> getOrderStatusNew(AddOrdersByParam addOrdersByParam) {
//        log.info("订单查询语音播报 + " + JSON.toJSONString(addOrdersByParam));
//
//        try {
//            if (StringUtil.isNullOrEmpty(addOrdersByParam.getProvId()))
//                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "prodId不能为空");
//
//            QueryWrapper<TOrderDetailInfo> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("PROV_ID",addOrdersByParam.getProvId());
//
//            List<TOrderDetailInfo> tOrderDetailInfos = baseMapper.selectList(queryWrapper);
//            List<String> returnList = new ArrayList<>();
//
//            for (TOrderDetailInfo tOrderDetailInfo:tOrderDetailInfos) {
//                UpdateWrapper<TOrderDetailInfo> updateWrapper = new UpdateWrapper<>();
//                updateWrapper.eq("ORDER_ID",tOrderDetailInfo.getOrderId());
//                TOrderDetailInfo tOrderDetailInfo1 = new TOrderDetailInfo();
//                tOrderDetailInfo1.setIsBroadCast("1");
//
//                int update = baseMapper.update(tOrderDetailInfo1, updateWrapper);
//
//                log.info(JSON.toJSONString(addOrdersByParam));
//                if (update != 1) {
//                    log.error("更新错误");
//                    return Result.build(-235,"更新语音播报错误");
//                }
//
//                //播报金额 = 交易金额+附加金额
//                BigDecimal amound = tOrderDetailInfo.getTxnAmt();
//
//                if (tOrderDetailInfo.getAdditAmt() != null) {
//                    amound = amound.add(tOrderDetailInfo.getAdditAmt());
//                }
//
//                String txt = "您有新的交易订单支付完成，支付金额为" + String.valueOf(amound) + "元。";
//                returnList.add(txt);
//            }
//
//            return Result.ok(returnList);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Transactional
    @Override
    public Result<PageOrderDTO> findByCondiction(SelectMainOrderAndOrderDetailVo selectMainOrderAndOrderDetailVo) {
        log.debug("查订单 + " + JSON.toJSONString(selectMainOrderAndOrderDetailVo));
        if (StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getUserId()))
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "userId不能为空");

        if (StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getType()))
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "type不能为空");

        if (StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getOrderStatus()))
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "orderStatus不能为空");

        if (selectMainOrderAndOrderDetailVo.getPageNum() == null)
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "pageNo不能为空");

        if (selectMainOrderAndOrderDetailVo.getPageSize() == null)
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "pageSize不能为空");


        PageOrderDTO pageOrderDTO = new PageOrderDTO();
        long total = 0;

        String startTime = selectMainOrderAndOrderDetailVo.getStartTime();
        String endTime = selectMainOrderAndOrderDetailVo.getEndTime();

        // 买家
        if (selectMainOrderAndOrderDetailVo.getType().equals("0")) {

            IPage<TOrderDetailInfo> page = new Page(selectMainOrderAndOrderDetailVo.getPageNum(), selectMainOrderAndOrderDetailVo.getPageSize());
            QueryWrapper<TOrderDetailInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ORDER_TP","5");
            queryWrapper.eq("PURCH_ID", selectMainOrderAndOrderDetailVo.getUserId());
            if (!StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getMerchantCode())) {
                queryWrapper.eq("PROV_ID", selectMainOrderAndOrderDetailVo.getMerchantCode());
            }

            if (!StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getStartTime()) && !StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getEndTime())) {
                queryWrapper.apply("date_format(ORDER_TM,'%Y-%m-%d') >= date_format('" + startTime + "','%Y-%m-%d')");
                queryWrapper.apply("date_format(ORDER_TM,'%Y-%m-%d') <= date_format('" + endTime + "','%Y-%m-%d')");
            }

            if (selectMainOrderAndOrderDetailVo.getOrderStatus().equals("10")) {
//                queryWrapper.eq("ORDER_ST", "3").or().eq("ORDER_ST", "6");
                queryWrapper.and(wrapper -> wrapper.eq("ORDER_ST", "3").or().eq("ORDER_ST", "6"));
            } else {
                queryWrapper.eq("ORDER_ST", selectMainOrderAndOrderDetailVo.getOrderStatus());
            }

            queryWrapper.orderByDesc("ORDER_TM");

            baseMapper.selectPage(page, queryWrapper);

            total = page.getTotal();
            List<TOrderDetailInfo> records = page.getRecords();

            List<OrderListReturn> listReturns = new ArrayList<>();

            for (TOrderDetailInfo tOrderDetailInfo:records) {
                QueryWrapper<TOrderGoodsInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("ORDER_ID",tOrderDetailInfo.getOrderId());

                List<TOrderGoodsInfo> tOrderGoodsInfos = tOrderGoodsInfoMapper.selectList(queryWrapper1);
                OrderListReturn orderListReturn = new OrderListReturn();
                BeanUtils.copyProperties(tOrderDetailInfo,orderListReturn);
                orderListReturn.setGoodList(tOrderGoodsInfos);
                listReturns.add(orderListReturn);
            }

            pageOrderDTO.setList(listReturns);
            pageOrderDTO.setTotal(total);
        }

        // 卖家
        if (selectMainOrderAndOrderDetailVo.getType().equals("1")) {
//            String telephone = baseMapper.queryUserTelephone(selectMainOrderAndOrderDetailVo.getUserId());
//            if (StringUtil.isNullOrEmpty(telephone)) {
//                return Result.build(PARA_TEL_EMPTY.getCode(), PARA_TEL_EMPTY.getMessage());
//            }

//            String provId = baseMapper.queryProvId(telephone);
            String provId = selectMainOrderAndOrderDetailVo.getUserId();
            if (StringUtil.isNullOrEmpty(provId)) {
                return Result.build(PARA_PROVID_EMPTY.getCode(), PARA_PROVID_EMPTY.getMessage());
            }

            IPage<TOrderDetailInfo> page = new Page(selectMainOrderAndOrderDetailVo.getPageNum(), selectMainOrderAndOrderDetailVo.getPageSize());
            QueryWrapper<TOrderDetailInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PROV_ID", provId);
            queryWrapper.eq("ORDER_TP","5");

            if (!selectMainOrderAndOrderDetailVo.getOrderStatus().equals("all") && !selectMainOrderAndOrderDetailVo.getOrderStatus().equals("10")) {
                queryWrapper.eq("ORDER_ST", selectMainOrderAndOrderDetailVo.getOrderStatus());
            }

            if (selectMainOrderAndOrderDetailVo.getOrderStatus().equals("10")) {
//                queryWrapper.eq("ORDER_ST", "3").or().eq("ORDER_ST", "6");
                queryWrapper.and(wrapper -> wrapper.eq("ORDER_ST", "3").or().eq("ORDER_ST", "6"));
            }

            if (!StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getStartTime()) && !StringUtil.isNullOrEmpty(selectMainOrderAndOrderDetailVo.getEndTime())) {
                queryWrapper.apply("date_format(ORDER_TM,'%Y-%m-%d') >= date_format('" + startTime + "','%Y-%m-%d')");
                queryWrapper.apply("date_format(ORDER_TM,'%Y-%m-%d') <= date_format('" + endTime + "','%Y-%m-%d')");
            }

            queryWrapper.orderByDesc("ORDER_TM");
            baseMapper.selectPage(page, queryWrapper);

            total = page.getTotal();
            List<TOrderDetailInfo> records = page.getRecords();
            List<OrderListReturn> listReturns = new ArrayList<>();

            for (TOrderDetailInfo tOrderDetailInfo:records) {
                QueryWrapper<TOrderGoodsInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("ORDER_ID",tOrderDetailInfo.getOrderId());

                List<TOrderGoodsInfo> tOrderGoodsInfos = tOrderGoodsInfoMapper.selectList(queryWrapper1);
                OrderListReturn orderListReturn = new OrderListReturn();
                BeanUtils.copyProperties(tOrderDetailInfo,orderListReturn);
                orderListReturn.setGoodList(tOrderGoodsInfos);
                listReturns.add(orderListReturn);
            }

            pageOrderDTO.setList(listReturns);
            pageOrderDTO.setTotal(total);
        }

        log.debug(JSON.toJSONString(pageOrderDTO));
        return Result.ok(pageOrderDTO);
    }

    @Transactional
    @Override
    public Result comfirmOrders(AddOrdersByParam addOrdersByParam) {
        log.debug("生成子订单 +" + JSON.toJSONString(addOrdersByParam));

        try {

            if (addOrdersByParam.getAmt() == null || addOrdersByParam.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "订单金额错误");
            }

            if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) <= 0) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易金额错误");
            }

            if (StringUtil.isNullOrEmpty(addOrdersByParam.getMode())) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易方式错误");
            }

            if (!addOrdersByParam.getMode().equals("01") && !addOrdersByParam.getMode().equals("02")) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易方式错误");
            }

            if (addOrdersByParam.getAmt().compareTo(addOrdersByParam.getTxnAmt()) < 0) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "金额错误");
            }

            if (StringUtil.isNullOrEmpty(addOrdersByParam.getProvId())) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "供货商ID不能为空");
            }

            if (StringUtil.isNullOrEmpty(addOrdersByParam.getPurchId())) {
                addOrdersByParam.setPurchId("dianzicheng");
            }

//            if (StringUtil.isNullOrEmpty(addOrdersByParam.getPurchId())) {
//                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "采购商ID不能为空");
//            }

            List<TOrderGoodsInfo> orderList = addOrdersByParam.getGoodList();
            if (orderList == null || 0 == orderList.size()) {
                log.error("没有接收到子订单 + " + JSON.toJSONString(addOrdersByParam));
                return Result.build(ORDER_COUNT_ERROR.getCode(), ORDER_COUNT_ERROR.getMessage());
            }

            Map map = baseMapper.queryOrderId();
            Integer orderId1 = (Integer) map.get("orderId");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime localDateTime = LocalDateTime.now();
            String newLocalDateTime = localDateTime.format(dtf);
            String rightAppend = String.valueOf(orderId1);
            if (rightAppend.length() < 7) {
                rightAppend = String.format("%7d", orderId1).replace(" ", "0");
            } else {
                rightAppend = rightAppend.substring(rightAppend.length() - 7, rightAppend.length());
            }

            TOrderDetailInfo tOrderDetailInfo = new TOrderDetailInfo();
            tOrderDetailInfo.setOrderSt(OrderStatusEnum.UNPAYS.getOrderStatus());
            String orderId = "Z" + newLocalDateTime + rightAppend;
            tOrderDetailInfo.setOrderId(orderId);
            tOrderDetailInfo.setOrderTm(new Date());
            tOrderDetailInfo.setUpdateDt(new Date());
            tOrderDetailInfo.setOrderTp("5");
            tOrderDetailInfo.setPurchId(addOrdersByParam.getPurchId());
            tOrderDetailInfo.setProvId(addOrdersByParam.getProvId());
            tOrderDetailInfo.setProvNm(addOrdersByParam.getProvNm());
            tOrderDetailInfo.setPrdctAmt(addOrdersByParam.getAmt());
            tOrderDetailInfo.setTxnAmt(addOrdersByParam.getTxnAmt());
            tOrderDetailInfo.setOrderModel(addOrdersByParam.getMode());
            tOrderDetailInfo.setAdditAmt(BigDecimal.ZERO);

            // 优惠总金额
            BigDecimal preferPrice = addOrdersByParam.getAmt().subtract(addOrdersByParam.getTxnAmt()).setScale(2, BigDecimal.ROUND_DOWN);

            // 设置优惠金额
            if (preferPrice.compareTo(BigDecimal.ZERO) > 0) {
                tOrderDetailInfo.setPreferPrice(preferPrice);
            }

            int result = baseMapper.insert(tOrderDetailInfo);
            if (1 != result) {
                log.error("插入数据库失败 + " + JSON.toJSONString(addOrdersByParam));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDER_INSERT_ERROR.getCode(), ORDER_INSERT_ERROR.getMessage());
            }

            List list = new ArrayList();
            for (TOrderGoodsInfo tOrderGoodsInfo:orderList) {
                if (StringUtil.isNullOrEmpty(tOrderGoodsInfo.getPrdctNm())) {
                    log.error("产品名称不能为空  " + JSON.toJSONString(addOrdersByParam));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "产品名称不能为空");
                }
                if (StringUtil.isNullOrEmpty(tOrderGoodsInfo.getPrdctId())) {
                    log.error("产品ID不能为空   " + JSON.toJSONString(addOrdersByParam));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "产品ID不能为空");
                }

                tOrderGoodsInfo.setOrderId(tOrderDetailInfo.getOrderId());
                tOrderGoodsInfo.setCreatTime(new Date());

                int insert = tOrderGoodsInfoMapper.insert(tOrderGoodsInfo);
                if (1 != insert) {
                    log.error("插入数据库失败 + " + JSON.toJSONString(addOrdersByParam));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_GOOD_INSERT_ERROR.getCode(), ORDER_GOOD_INSERT_ERROR.getMessage());
                }

                list.add(tOrderGoodsInfo);
            }

            HashMap hashMap = new HashMap();
            hashMap.put("goodLists", list);
            hashMap.put("order", tOrderDetailInfo);
            log.debug(JSON.toJSONString(hashMap));
            return Result.ok(hashMap);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result<Object> payOrders(PayOrderVo payOrderVo) {

        Result<Object> objectResult = autoTransction.firstStep(payOrderVo);

        if (200 != objectResult.getCode()) {
            return objectResult;
        }

        return autoTransction.secondStep((HashMap) objectResult.getData());
    }

    @Transactional
    @Override
    public Result<ProvPductInfo> selectProductInventoryByProvId(SelectMerchantVo selectMerchantVo) {
        log.debug("查询商户商品" + JSON.toJSONString(selectMerchantVo));
        try {
            if (StringUtil.isNullOrEmpty(selectMerchantVo.getProvId())) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), ORDERID_PARAMETER_ERROE.getMessage());
            }

//            QueryWrapper<TProductInventoryInfo> tProductInventoryInfoQueryWrapper = new QueryWrapper<>();
//            tProductInventoryInfoQueryWrapper.eq("PROV_ID", selectMerchantVo.getProvId());
//            tProductInventoryInfoQueryWrapper.orderByAsc("PRDCT_ID");
//            tProductInventoryInfoQueryWrapper.select("DISTINCT PRDCT_NM,PRDCT_ID,PRDCT_TYPE,PRDCT_TYPE_ID,UPDATE_DT,UNIT,STOCK,PROV_ID");
//            tProductInventoryInfoQueryWrapper.last("limit 1,15");
            List<TProductInventoryInfo> tProductInventoryInfos = tProductInventoryInfoMapper.queryResult(selectMerchantVo.getProvId());
            if (0 == tProductInventoryInfos.size()) {
                log.error("查询不到库存 + " + JSON.toJSONString(selectMerchantVo));
                return Result.build(PRODUCT_INVENTORY_SELECT_ERROR.getCode(), PRODUCT_INVENTORY_SELECT_ERROR.getMessage());
            }


            List<TProductInventoryInfo> tmpTproducts = new ArrayList<>();
            for (TProductInventoryInfo tProductInventoryInfo : tProductInventoryInfos) {
//                // 查每样商品的单位
//                QueryWrapper<TDictData> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("item_name", "common_value");
//                queryWrapper.eq("col_name", tProductInventoryInfo.getUnit());
//                queryWrapper.last("for update");
//
//                TDictData tDictData = tDictDataMapper.selectOne(queryWrapper);
//                if (null == tDictData) {
//                    log.error("查询不到数量 + " + JSON.toJSONString(selectMerchantVo));
//                    return Result.build(UNIT_SELECT_ERROE.getCode(), UNIT_SELECT_ERROE.getMessage());
//                }

//                TProductInventoryInfoVo tProductInventoryInfoVo = new TProductInventoryInfoVo();
//                BeanUtils.copyProperties(tProductInventoryInfo, tProductInventoryInfoVo);
//                tProductInventoryInfoVo.setUnitkey(tProductInventoryInfo.getUnit());
//                tProductInventoryInfoVo.setUnit(tDictData.getItemVal());

                tmpTproducts.add(tProductInventoryInfo);
            }

            ProvPductInfo provPductInfo = new ProvPductInfo();
            ArrayList<Object> objects = new ArrayList<>();
            provPductInfo.setProductInventoryList(tmpTproducts);

            QueryWrapper<TPalceOrderType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PROV_ID", selectMerchantVo.getProvId());
            queryWrapper.eq("STATUS", "1");
            List<TPalceOrderType> tPalceOrderTypes = tPalceOrderTypeMapper.selectList(queryWrapper);
            if (0 == tPalceOrderTypes.size()) {
                log.error("查询不到交易模式 + " + JSON.toJSONString(selectMerchantVo));
                return Result.build(PALCE_SELECT_ERROR.getCode(), PALCE_SELECT_ERROR.getMessage());
            }

            for (TPalceOrderType tPalceOrderType : tPalceOrderTypes) {
                if (tPalceOrderType.getPlaceOrderMd().equals("01")) {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("name", tPalceOrderType.getPalceOrderNm());
                    stringStringHashMap.put("md", tPalceOrderType.getPlaceOrderMd());
                    objects.add(stringStringHashMap);
                }

                if (tPalceOrderType.getPlaceOrderMd().equals("02")) {
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("name", tPalceOrderType.getPalceOrderNm());
                    stringStringHashMap.put("md", tPalceOrderType.getPlaceOrderMd());
                    objects.add(stringStringHashMap);
                }
            }

            Map provIdName = tPalceOrderTypeMapper.getProvIdName(selectMerchantVo.getProvId());
            String provName = (String)provIdName.get("prov_nm");
            String provStatus = (String) provIdName.get("status");
            if (StringUtil.isNullOrEmpty(provName)) {
                log.error("查询不到商户名 + " + JSON.toJSONString(selectMerchantVo));
                return Result.build(ORDER_MERCHANT_NAME_NOT_EXIST.getCode(), ORDER_MERCHANT_NAME_NOT_EXIST.getMessage());
            }

            provPductInfo.setName(provName);
            provPductInfo.setStatus(provStatus);
            provPductInfo.setSupportMode(objects);

            log.debug(JSON.toJSONString(provPductInfo));
            return Result.ok(provPductInfo);
        } catch (DataAccessException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public ResultToHSBOut orderResultCallBack(ResultOrderCallBackVo resultOrderCallBackVo) {

        log.debug("订单回调接口" + JSON.toJSONString(resultOrderCallBackVo));
        if (SUCCESS.getOrderStatus().equals(resultOrderCallBackVo.getOrderSt())) {
            log.info("语音播报处理");
            playSignService.getPlaySoundSign(resultOrderCallBackVo.getMainOrderId());
        }

        try {
            QueryWrapper<TOrderInfo> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("MAIN_ORDER_ID", resultOrderCallBackVo.getMainOrderId());

            TOrderInfo tOrderInfo1 = tOrderInfoMapper.selectOne(queryWrapper4);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String dateNow = dateTimeFormatter.format(LocalDateTime.now());
            if (null == tOrderInfo1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(MAIN_ORDER_UPDATE_NOTNESS_ERROR.getCode()), MAIN_ORDER_UPDATE_NOTNESS_ERROR.getMessage());
            }

            if (tOrderInfo1.getOrderSt().equals(resultOrderCallBackVo.getOrderSt())) {
                log.error("订单状态相同不需要更新 + " + JSON.toJSONString(resultOrderCallBackVo));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("00", dateNow, null, null);
            }

            if (SUCCESS.equals(tOrderInfo1.getOrderSt())) {
                log.error("订单状态已为成功 + " + JSON.toJSONString(resultOrderCallBackVo));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("00", dateNow, null, null);
            }


            // 根据主订单id更新主订单表
            UpdateWrapper<TOrderInfo> updateWrapper2 = new UpdateWrapper<>();
            updateWrapper2.eq("MAIN_ORDER_ID", resultOrderCallBackVo.getMainOrderId());
            TOrderInfo tOrderInfo2 = new TOrderInfo();
            tOrderInfo2.setOrderSt(resultOrderCallBackVo.getOrderSt());

            // 如果是为处理，改为处理中，主订单号没有未处理
            if (resultOrderCallBackVo.getOrderSt().equals(OrderStatusEnum.UNPAYS.getOrderStatus())) {
                tOrderInfo2.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            if (resultOrderCallBackVo.getOrderSt().equals(SUCCESS.getOrderStatus())) {
                if (!StringUtil.isNullOrEmpty(resultOrderCallBackVo.getPaymentDtTm())) {
                    tOrderInfo2.setPaymentDtTM(simpleDateFormat.parse(resultOrderCallBackVo.getPaymentDtTm()));
                }

//                if (StringUtil.isNullOrEmpty(resultOrderCallBackVo.get))

                tOrderInfo2.setPaymentTraceId(resultOrderCallBackVo.getPaymentTraceId());
            }

            tOrderInfo2.setUpdateDt(new Date());

//            BeanUtils.copyProperties(resultOrderCallBackVo, tOrderInfo2);
            int update2 = tOrderInfoMapper.update(tOrderInfo2, updateWrapper2);
            if (1 != update2) {
                log.error("更新主订单失败 + " + JSON.toJSONString(resultOrderCallBackVo));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(MAIN_ORDER_UPDATE_ERROR.getCode()), MAIN_ORDER_UPDATE_ERROR.getMessage());
            }

            // 根据主订单id查找订单关联表
            QueryWrapper<TOrderRelevancyInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("MAIN_ORDER_ID", resultOrderCallBackVo.getMainOrderId());
            queryWrapper.select("DISTINCT ORDER_ID");
            List<TOrderRelevancyInfo> tOrderRelevancyInfos = tOrderRelevancyInfoMapper.selectList(queryWrapper);
            if (tOrderRelevancyInfos.size() == 0) {
                log.error("未查询到关联订单 + " + JSON.toJSONString(resultOrderCallBackVo));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(ORDER_RELE_UPDATE_ERROR.getCode()), ORDER_RELE_UPDATE_ERROR.getMessage());
            }

            String purchId = null;
            String txnType = null;
            BigDecimal additAmt = new BigDecimal("0.00");
            Date txnDate = null;
            for (TOrderRelevancyInfo tOrderRelevancyInfo : tOrderRelevancyInfos) {

                // 查找子订单表
                QueryWrapper<TOrderDetailInfo> queryWrapper9 = new QueryWrapper<>();
                queryWrapper9.eq("ORDER_ID",tOrderRelevancyInfo.getOrderId());
                TOrderDetailInfo tOrderDetailInfo2 = baseMapper.selectOne(queryWrapper9);

                if (tOrderDetailInfo2 == null) {
                    log.error("未查询子订单 + " + JSON.toJSONString(resultOrderCallBackVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultToHSBOut.build("01", dateNow, String.valueOf(ORDER_SELECT_ERROR.getCode()),ORDER_SELECT_ERROR.getMessage());
                }

                purchId = tOrderDetailInfo2.getPurchId();
                txnType = tOrderDetailInfo2.getOrderTp();
                txnDate = tOrderDetailInfo2.getTxnDt();
                if (tOrderDetailInfo2.getAdditAmt()!=null) {
                    additAmt = tOrderDetailInfo2.getAdditAmt();
                }

                // 更新子订单表
                UpdateWrapper<TOrderDetailInfo> updateWrapper1 = new UpdateWrapper<>();
                updateWrapper1.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                TOrderDetailInfo tOrderDetailInfo = new TOrderDetailInfo();
                tOrderDetailInfo.setOrderSt(resultOrderCallBackVo.getOrderSt());

//                // 主订单处理失败，子订单未支付
//                if (resultOrderCallBackVo.getOrderSt().equals(OrderStatusEnum.FAIL.getOrderStatus())) {
//                    tOrderDetailInfo.setOrderSt(OrderStatusEnum.UNPAYS.getOrderStatus());
//                }

                // 主订单未支付，子订单处理中
                if (resultOrderCallBackVo.getOrderSt().equals(OrderStatusEnum.UNPAYS.getOrderStatus())) {
                    tOrderDetailInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
                }

                tOrderDetailInfo.setUpdateDt(new Date());
                int update1 = baseMapper.update(tOrderDetailInfo, updateWrapper1);

                if (update1 != 1) {
                    log.error("更新子订单失败 + " + JSON.toJSONString(resultOrderCallBackVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultToHSBOut.build("01", dateNow, String.valueOf(ORDER_UPDATE_ERROR.getCode()),ORDER_UPDATE_ERROR.getMessage());
                }


                // 推送订单状态为未支付成功或其他更新库存
                // 备注！！！！ 只要我收到的消息不是成功，库存我肯定给他加上去!!!!!!
                if (!SUCCESS.getOrderStatus().equals(resultOrderCallBackVo.getOrderSt())) {
                    QueryWrapper<TOrderDetailInfo> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());

                    List<TOrderDetailInfo> tOrderDetailInfos = baseMapper.selectList(queryWrapper1);
                    for (TOrderDetailInfo tOrderDetailInfo1 : tOrderDetailInfos) {
                        if (tOrderDetailInfo1.getOrderModel().equals("02")) {

                            QueryWrapper<TOrderGoodsInfo> queryWrapper5 = new QueryWrapper<>();
                            queryWrapper5.eq("ORDER_ID",tOrderDetailInfo1.getOrderId());

                            List<TOrderGoodsInfo> tOrderGoodsInfos = tOrderGoodsInfoMapper.selectList(queryWrapper5);
                            for (TOrderGoodsInfo tOrderGoodsInfo:tOrderGoodsInfos) {
                                QueryWrapper<TProductInventoryInfo> tProductInventoryInfoQueryWrapper = new QueryWrapper<>();
                                tProductInventoryInfoQueryWrapper.eq("PROV_ID", tOrderDetailInfo1.getProvId());
                                tProductInventoryInfoQueryWrapper.eq("PRDCT_ID", tOrderGoodsInfo.getPrdctId());
                                tProductInventoryInfoQueryWrapper.last("for update");
                                TProductInventoryInfo tProductInventoryInfo1 = tProductInventoryInfoMapper.selectOne(tProductInventoryInfoQueryWrapper);
                                if (null == tProductInventoryInfo1) {
                                    log.error("查询不到库存 + " + JSON.toJSONString(resultOrderCallBackVo));
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return ResultToHSBOut.build("01", dateNow, String.valueOf(PRODUCT_INVENTORY_NOTEXIST.getCode()), PRODUCT_INVENTORY_NOTEXIST.getMessage());
                                }

                                UpdateWrapper<TProductInventoryInfo> tProductInventoryInfoUpdateWrapper = new UpdateWrapper<>();
                                tProductInventoryInfoUpdateWrapper.eq("PROV_ID", tOrderDetailInfo1.getProvId());
                                tProductInventoryInfoUpdateWrapper.eq("PRDCT_ID", tOrderGoodsInfo.getPrdctId());
                                tProductInventoryInfo1.setStock(tProductInventoryInfo1.getStock().add(tOrderGoodsInfo.getPrdctWeight()));
                                int update3 = tProductInventoryInfoMapper.update(tProductInventoryInfo1, tProductInventoryInfoUpdateWrapper);
                                if (1 != update3) {
                                    log.error("更新库存失败 + " + JSON.toJSONString(resultOrderCallBackVo));
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return ResultToHSBOut.build("01", dateNow, String.valueOf(ORDER_INVENTORY_UPDATE_ERROR.getCode()), ORDER_INVENTORY_UPDATE_ERROR.getMessage());
                                }
                            }
                        }
                    }
                }
            }

            if (txnType.equals("5")) {

                if (additAmt.compareTo(BigDecimal.ZERO) > 0) {

                    if (resultOrderCallBackVo.getOrderSt().equals(SUCCESS.getOrderStatus()) ||
                        resultOrderCallBackVo.getOrderSt().equals(FOR_HADNLE.getOrderStatus()) ||
                            resultOrderCallBackVo.getOrderSt().equals(FOR_POLLING.getOrderStatus())){

                    } else {
                        Map map = new HashMap();
                        map.put("PROV_ID",purchId);
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        map.put("DISCNT_DT",simpleDateFormat1.format(txnDate));
                        int i = tDiscountsUserMapper.deleteByMap(map);
                    }
//                    if (i != 1){
//                        log.error("删除活动表错误 + " + JSON.toJSONString(resultOrderCallBackVo));
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                        return ResultToHSBOut.build("01", dateNow, String.valueOf(ACT_INSERT_ERROR.getCode()), ACT_INSERT_ERROR.getMessage());
//                    }
                    return ResultToHSBOut.build("00", dateNow, null, null);
                } else {
                    return ResultToHSBOut.build("00", dateNow, null, null);
                }

            } else  {
                // 更新流水记录
                // 惠市宝回来的时候新增
                FindAcctDTO findAcctDTO = new FindAcctDTO();
                findAcctDTO.setMainOrderId(resultOrderCallBackVo.getMainOrderId());
                findAcctDTO.setStatus(resultOrderCallBackVo.getOrderSt());

                ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210276", findAcctDTO, ReturnFromBusiness.class);
                if (result1 != null && result1.getCode() == 200) {
                    return ResultToHSBOut.build("00", dateNow, null, null);
                } else {
                    log.error("调用更新流水及记帐失败 + " + JSON.toJSONString(result1));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
                    String dateNow1 = dateTimeFormatter1.format(LocalDateTime.now());
                    return ResultToHSBOut.build("01", dateNow1, "204", "调用更新流水及记帐失败");
                }
            }

        } catch (Exception e) {
            log.error("系统错误", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String dateNow = dateTimeFormatter.format(LocalDateTime.now());
            return ResultToHSBOut.build("01", dateNow, "203", "系统异常");
        }
    }

    @Transactional
    @Override
    public Result<Object> cancelOrder(OrderVo orderId) {
        if (StringUtil.isNullOrEmpty(orderId.getOrderId())) {
            log.error("子订单编号为空");
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "子订单编号为空");
        }

        try {
            QueryWrapper<TOrderDetailInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ORDER_ID", orderId.getOrderId());
            TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper);
            if (null == tOrderDetailInfo) {
                log.error("未查询到子订单 + " + orderId.getOrderId());
                return Result.build(ORDER_GETDETAIL.getCode(), ORDER_GETDETAIL.getMessage());
            }

            if (!tOrderDetailInfo.getOrderSt().equals(OrderStatusEnum.UNPAYS.getOrderStatus())) {
                log.error("子订单号状态错误 + " + orderId.getOrderId());
                return Result.build(ORDER_STATUS_ERROE.getCode(), ORDER_STATUS_ERROE.getMessage());
            }

            UpdateWrapper<TOrderDetailInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("ORDER_ID", orderId.getOrderId());
            tOrderDetailInfo.setOrderSt(OrderStatusEnum.EXPIRE.getOrderStatus());
            tOrderDetailInfo.setUpdateDt(new Date());
            int update = baseMapper.update(tOrderDetailInfo, updateWrapper);
            if (update != 1) {
                log.error("子订单号状态错误 + " + orderId.getOrderId());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
            }

            return Result.ok(orderId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    @Override
    public ResultToHSBOut verifyAccountCallBack(MultipartFile file) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String dateNow = dateTimeFormatter.format(LocalDateTime.now());
        File file1 = null;
        ZipInputStream zin = null;
        ZipFile zipFile = null;
        FileInputStream fileInputStream = null;
        File tmp = null;
        FileOutputStream fos = null;
        try {
            // 将文件插入数据库
            file1 = TOrderDetailInfoServiceImpl.multipartFileToFile(file);
            ResultToHSBOut resultToHSBOut = autoTransction.InsertFile(file1);
            if (!resultToHSBOut.getTxnSt().equals("00")) {
                return resultToHSBOut;
            }

            //创建ZipInputStream对象
            zipFile = new ZipFile(file1);

            fileInputStream = new FileInputStream(file1);

            //创建压缩文件对象
            //实例化对象，指明要解压的文件
            zin = new ZipInputStream(fileInputStream);
            ZipEntry entry;

            //如果entry不为空，并不在同一个目录下
            while (((entry = zin.getNextEntry()) != null) && !entry.isDirectory()) {
                //解压出的文件路径
                tmp = new File(entry.getName());

                if (!tmp.exists()) {
                    tmp.getParentFile().mkdirs();
                }


                String fileName = tmp.getAbsolutePath();
                int tmpNum = -1;
                fos = new FileOutputStream(fileName);
                while ((tmpNum = zin.read()) != -1) {
                    fos.write(tmpNum);
                }

                ArrayList<String> strings = TOrderDetailInfoServiceImpl.readTxtFile(tmp.getAbsolutePath());
                for (int i = 1; i < strings.size(); i++) {
                    String value = strings.get(i);
                    String[] fileds = value.split("\\|");

                    if ((fileName.contains("det.txt")) && !(fileName.contains("redet.txt"))) {
                        TApportionDetailsInfoTmp tApportionDetailsInfo = getMappingPo(TApportionDetailsInfoTmp.class, fileds);
                        tApportionDetailsInfo.setPartAmt(new BigDecimal(tApportionDetailsInfo.getTempPartAmt()));
                        tApportionDetailsInfo.setMarketId(baseMapper.getMarketId());
                        StringBuffer tmpPartDt = new StringBuffer(tApportionDetailsInfo.getTmpPartDt());
                        tmpPartDt.insert(4, "-");
                        tmpPartDt.insert(7, "-");
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = smf.parse(String.valueOf(tmpPartDt));
                        tApportionDetailsInfo.setPartDt(date);
                        tApportionDetailsInfo.setServChrg(new BigDecimal(tApportionDetailsInfo.getServChrgTmp()));
                        TApportionDetailsInfo tApportionDetailsInfo1 = new TApportionDetailsInfo();
                        BeanUtils.copyProperties(tApportionDetailsInfo, tApportionDetailsInfo1);
                        int insert = tApportionDetailsInfoMapper.insert(tApportionDetailsInfo1);
                        if (1 != insert) {
                            log.error("分账明细表插入失败");
                            fos.close();
                            zin.closeEntry();
                            fileInputStream.close();
                            zipFile.close();
                            zin.close();
                            tmp.delete();
                            tmp.getParentFile().delete();
                            TOrderDetailInfoServiceImpl.delteTempFile(file1);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultToHSBOut.build("01", dateNow, String.valueOf(APO_DETAIL_FAIL.getCode()), APO_DETAIL_FAIL.getMessage());
                        }
                    }

                    if (fileName.contains("redet.txt")) {
                        TApportionDetailsInfoTmp tApportionDetailsInfo = getMappingPo(TApportionDetailsInfoTmp.class, fileds);
                        tApportionDetailsInfo.setPartAmt(new BigDecimal(tApportionDetailsInfo.getTempPartAmt()));
                        tApportionDetailsInfo.setMarketId(baseMapper.getMarketId());
                        StringBuffer tmpPartDt = new StringBuffer(tApportionDetailsInfo.getTmpPartDt());
                        tmpPartDt.insert(4, "-");
                        tmpPartDt.insert(7, "-");
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = smf.parse(String.valueOf(tmpPartDt));
                        tApportionDetailsInfo.setPartDt(date);
                        tApportionDetailsInfo.setServChrg(new BigDecimal(tApportionDetailsInfo.getServChrgTmp()));
                        TApportionDetailsInfo tApportionDetailsInfo1 = new TApportionDetailsInfo();
                        BeanUtils.copyProperties(tApportionDetailsInfo, tApportionDetailsInfo1);
                        UpdateWrapper<TApportionDetailsInfo> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("MARKET_ID", tApportionDetailsInfo1.getMarketId());
                        updateWrapper.eq("SER_NUM", tApportionDetailsInfo1.getSerNum());
                        updateWrapper.eq("PAYMENT_TRACE_ID", tApportionDetailsInfo1.getPaymentTraceId());

                        int update = tApportionDetailsInfoMapper.update(tApportionDetailsInfo1, updateWrapper);
                        if (1 != update) {
                            log.error("分账明细表更新失败");
                            fos.close();
                            zin.closeEntry();
                            fileInputStream.close();
                            zipFile.close();
                            zin.close();
                            tmp.delete();
                            tmp.getParentFile().delete();
                            TOrderDetailInfoServiceImpl.delteTempFile(file1);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultToHSBOut.build("01", dateNow, String.valueOf(APO_DETAIL_UPDATE_FAIL.getCode()), APO_DETAIL_UPDATE_FAIL.getMessage());
                        }
                    }

                    if ((fileName.contains("sum.txt")) && !(fileName.contains("resum.txt"))) {
                        TApportionSumInfoTmp tApportionSumInfoTmp = getMappingPo(TApportionSumInfoTmp.class, fileds);
                        tApportionSumInfoTmp.setMarketId(baseMapper.getMarketId());
                        tApportionSumInfoTmp.setPartAmt(new BigDecimal(tApportionSumInfoTmp.getPartAmtTmp()));
                        StringBuffer tmpPartDt = new StringBuffer(tApportionSumInfoTmp.getPartDtTmp());
                        tmpPartDt.insert(4, "-");
                        tmpPartDt.insert(7, "-");
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = smf.parse(String.valueOf(tmpPartDt));
                        tApportionSumInfoTmp.setPartDt(date);
                        tApportionSumInfoTmp.setUpdateDt(new Date());
                        TApportionSumInfo tApportionSumInfo = new TApportionSumInfo();
                        BeanUtils.copyProperties(tApportionSumInfoTmp, tApportionSumInfo);
                        int insert = tApportionSumInfoMapper.insert(tApportionSumInfo);
                        if (1 != insert) {
                            log.error("分账汇总表插入失败");
                            fos.close();
                            zin.closeEntry();
                            fileInputStream.close();
                            zipFile.close();
                            zin.close();
                            tmp.delete();
                            tmp.getParentFile().delete();
                            TOrderDetailInfoServiceImpl.delteTempFile(file1);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultToHSBOut.build("01", dateNow, String.valueOf(APO_SUM_FAIL.getCode()), APO_SUM_FAIL.getMessage());
                        }
                    }

                    if (fileName.contains("resum.txt")) {
                        TApportionSumInfoTmp tApportionSumInfoTmp = getMappingPo(TApportionSumInfoTmp.class, fileds);
                        tApportionSumInfoTmp.setMarketId(baseMapper.getMarketId());
                        tApportionSumInfoTmp.setPartAmt(new BigDecimal(tApportionSumInfoTmp.getPartAmtTmp()));
                        StringBuffer tmpPartDt = new StringBuffer(tApportionSumInfoTmp.getPartDtTmp());
                        tmpPartDt.insert(4, "-");
                        tmpPartDt.insert(7, "-");
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = smf.parse(String.valueOf(tmpPartDt));
                        tApportionSumInfoTmp.setPartDt(date);
                        tApportionSumInfoTmp.setUpdateDt(new Date());
                        TApportionSumInfo tApportionSumInfo = new TApportionSumInfo();
                        BeanUtils.copyProperties(tApportionSumInfoTmp, tApportionSumInfo);
                        UpdateWrapper<TApportionSumInfo> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("MARKET_ID", tApportionSumInfo.getMarketId());
                        updateWrapper.eq("PART_DT", tApportionSumInfo.getPartDt());
                        updateWrapper.eq("PAYEE_ID", tApportionSumInfo.getPayeeId());

                        int update = tApportionSumInfoMapper.update(tApportionSumInfo, updateWrapper);
                        if (1 != update) {
                            log.error("分账汇宗表更新失败");
                            fos.close();
                            zin.closeEntry();
                            fileInputStream.close();
                            zipFile.close();
                            zin.close();
                            tmp.delete();
                            tmp.getParentFile().delete();
                            TOrderDetailInfoServiceImpl.delteTempFile(file1);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultToHSBOut.build("01", dateNow, String.valueOf(APO_SUM_UPDATE_FAIL.getCode()), APO_SUM_UPDATE_FAIL.getMessage());
                        }
                    }

                    if (fileName.contains("sub.txt")) {
                        TWithholdDetailsInfoTmp tWithholdDetailsInfoTmp = getMappingPo(TWithholdDetailsInfoTmp.class, fileds);
                        tWithholdDetailsInfoTmp.setMarketId(baseMapper.getMarketId());
                        tWithholdDetailsInfoTmp.setDeduAmt(new BigDecimal(tWithholdDetailsInfoTmp.getDeduAmtTmp()));
                        StringBuffer tmpPartDt = new StringBuffer(tWithholdDetailsInfoTmp.getDeduStTmp());
                        tmpPartDt.insert(4, "-");
                        tmpPartDt.insert(7, "-");
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = smf.parse(String.valueOf(tmpPartDt));
                        tWithholdDetailsInfoTmp.setDeduDt(date);
                        TWithholdDetailsInfo tWithholdDetailsInfo = new TWithholdDetailsInfo();
                        BeanUtils.copyProperties(tWithholdDetailsInfoTmp, tWithholdDetailsInfo);
                        int insert = tWithholdDetailsInfoMapper.insert(tWithholdDetailsInfo);
                        if (1 != insert) {
                            log.error("建翔明细表插入失败");
                            fos.close();
                            zin.closeEntry();
                            fileInputStream.close();
                            zipFile.close();
                            zin.close();
                            tmp.delete();
                            tmp.getParentFile().delete();
//                            TOrderDetailInfoServiceImpl.delteTempFile(file1);
                            boolean delete = file1.delete();
                            System.out.println(delete);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultToHSBOut.build("01", dateNow, String.valueOf(APO_SUB_FAIL.getCode()), APO_SUB_FAIL.getMessage());
                        }
                    }

                    if (fileName.contains("chk.txt")) {
                        TReconciliationDetailsInfoTmp tReconciliationDetailsInfoTmp = getMappingPo(TReconciliationDetailsInfoTmp.class, fileds);
                        tReconciliationDetailsInfoTmp.setTxnAmt(new BigDecimal(tReconciliationDetailsInfoTmp.getTxnAmtTmp()));
                        StringBuffer tmpPartDt = new StringBuffer(tReconciliationDetailsInfoTmp.getTxnDtTmp());
                        tmpPartDt.insert(4, "-");
                        tmpPartDt.insert(7, "-");
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = smf.parse(tmpPartDt.toString());
                        tReconciliationDetailsInfoTmp.setMarketId(baseMapper.getMarketId());
                        tReconciliationDetailsInfoTmp.setTxnDt(date);
                        tReconciliationDetailsInfoTmp.setServChgr(new BigDecimal(tReconciliationDetailsInfoTmp.getServCharTmp()));
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
                        tReconciliationDetailsInfoTmp.setTxnTm(simpleDateFormat.parse(tReconciliationDetailsInfoTmp.getTxtTmTmp()));
                        TReconciliationDetailsInfo tReconciliationDetailsInfo = new TReconciliationDetailsInfo();
                        BeanUtils.copyProperties(tReconciliationDetailsInfoTmp, tReconciliationDetailsInfo);
                        int insert = tReconciliationDetailsInfoMapper.insert(tReconciliationDetailsInfo);

                        if (1 != insert) {
                            log.error("插入对账明细表成功");
                            fos.close();
                            zin.closeEntry();
                            zin.close();
                            fileInputStream.close();
                            zipFile.close();
                            tmp.delete();
                            tmp.getParentFile().delete();
                            boolean delete = file1.delete();
                            System.out.println(delete);
//                            delteTempFile(file1);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultToHSBOut.build("01", dateNow, String.valueOf(APO_CHK_FAIL.getCode()), APO_CHK_FAIL.getMessage());
                        }

                    }
                }

                fos.close();
                zin.closeEntry();
                tmp.delete();
                tmp.getParentFile().delete();
                System.out.println(entry.getName() + "解压成功");

            }

            zin.close();
            zipFile.close();
            fileInputStream.close();

            DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
            TFileInfo tFileInfo = new TFileInfo();
            tFileInfo.setFileName(file1.getName());
            QueryWrapper<TFileInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("FILE_NAME", file1.getName());
            queryWrapper.eq("FILE_STATUS", "0");

            TFileInfo tFileInfo1 = tFileInfoMapper.selectOne(queryWrapper);
            if (null == tFileInfo1) {
                log.error("没找到需要更新状态的文件");
                TOrderDetailInfoServiceImpl.delteTempFile(file1);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(FILE_SELECT_FAIL.getCode()), FILE_SELECT_FAIL.getMessage());
            }

            UpdateWrapper<TFileInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("FILE_NAME", tFileInfo1.getFileName());
            tFileInfo1.setFileStatus("1");
            tFileInfo1.setUpdateTime(new Date());

            int update = tFileInfoMapper.update(tFileInfo1, updateWrapper);
            if (1 != update) {
                log.error("更新文件失败");
                TOrderDetailInfoServiceImpl.delteTempFile(file1);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(FILE_UPDATE_FAIL.getCode()), FILE_UPDATE_FAIL.getMessage());
            }

            TOrderDetailInfoServiceImpl.delteTempFile(file1);
            return ResultToHSBOut.build("00", dateNow, null, null);

        } catch (Exception e) {
//            fos.close();
            try {
                fos.close();
                zin.closeEntry();
                zin.close();
                fileInputStream.close();
//                tmp.getParentFile().delete();
                zipFile.close();
            } catch (Exception ioException) {
//                ioException.printStackTrace();
//                try {
                throw new RuntimeException(ioException);

//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
            }

//            tmp.delete();
            boolean delete = file1.delete();
            System.out.println(tmp.delete());
            System.out.println(tmp.getParentFile().delete());
            System.out.println(delete);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultToHSBOut.build("01", dateNow, "203", e.getMessage());
        }
    }


    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }

        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) throws Exception {
//        try {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            boolean delete = del.delete();
            System.out.println(delete);
        }
    }

    public static ArrayList<String> readTxtFile(String filePath) throws Exception {

        ArrayList<String> objects = new ArrayList<>();

//        try {
        String encoding = "UTF8";
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                if (lineTxt.equals("")) {
                    continue;
                } else {
                    objects.add(lineTxt);
                }
            }
            read.close();
        } else {
            System.out.println("找不到指定的文件");
        }

        return objects;
//        } catch (Exception e) {
//            System.out.println("读取文件内容出错");
//            e.printStackTrace();
//        }

//        return objects;
    }

    public static <T> T getMappingPo(Class<T> classz, Object[] objs) throws Exception {
        Field[] fields = classz.getDeclaredFields();
        Object object = classz.newInstance();
        int length = objs.length;

        for (Field fild : fields) {
            String typeStr = fild.getType().getSimpleName().toLowerCase();
            Annotation[] annos = fild.getAnnotations();

            if (annos.length > 0) {
                for (Annotation anno : annos) {
                    if (("YHHIndex".equals(anno.annotationType().getSimpleName()))) {
                        YHHIndex myAmoncation = (YHHIndex) anno;
                        int index = myAmoncation.index();

                        if (index < length) {
                            fild.setAccessible(true);

                            if ("double".equals(typeStr)) {
                                fild.set(object, Double.valueOf(objs[index] == null ? "0" : objs[index].toString()));
                            } else if ("int".equals(typeStr) || "integer".equals(typeStr)) {
                                fild.set(object, Integer.valueOf(objs[index] == null ? "0" : objs[index].toString()));
                            } else if ("bigDecimal".equals(typeStr)) {
                                if (objs[index] == null) {
                                    fild.set(object, null);
                                } else {
                                    BigDecimal bigDecimal = new BigDecimal((String) objs[index]);
                                    fild.set(object, bigDecimal);
                                }
                            } else if ("Date".equals(typeStr)) {
                                if (objs[index] == null) {
                                    fild.set(object, null);
                                } else {
                                    SimpleDateFormat smf = new SimpleDateFormat("yyyyMMdd");
                                    Date date = smf.parse((String) objs[index]);
                                    fild.set(object, date);
                                }

                            } else if ("date".equals(typeStr)) {
//                                fild.set(object, SGS.asDate(objs[index] ==null? null : objs[index].toString()));
//                            }else {
                            } else {
                                fild.set(object, objs[index]);
                            }
                        }
                    }
                }
            }
        }

        return (T) object;
    }
}



