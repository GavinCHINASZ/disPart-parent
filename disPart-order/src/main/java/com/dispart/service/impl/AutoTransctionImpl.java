package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dispart.dao.*;
import com.dispart.dao.mapper.TFileInfoMapper;
import com.dispart.dao.mapper.TPartModeTypeMapper;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.dto.orderdto.*;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.model.order.OrderStatusEnum;
import com.dispart.model.order.*;
import com.dispart.result.Result;
import com.dispart.result.ResultToHSBOut;
import com.dispart.service.AutoTransction;
import com.dispart.util.IdWorker;
import com.dispart.vo.order.PayOrderVo;
import com.dispart.vo.order.ReturnFromBusiness;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import javax.management.Query;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.dispart.result.ResultCodeOrderEnum.*;
import static com.dispart.result.ResultCodeOrderEnum.ORDER_RELE_INSERT_ERROR;
import static com.dispart.model.businessCommon.CardInfoStatusEnum.*;
import static com.dispart.model.businessCommon.TxnTypeEnum.*;
import static com.dispart.model.businessCommon.TransMdEnum.*;


@Service
@Slf4j
public class AutoTransctionImpl implements AutoTransction {

    @Value("${pushUrl}")
    private String SERVER_URL;

    @Autowired
    @Qualifier("restTemplate1")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Autowired
    private TOrderInfoMapper tOrderInfoMapper;

    @Autowired
    private TFileInfoMapper tFileInfoMapper;

    @Autowired
    private TOrderGoodsInfoMapper tOrderGoodsInfoMapper;

    @Autowired
    private TDiscountsUserMapper tDiscountsUserMapper;

    @Autowired
    private TProductInventoryInfoMapper tProductInventoryInfoMapper;

    @Autowired
    private TOrderRelevancyInfoMapper tOrderRelevancyInfoMapper;

    @Autowired
    private TOrderDetailInfoMapper baseMapper;

    @Autowired
    private TPartModeTypeMapper tPartModeTypeMapper;

    @Override
    @Transactional
    public Result<Object> firstStep(PayOrderVo payOrderVo) {
        log.debug("开始生成主订单" + payOrderVo.toString());

        try {
            String partModeId = null;

            if (null == payOrderVo.getAmt() || payOrderVo.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "总金额错误");
            }

            if (null == payOrderVo.getTxnAmt() || payOrderVo.getTxnAmt().compareTo(BigDecimal.ZERO) <= 0) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "总交易金额错误");
            }

            if (StringUtil.isNullOrEmpty(payOrderVo.getPaymentMode())) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "支付方式不能为空");
            }

            List<TOrderDetailInfo> orderList = payOrderVo.getOrderList();
            if (0 == orderList.size()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDER_COUNT_ERROR.getCode(), ORDER_COUNT_ERROR.getMessage());
            }

            GetOrderByParam getOrderByParam = new GetOrderByParam();
            ArrayList<TOrderInfo2> objects = new ArrayList<>();
            Date now = new Date();

            HashMap<String, BigDecimal> stringHashMap = new HashMap<>();

            BigDecimal additAmt = null;

            // 剩下的附加项金额
            BigDecimal additAmtRemain = null;

            if (payOrderVo.getAdditAmt()!= null && payOrderVo.getAdditAmt().compareTo(BigDecimal.ZERO) > 0) {
                // 附加金额的价格
                additAmt = payOrderVo.getAdditAmt().setScale(2, BigDecimal.ROUND_DOWN);
                additAmtRemain = payOrderVo.getAdditAmt().setScale(2, BigDecimal.ROUND_DOWN);
            }

            for (int i = 0; i < payOrderVo.getOrderList().size(); i++) {
                QueryWrapper<TOrderDetailInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("ORDER_ID", payOrderVo.getOrderList().get(i).getOrderId());
                queryWrapper1.last("for update");
                TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper1);
                if (null == tOrderDetailInfo) {
                    log.error("没有查询到子订单 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_GETDETAIL.getCode(), ORDER_GETDETAIL.getMessage());
                }

                // 判断子订单状态
                if (!OrderStatusEnum.UNPAYS.getOrderStatus().equals(tOrderDetailInfo.getOrderSt())) {
                    log.error("子订单状态错误 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_STATUS_ERROE.getCode(), ORDER_STATUS_ERROE.getMessage());
                }

                // 计算是否有附加金额
                if (additAmt != null && additAmt.compareTo(BigDecimal.ZERO) > 0) {
                    if (i == payOrderVo.getOrderList().size() -1) {
                        tOrderDetailInfo.setAdditAmt(additAmtRemain);
                        tOrderDetailInfo.setTxnAmt(tOrderDetailInfo.getTxnAmt().subtract(tOrderDetailInfo.getAdditAmt()));
                    } else {
                        BigDecimal divide = tOrderDetailInfo.getTxnAmt().divide(payOrderVo.getTxnAmt(), 4, BigDecimal.ROUND_DOWN);
                        BigDecimal eachAmt = additAmt.multiply(divide).setScale(2, BigDecimal.ROUND_DOWN);
                        tOrderDetailInfo.setAdditAmt(eachAmt);
                        additAmtRemain = additAmtRemain.subtract(eachAmt);
                        tOrderDetailInfo.setTxnAmt(tOrderDetailInfo.getTxnAmt().subtract(tOrderDetailInfo.getAdditAmt()));
                    }
                }

                // 查询分账模式
                QueryWrapper<TPartModeType> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("STATUS", "0");
                List<TPartModeType> tPartModeTypes = tPartModeTypeMapper.selectList(queryWrapper);
                if (1 != tPartModeTypes.size()) {
                    log.error("没有查询到分账模式 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(PART_MODEL_FAIL.getCode(), PART_MODEL_FAIL.getMessage());
                }

                // 更新子订单表到list里，与数据库无关
                TPartModeType tPartModeType = tPartModeTypes.get(0);
                partModeId = tPartModeType.getPartMdId();

                // 比例分账
                if (tPartModeType.getPartMdTp().equals("0")) {

                    // 比例大于1
                    if ((new BigDecimal("1.00")).compareTo(tPartModeType.getPartMdVal()) == -1) {
                        log.error("分账模式比例大于１ + " + JSON.toJSONString(payOrderVo));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(BILI_TOO_BIG.getCode(), BILI_TOO_BIG.getMessage());
                    }

                    BigDecimal allMoney = tOrderDetailInfo.getTxnAmt();
                    BigDecimal servChag = allMoney.multiply(tPartModeType.getPartMdVal());
                    servChag = servChag.setScale(2, BigDecimal.ROUND_DOWN);
                    BigDecimal partAmt = allMoney.subtract(servChag);
                    tOrderDetailInfo.setPartAmt(partAmt);
                    tOrderDetailInfo.setServChrg(servChag);
                }

                // 固定分账
                if (tPartModeType.getPartMdTp().equals("1")) {
                    // 分成大于现在金额报错
                    if (tOrderDetailInfo.getTxnAmt().compareTo(tPartModeType.getPartMdVal()) < 0) {
                        log.error("分账金额太大 + " + JSON.toJSONString(payOrderVo));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(MONEY_TOO_BIG.getCode(), MONEY_TOO_BIG.getMessage());
                    }

                    BigDecimal allMoney = tOrderDetailInfo.getTxnAmt();
                    BigDecimal servChag = tPartModeType.getPartMdVal();
                    BigDecimal partAmt = allMoney.subtract(tPartModeType.getPartMdVal());
                    tOrderDetailInfo.setPartAmt(partAmt);
                    tOrderDetailInfo.setServChrg(servChag);
                }

                // 设置电子秤用户ID
                if (tOrderDetailInfo.getPurchId().equals("dianzicheng")) {
                    tOrderDetailInfo.setPurchId(payOrderVo.getPurchId());
                }

                UpdateWrapper<TOrderDetailInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("ORDER_ID", tOrderDetailInfo.getOrderId());
                tOrderDetailInfo.setTxnDt(now);
                tOrderDetailInfo.setUpdateDt(now);

                int update1 = baseMapper.update(tOrderDetailInfo, updateWrapper);
                if (update1 == 0) {
                    log.error("更新子订单失败 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_INVENTORY_UPDATE_ERROR.getCode(), ORDER_INVENTORY_UPDATE_ERROR.getMessage());
                }

                // 用数据库最新的值替代当前的数组
                payOrderVo.getOrderList().set(i, tOrderDetailInfo);
            }

            // 生成主订单表
            TOrderInfo tOrderInfo = new TOrderInfo();

            Map map1 = baseMapper.queryMainOrderId();
            Integer orderId1 = (Integer) map1.get("mainOrderId");
            if (null == orderId1) {
                log.error("查不到分布式住订单ＩＤ + " + JSON.toJSONString(payOrderVo));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(MAIN_GET_MAINID_ERROR.getCode(), MAIN_GET_MAINID_ERROR.getMessage());
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime localDateTime = LocalDateTime.now();
            String newLocalDateTime = localDateTime.format(dtf);
            String rightAppend = String.valueOf(orderId1);

            if (rightAppend.length() < 7) {
                rightAppend = String.format("%7d", orderId1).replace(" ", "0");
            } else {
                rightAppend = rightAppend.substring(rightAppend.length() - 7, rightAppend.length());
            }

            String orderId = "M" + newLocalDateTime + rightAppend;
            tOrderInfo.setInitiatorTime(now);
            tOrderInfo.setUpdateDt(now);
            tOrderInfo.setOrderTotAmt(payOrderVo.getAmt());
            tOrderInfo.setTxnTotAmt(payOrderVo.getTxnAmt());
            tOrderInfo.setPaymentMode(payOrderVo.getPaymentMode());
            tOrderInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
            tOrderInfo.setMainOrderId("M" + newLocalDateTime + rightAppend);
            tOrderInfo.setPartModeId(partModeId);

            try {
                int insert = tOrderInfoMapper.insert(tOrderInfo);
                if (1 != insert) {
                    log.error("主订单表新增失败 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_INSERT_ERROR.getCode(), MAIN_ORDER_INSERT_ERROR.getMessage());
                }
            } catch (DataAccessException e) {
                throw new RuntimeException("数据库主订单新增异常");
            }

            HashMap<String, BigDecimal> hashMap = new HashMap<>();

            String hsbpriNm = null;
            String hsbprjId = null;
            if (additAmt!=null && additAmt.compareTo(BigDecimal.ZERO) > 0){
                hsbpriNm = baseMapper.getHsbpriNm();
                if (StringUtil.isNullOrEmpty(hsbpriNm)) {
                    log.error("hsbpriNm查询失败 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(),"hsbpriNm查询失败");
                }

                hsbprjId = baseMapper.getHsbprjId();
                if (StringUtil.isNullOrEmpty(hsbprjId)) {
                    log.error("hsbprjId查询失败 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDERID_PARAMETER_ERROE.getCode(),"hsbprjId查询失败");
                }
            }

            // 更改检查库存的方式
            for (TOrderDetailInfo tOrderDetailInfo : orderList) {

                if (tOrderDetailInfo.getOrderModel().equals("02")) {
                    QueryWrapper<TOrderGoodsInfo> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("ORDER_ID",tOrderDetailInfo.getOrderId());

                    List<TOrderGoodsInfo> tOrderGoodsInfos = tOrderGoodsInfoMapper.selectList(queryWrapper);
                    for (TOrderGoodsInfo tOrderGoodsInfo:tOrderGoodsInfos) {
                        // 检查库存
                        TProductInventoryInfo tProductInventoryInfo = new TProductInventoryInfo();
                        tProductInventoryInfo.setProvId(tOrderDetailInfo.getProvId());
                        tProductInventoryInfo.setPrdctId(tOrderGoodsInfo.getPrdctId());

                        QueryWrapper<TProductInventoryInfo> queryWrapper5 = new QueryWrapper<>();
                        queryWrapper5.eq("PROV_ID", tProductInventoryInfo.getProvId());
                        queryWrapper5.eq("PRDCT_ID", tProductInventoryInfo.getPrdctId());
                        queryWrapper5.last("for update");

                        TProductInventoryInfo resTProductInventoryInfo = tProductInventoryInfoMapper.selectOne(queryWrapper5);
                        if (resTProductInventoryInfo == null) {
                            log.error("库存检查失败 + " + JSON.toJSONString(payOrderVo));
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(PRODUCT_INVENTORY_ERROR.getCode(), PRODUCT_INVENTORY_ERROR.getMessage());
                        }

                        BigDecimal bigDecimal2 = hashMap.get(tProductInventoryInfo.getProvId() + "****" + tOrderGoodsInfo.getPrdctId());
                        BigDecimal resStock = resTProductInventoryInfo.getStock();
                        if (null != bigDecimal2) {
                            resStock = bigDecimal2;
                        }

//                        if (resStock.compareTo(tOrderGoodsInfo.getPrdctWeight()) == -1) {
//                            log.error("库存不足 + " + JSON.toJSONString(payOrderVo));
//                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                            return Result.build(PRODUCT_INVENTORY_ERROR.getCode(), PRODUCT_INVENTORY_ERROR.getMessage());
//                        }

                        hashMap.put(tProductInventoryInfo.getProvId() + "****" + tOrderGoodsInfo.getPrdctId(), resTProductInventoryInfo.getStock().subtract(tOrderGoodsInfo.getPrdctWeight()));
                    }
                }

                String wuliuyuanProvId = baseMapper.getWuliuyuanProvId();
                if (null == wuliuyuanProvId) {
                    log.error("查不到市场方ＩＤ + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(GET_WULIUYUANPROV_ERROR.getCode(), GET_WULIUYUANPROV_ERROR.getMessage());
                }

                for (int i = 0; i < 2; i++) {
                    // 插入关联表
                    // 先生成推送惠市宝订单号

                    Map map2 = baseMapper.queryHSBOrderId();
                    Integer orderId2 = (Integer) map2.get("hsbOrderId");
                    if (null == orderId2) {
                        log.error("获取ＨＳＢＩＤ失败 + " + JSON.toJSONString(payOrderVo));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(MAIN_GET_MAINID_ERROR.getCode(), MAIN_GET_MAINID_ERROR.getMessage());
                    }

                    String rightAppend1 = String.valueOf(orderId2);
                    if (rightAppend1.length() < 7) {
                        rightAppend1 = String.format("%7d", orderId2).replace(" ", "0");
                    } else {
                        rightAppend1 = rightAppend1.substring(rightAppend1.length() - 7, rightAppend1.length());
                    }

                    TOrderRelevancyInfo tOrderRelevancyInfo = new TOrderRelevancyInfo();
                    tOrderRelevancyInfo.setMainOrderId(orderId);
                    tOrderRelevancyInfo.setOrderId(tOrderDetailInfo.getOrderId());
                    tOrderRelevancyInfo.setCustOrderId("H" + newLocalDateTime + rightAppend1);
                    tOrderRelevancyInfo.setUpdateDt(now);
                    tOrderRelevancyInfo.setType(String.valueOf(i));

                    TOrderInfo2 tOrderInfo2 = new TOrderInfo2();
                    tOrderInfo2.setProvId(tOrderDetailInfo.getProvId());
                    DecimalFormat df = new DecimalFormat("0.00");

                    if (i == 0) {

                        // 一条数据都没插入
                        int insert1 = tOrderRelevancyInfoMapper.insert(tOrderRelevancyInfo);
                        if (1 != insert1) {
                            log.error("关联表新增失败 + " + JSON.toJSONString(payOrderVo));
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ORDER_RELE_INSERT_ERROR.getCode(), ORDER_RELE_INSERT_ERROR.getMessage());
                        }

                        String HSBProvId = baseMapper.getHSBProvId(tOrderDetailInfo.getProvId());
                        if (StringUtil.isNullOrEmpty(HSBProvId)) {
                            log.error("商户关联表未查询到数据 + " + JSON.toJSONString(payOrderVo));
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(NOT_FOUND_MERCHANTCODE.getCode(), NOT_FOUND_MERCHANTCODE.getMessage());
                        }

                        tOrderInfo2.setProvId(HSBProvId);
                        tOrderInfo2.setTxnAmt(tOrderDetailInfo.getPartAmt().setScale(2, BigDecimal.ROUND_DOWN));
                        tOrderInfo2.setOrderAmt(tOrderDetailInfo.getPartAmt().setScale(2, BigDecimal.ROUND_DOWN));

                        if (additAmt!=null && additAmt.compareTo(BigDecimal.ZERO) > 0) {
                            ArrayList<Prj> prjs = new ArrayList<>();
                            Prj prj = new Prj();
                            prj.setItemNo(hsbprjId);
                            prj.setItemNm(hsbpriNm);
                            prj.setItemTp("2");
                            prj.setAmt(tOrderDetailInfo.getAdditAmt());
                            prjs.add(prj);
                            tOrderInfo2.setPrjList(prjs);
                            tOrderInfo2.setAdditAmt(tOrderDetailInfo.getAdditAmt());
                            tOrderInfo2.setOrderAmt(tOrderDetailInfo.getPartAmt().add(tOrderDetailInfo.getAdditAmt()));
                        }
                    }

                    if (i == 1) {

                        if (tOrderDetailInfo.getServChrg().compareTo(BigDecimal.ZERO) > 0) {

                            // 一条数据都没插入
                            int insert1 = tOrderRelevancyInfoMapper.insert(tOrderRelevancyInfo);
                            if (1 != insert1) {
                                log.error("关联表新增失败 + " + JSON.toJSONString(payOrderVo));
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ORDER_RELE_INSERT_ERROR.getCode(), ORDER_RELE_INSERT_ERROR.getMessage());
                            }
                        }

                        tOrderInfo2.setProvId(wuliuyuanProvId);
//                        tOrderInfo2.setAdditAmt(new BigDecimal("0.00"));
                        tOrderInfo2.setTxnAmt(tOrderDetailInfo.getServChrg().setScale(2, BigDecimal.ROUND_DOWN));
                        tOrderInfo2.setOrderAmt(tOrderDetailInfo.getServChrg().setScale(2, BigDecimal.ROUND_DOWN));
                    }

                    tOrderInfo2.setSubOrderId(tOrderRelevancyInfo.getCustOrderId());

                    if (tOrderInfo2.getOrderAmt().compareTo(BigDecimal.ZERO) <= 0) {

                    } else {
                        objects.add(tOrderInfo2);
                    }

                }

            }

            LocalDateTime localDate = LocalDateTime.now();
            String yyyyMMdd = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String yyyyMMddHHmmssSSS = localDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            IdWorker idWorker = new IdWorker("1", "1");
            long nextId = idWorker.nextId();

            getOrderByParam.setSndDtTm(String.valueOf(yyyyMMddHHmmssSSS));
            getOrderByParam.setSndTraceId(String.valueOf(nextId));
            getOrderByParam.setVersion("4");
            getOrderByParam.setMainOrderId(tOrderInfo.getMainOrderId());
            getOrderByParam.setMarketId(baseMapper.getMarketId());
//            getOrderByParam.setPaymentMode(payOrderVo.getPaymentMode());
            getOrderByParam.setOrderType("04");
            getOrderByParam.setPaymentMode(payOrderVo.getPaymentMode());

            if (payOrderVo.getPaymentMode().equals("05")) {
                getOrderByParam.setAppId(payOrderVo.getAppId());
                getOrderByParam.setUserIdent(payOrderVo.getUserIdent());
            }

            if (payOrderVo.getPaymentMode().equals("06")) {
                // 银行编码
                getOrderByParam.setBankCd(payOrderVo.getBankCd());
                getOrderByParam.setPeriodsNum(payOrderVo.getPeriodsNum());
            }

            getOrderByParam.setList(objects);
            getOrderByParam.setCcyCd("156");

            if (additAmt != null && additAmt.compareTo(BigDecimal.ZERO) > 0) {
                getOrderByParam.setOrderTotAmt(payOrderVo.getTxnAmt());
                getOrderByParam.setTxnTotAmt(payOrderVo.getTxnAmt().subtract(additAmt));
            } else {
                getOrderByParam.setOrderTotAmt(payOrderVo.getTxnAmt());
                getOrderByParam.setTxnTotAmt(payOrderVo.getTxnAmt());
            }

            getOrderByParam.setConfirmDt(yyyyMMdd);
            System.out.println(getOrderByParam.toString());

            log.debug("给外联支付参数:" + JSON.toJSONString(getOrderByParam));
            // 调用外联支付
            // ...
            ResultHSBCallBack result = restTemplate.postForObject(SERVER_URL + "/outHsb/securityCenter/DISP20210096", getOrderByParam, ResultHSBCallBack.class);

            if (null != result) {
                if (!StringUtil.isNullOrEmpty(getOrderByParam.getAppId())) {
                    result.setAppId(getOrderByParam.getAppId());
                }

                if (!StringUtil.isNullOrEmpty(getOrderByParam.getUserIdent())) {
                    result.setUserIdent(getOrderByParam.getUserIdent());
                }

                if (!StringUtil.isNullOrEmpty(getOrderByParam.getBankCd())) {
                    result.setBankCd(getOrderByParam.getBankCd());
                }

                if (null != getOrderByParam.getPeriodsNum()) {
                    result.setPeriodsNum(getOrderByParam.getPeriodsNum());
                }
            }


            if (payOrderVo.getAdditAmt() != null) {
                result.setAdditAmt(payOrderVo.getAdditAmt());
            }

            log.debug("收到的响应报文:" + JSON.toJSONString(result));
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("result", result);
            objectObjectHashMap.put("mainOrderId", tOrderInfo.getMainOrderId());
            objectObjectHashMap.put("ant_id",payOrderVo.getAntId());


            return Result.ok(objectObjectHashMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Result<Object> secondStep(HashMap hashMap) {
        try {
            ResultHSBCallBack result = (ResultHSBCallBack) hashMap.get("result");
            System.out.println(result);
            if (result.getTxnSt().equals("00")) {

                // 查主订单表
                QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MAIN_ORDER_ID", result.getMainOrderNo());
                TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
                if (null == tOrderInfo) {
                    log.error("查询不到主订单 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_SELECT_ERROR.getCode(), MAIN_ORDER_SELECT_ERROR.getMessage());
                }

                // 更新主订单
                tOrderInfo.setPaymentUrl(result.getPaymentURL());
                tOrderInfo.setOederTimeout(result.getOrderTimeout());
                tOrderInfo.setPaymentTraceId(result.getPaymentTraceId());
                tOrderInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
                if (!StringUtil.isNullOrEmpty(result.getBankCd())) {
                    tOrderInfo.setBankCd(result.getBankCd());
                }

                if (!StringUtil.isNullOrEmpty(result.getAppId())) {
                    tOrderInfo.setAppId(result.getAppId());
                }

                if (!StringUtil.isNullOrEmpty(result.getUserIdent())) {
                    tOrderInfo.setUserIdent(result.getUserIdent());
                }

                tOrderInfo.setBankCd(result.getBankCd());
                tOrderInfo.setAppId(result.getAppId());
                tOrderInfo.setUserIdent(result.getAppId());

                if (result.getPayQrCode() != null) {
//                    tOrderInfo.set
                }

                if (result.getReturnParmData() != null) {
                    tOrderInfo.setPayemtnParm(result.getReturnParmData());
                }

                //获取支付参数
//                tOrderInfo.setPayemtnParm(result.get);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
//                String tmp = simpleDateFormat.format(result.getSndDtTm());
//                tOrderInfo.setInitiatorTime(simpleDateFormat.parse(result.getSndDtTm()));

                UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("MAIN_ORDER_ID", tOrderInfo.getMainOrderId());
                int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
                if (1 != update) {
                    log.error("更新主订单表失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_UPDATE_ERROR.getCode(), MAIN_ORDER_UPDATE_ERROR.getMessage());
                }

                String kucunOrderId = "";
                String purchIdNew = null;
                for (ResultHSBZiOrder resultHSBZiOrder : result.getOrderLists()) {
                    String HSB = resultHSBZiOrder.getSubOrderId();

                    // 更新关联表
                    QueryWrapper<TOrderRelevancyInfo> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("CUST_ORDER_ID", HSB);
                    TOrderRelevancyInfo tOrderRelevancyInfo = tOrderRelevancyInfoMapper.selectOne(queryWrapper1);
                    if (null == tOrderRelevancyInfo) {
                        log.error("查不到关联表数据 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_RELE_SELECT_ERROR.getCode(), ORDER_RELE_SELECT_ERROR.getMessage());
                    }

                    tOrderRelevancyInfo.setCcbOrderDetailId(resultHSBZiOrder.getCcbSubOrderNo());
                    tOrderRelevancyInfo.setCcbOrderId(result.getCcbMainOrderNo());

                    UpdateWrapper<TOrderRelevancyInfo> updateWrapper1 = new UpdateWrapper<>();
                    updateWrapper1.eq("CUST_ORDER_ID", HSB);
                    int update1 = tOrderRelevancyInfoMapper.update(tOrderRelevancyInfo, updateWrapper1);
                    if (1 != update1) {
                        log.error("更新关联表失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_RELE_UPDATE_ERROR.getCode(), ORDER_RELE_UPDATE_ERROR.getMessage());
                    }

                    // 查子订单表
                    QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                    TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
                    if (null == tOrderDetailInfo) {
                        log.error("查不到子订单表 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_SELECT_ERROR.getCode(), ORDER_SELECT_ERROR.getMessage());
                    }

                    purchIdNew = tOrderDetailInfo.getPurchId();

                    // 更新子订单表为处理中
                    UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
                    updateWrapper2.eq("ORDER_ID", tOrderDetailInfo.getOrderId());
                    tOrderDetailInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
                    tOrderDetailInfo.setMainOrderId(result.getMainOrderNo());
                    int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
                    if (1 != update2) {
                        log.error("更新子订单表失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
                    }

                    // 相同的子订单 库存只减少1次
                    // 第一次进来时空，赋值
                    if (StringUtil.isNullOrEmpty(kucunOrderId)) {
                        kucunOrderId = tOrderDetailInfo.getOrderId();
                        // 第二次进来相同，退出for
                    } else if (kucunOrderId.equals(tOrderDetailInfo.getOrderId())) {
                        continue;
                    } else {
                        // 避免库存减2次
                        kucunOrderId = tOrderDetailInfo.getOrderId();
                    }

                    if (tOrderDetailInfo.getOrderModel().equals("02")) {

                        QueryWrapper<TOrderGoodsInfo> queryWrapper5 = new QueryWrapper<>();
                        queryWrapper5.eq("ORDER_ID",tOrderDetailInfo.getOrderId());

                        List<TOrderGoodsInfo> tOrderGoodsInfos = tOrderGoodsInfoMapper.selectList(queryWrapper5);
                        for (TOrderGoodsInfo tOrderGoodsInfo:tOrderGoodsInfos) {
                            // 查询库存表
                            QueryWrapper<TProductInventoryInfo> queryWrapper3 = new QueryWrapper<>();
                            queryWrapper3.eq("PROV_ID", tOrderDetailInfo.getProvId());
                            queryWrapper3.eq("PRDCT_ID", tOrderGoodsInfo.getPrdctId());
                            queryWrapper3.last("for update");
                            TProductInventoryInfo tProductInventoryInfo = tProductInventoryInfoMapper.selectOne(queryWrapper3);
                            if (null == tProductInventoryInfo) {
                                log.error("查询不到库存 + " + JSON.toJSONString(result));
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(PRODUCT_INVENTORY_ERROR.getCode(), PRODUCT_INVENTORY_ERROR.getMessage());
                            }

                            // 更新库存
                            UpdateWrapper<TProductInventoryInfo> updateWrapper3 = new UpdateWrapper<>();
                            updateWrapper3.eq("PROV_ID", tOrderDetailInfo.getProvId());
                            updateWrapper3.eq("PRDCT_ID", tOrderGoodsInfo.getPrdctId());
                            tProductInventoryInfo.setStock(tProductInventoryInfo.getStock().subtract(tOrderGoodsInfo.getPrdctWeight()));
                            int update3 = tProductInventoryInfoMapper.update(tProductInventoryInfo, updateWrapper3);
                            if (update3 < 0) {
                                log.error("更新库存失败 + " + JSON.toJSONString(result));
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ORDER_INVENTORY_UPDATE_ERROR.getCode(), ORDER_INVENTORY_UPDATE_ERROR.getMessage());
                            }
                        }

                        kucunOrderId = tOrderDetailInfo.getOrderId();
                    }
                }

                String antId = (String) hashMap.get("ant_id");
                if (!StringUtil.isNullOrEmpty(antId)) {
                    TDiscountsUser tDiscountsUser = new TDiscountsUser();
                    tDiscountsUser.setActId(Integer.parseInt(antId));
                    tDiscountsUser.setCreatTime(new Date());
                    tDiscountsUser.setProvId(purchIdNew);
                    tDiscountsUser.setDiscntDt(new Date());
                    tDiscountsUser.setDiscntAmt(result.getAdditAmt());

                    int insert = tDiscountsUserMapper.insert(tDiscountsUser);
                    if (insert != 1) {
                        log.error("插入活动表失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ACT_INSERT_ERROR.getCode(), ACT_INSERT_ERROR.getMessage());
                    }
                }

                HashMap<String, String> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("url", result.getPaymentURL());
                objectObjectHashMap.put("deskUrl", result.getCashDeskURL());
                objectObjectHashMap.put("qrCode", result.getPayQrCode());
                objectObjectHashMap.put("param", result.getReturnParmData());
                log.debug(objectObjectHashMap.toString());
                return Result.ok(objectObjectHashMap);

            } else {
                // 查主订单表
                String mainOrderId = (String) hashMap.get("mainOrderId");
                QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MAIN_ORDER_ID", mainOrderId);
                TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
                if (null == tOrderInfo) {
                    log.error("查询不到主订单 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_SELECT_ERROR.getCode(), MAIN_ORDER_SELECT_ERROR.getMessage());
                }

                // 更新主订单
                tOrderInfo.setPaymentUrl(result.getPaymentURL());
                tOrderInfo.setOrderSt(OrderStatusEnum.FAIL.getOrderStatus());

                UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("MAIN_ORDER_ID", tOrderInfo.getMainOrderId());
                int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
                if (1 != update) {
                    log.error("更新主订单失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_UPDATE_ERROR.getCode(), MAIN_ORDER_UPDATE_ERROR.getMessage());
                }

                // 查找关联表
                QueryWrapper<TOrderRelevancyInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("MAIN_ORDER_ID", mainOrderId);
                List<TOrderRelevancyInfo> tOrderRelevancyInfos = tOrderRelevancyInfoMapper.selectList(queryWrapper1);
                if (tOrderRelevancyInfos.size() == 0) {
                    log.error("查询不到关联表 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_RELE_SELECT_ERROR.getCode(), ORDER_RELE_SELECT_ERROR.getMessage());
                }

                for (TOrderRelevancyInfo tOrderRelevancyInfo : tOrderRelevancyInfos) {
                    // 查子订单表
                    QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                    TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
                    if (null == tOrderDetailInfo) {
                        log.error("查询不到子订单 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_SELECT_ERROR.getCode(), ORDER_SELECT_ERROR.getMessage());

                    }

                    // 更新子订单表为未支付
                    UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
                    updateWrapper2.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                    tOrderDetailInfo.setOrderSt(OrderStatusEnum.FAIL.getOrderStatus());
                    tOrderDetailInfo.setMainOrderId(mainOrderId);
                    int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
                    if (1 != update2) {
                        log.error("更新子订单失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
                    }
                }

                return Result.build(FROM_HSB_OUT_ERROR.getCode(), result.getErrCode() + "" + result.getErrMsg());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultToHSBOut InsertFile(File file) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String dateNow = dateTimeFormatter.format(LocalDateTime.now());
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            TFileInfo tFileInfo = new TFileInfo();

            DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
            tFileInfo.setFileName(file.getName());
            tFileInfo.setFileStatus("0");
            tFileInfo.setUpdateTime(new Date());

            fileInputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(ch);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();
            tFileInfo.setFileData(bytes);
            int inserttemp = tFileInfoMapper.insert(tFileInfo);
            if (1 != inserttemp) {
                log.error("文件插入数据库失败");
                fileInputStream.close();
                byteArrayOutputStream.close();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultToHSBOut.build("01", dateNow, String.valueOf(FILE_INSERT_FAIL.getCode()), FILE_INSERT_FAIL.getMessage());
            }

            fileInputStream.close();
            byteArrayOutputStream.close();
            return ResultToHSBOut.build("00", dateNow, null, null);

        } catch (Exception e) {
            try {
                fileInputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                throw new RuntimeException(ioException);
            }

            file.delete();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultToHSBOut.build("01", dateNow, "203", e.getMessage());
        }

//        return null;
    }

    @Transactional
    @Override
    public Result<Object> commitToHSB(AddOrdersByParam addOrdersByParam) {
        log.debug("下单前操作 +" + JSON.toJSONString(addOrdersByParam));

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getTxnType())) {
            log.error("交易类型不能为空 + " + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易类型不能为空");
        }

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getTransMd())) {
            log.error("交易方式不能为空 + " + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易方式不能为空");
        }

        if (addOrdersByParam.getTxnAmt() == null || addOrdersByParam.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
            log.error("交易金额异常 + " + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易金额异常");
        }

        if (StringUtil.isNullOrEmpty(addOrdersByParam.getPayerNo())) {
            log.error("付款人编号不能为空 + " + JSON.toJSONString(addOrdersByParam));
            return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "付款人编号不能为空");
        }


        List<TOrderDetailInfo> orderList1 = new ArrayList<>();
        TOrderDetailInfo tOrderDetailInfo10 = new TOrderDetailInfo();
        tOrderDetailInfo10.setPurchId(addOrdersByParam.getPayerNo());
        orderList1.add(tOrderDetailInfo10);
        addOrdersByParam.setOrderList(orderList1);

        String wuliuyuanOtherId = null;

        try {

            if (addOrdersByParam.getTxnType().equals("0")) {
                wuliuyuanOtherId = baseMapper.getWuliuyuanProvId2();
            } else {
                wuliuyuanOtherId = baseMapper.getWuliuyuanProvId();
            }

            if (StringUtil.isNullOrEmpty(wuliuyuanOtherId)) {
                log.error("查不到物流园收款商家ID + " + JSON.toJSONString(addOrdersByParam));
                return Result.build(WULIUYUAN2_SELECT_ERROR.getCode(), WULIUYUAN2_SELECT_ERROR.getMessage());
            }
        } catch (Exception e) {
            log.error("系统错误", e);
            throw new RuntimeException(e);
        }
//        List<TOrderDetailInfo> orderList1 = addOrdersByParam.getOrderList();
//        if (0 == orderList1.size()) {
//            log.error("没有接收到子订单 + " + JSON.toJSONString(addOrdersByParam));
//            return Result.build(ORDER_COUNT_ERROR.getCode(), ORDER_COUNT_ERROR.getMessage());
//        }

        List list = new ArrayList();

        String orderIdOut = null;
        for (TOrderDetailInfo tOrderDetailInfo : orderList1) {
            if (StringUtil.isNullOrEmpty(tOrderDetailInfo.getPurchId())) {
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "采购商ID不能为空");
            }
//            if (tOrderDetailInfo.getPrdctAmt() == null || (tOrderDetailInfo.getPrdctAmt().compareTo(BigDecimal.ZERO) == -1)) {
//                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "订单金额不能小于0");
//            }
//            if (tOrderDetailInfo.getTxnAmt() == null || tOrderDetailInfo.getTxnAmt().compareTo(BigDecimal.ZERO) == -1) {
//                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "交易金额不能小于0");
//            }

            tOrderDetailInfo.setPrdctAmt(addOrdersByParam.getTxnAmt());
            tOrderDetailInfo.setTxnAmt(addOrdersByParam.getTxnAmt());
            tOrderDetailInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
            tOrderDetailInfo.setProvId(wuliuyuanOtherId);

            // 订单模式设为01
            tOrderDetailInfo.setOrderModel("01");

            // 产品ID
//            tOrderDetailInfo.setPrdctId(addOrdersByParam.trans());

            // 设置其他交易物流园ID



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

            tOrderDetailInfo.setOrderSt(OrderStatusEnum.UNPAYS.getOrderStatus());
            String orderId = "Z" + newLocalDateTime + rightAppend;
            tOrderDetailInfo.setOrderId(orderId);
            orderIdOut = orderId;
            tOrderDetailInfo.setOrderTm(new Date());
            tOrderDetailInfo.setUpdateDt(new Date());
            tOrderDetailInfo.setOrderTp(addOrdersByParam.getTxnType());

            try {
                int result = baseMapper.insert(tOrderDetailInfo);
                if (1 != result) {
                    log.error("插入数据库失败 + " + JSON.toJSONString(addOrdersByParam));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_INSERT_ERROR.getCode(), ORDER_INSERT_ERROR.getMessage());
                }

                list.add(tOrderDetailInfo);
            } catch (DataAccessException e) {
                log.error("系统错误", e);
                throw new RuntimeException(e);
            }
        }

        log.debug(JSON.toJSONString(list));

        PayOrderVo payOrderVo = new PayOrderVo();
        payOrderVo.setOrderList(list);
        payOrderVo.setAmt(addOrdersByParam.getOrderList().get(0).getPrdctAmt());
        payOrderVo.setTxnAmt(addOrdersByParam.getOrderList().get(0).getTxnAmt());
        payOrderVo.setPaymentMode(addOrdersByParam.getPaymentMode());
        payOrderVo.setAppId(addOrdersByParam.getAppId());
        payOrderVo.setUserIdent(addOrdersByParam.getUserIdent());

        // 子订单已创建成功
        log.debug("开始生成主订单" + payOrderVo.toString());

        // 专门测试用
//        payOrderVo.setPaymentMode("03");

        try {
            String partModeId = null;

            if (null == payOrderVo.getAmt() || payOrderVo.getAmt().compareTo(BigDecimal.ZERO) <= 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "总金额错误");
            }

            if (null == payOrderVo.getTxnAmt() || payOrderVo.getTxnAmt().compareTo(BigDecimal.ZERO) <= 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "总交易金额错误");
            }

//            if (StringUtil.isNullOrEmpty(payOrderVo.getPaymentMode())) {
//                return Result.build(ORDERID_PARAMETER_ERROE.getCode(), "支付方式不能为空");
//            }

            List<TOrderDetailInfo> orderList = payOrderVo.getOrderList();
            if (0 == orderList.size()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ORDER_COUNT_ERROR.getCode(), ORDER_COUNT_ERROR.getMessage());
            }

            ArrayList<TOrderInfo2> objects = new ArrayList<>();
            Date now = new Date();

            HashMap<String, BigDecimal> stringHashMap = new HashMap<>();

            for (int i = 0; i < payOrderVo.getOrderList().size(); i++) {
                QueryWrapper<TOrderDetailInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("ORDER_ID", payOrderVo.getOrderList().get(i).getOrderId());
                queryWrapper1.last("for update");
                TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper1);
                if (null == tOrderDetailInfo) {
                    log.error("没有查询到子订单 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_GETDETAIL.getCode(), ORDER_GETDETAIL.getMessage());
                }

                // 不判断子订单状态了
                // 判断子订单状态
                if (!OrderStatusEnum.UNPAYS.getOrderStatus().equals(tOrderDetailInfo.getOrderSt())) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_STATUS_ERROE.getCode(), ORDER_STATUS_ERROE.getMessage());
                }

                // 查询分账模式
//                QueryWrapper<TPartModeType> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("STATUS", "0");
//                List<TPartModeType> tPartModeTypes = tPartModeTypeMapper.selectList(queryWrapper);
//                if (1 != tPartModeTypes.size()) {
//                    log.error("没有查询到分账模式 + " + JSON.toJSONString(payOrderVo));
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return Result.build(PART_MODEL_FAIL.getCode(), PART_MODEL_FAIL.getMessage());
//                }
//
//                // 更新子订单表到list里，与数据库无关
//                TPartModeType tPartModeType = tPartModeTypes.get(0);
//                partModeId = tPartModeType.getPartMdId();
//
//                // 比例分账
//                if (tPartModeType.getPartMdTp().equals("0")) {
//
//                    // 比例大于1
//                    if ((new BigDecimal("1.00")).compareTo(tPartModeType.getPartMdVal()) == -1) {
//                        log.error("分账模式比例大于１ + " + JSON.toJSONString(payOrderVo));
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                        return Result.build(BILI_TOO_BIG.getCode(), BILI_TOO_BIG.getMessage());
//                    }
//
//                    BigDecimal allMoney = tOrderDetailInfo.getTxnAmt();
//                    BigDecimal servChag = allMoney.multiply(tPartModeType.getPartMdVal());
//                    servChag = servChag.setScale(2, BigDecimal.ROUND_DOWN);
//                    BigDecimal partAmt = allMoney.subtract(servChag);
//                    tOrderDetailInfo.setPartAmt(partAmt);
//                    tOrderDetailInfo.setServChrg(servChag);
//                }
//
//                // 固定分账
//                if (tPartModeType.getPartMdTp().equals("1")) {
//
//                    // 分成大于现在金额报错
//                    if (tOrderDetailInfo.getTxnAmt().compareTo(tPartModeType.getPartMdVal()) == -1) {
//                        log.error("分账金额太大 + " + JSON.toJSONString(payOrderVo));
//                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                        return Result.build(MONEY_TOO_BIG.getCode(), MONEY_TOO_BIG.getMessage());
//                    }
//
//                    BigDecimal allMoney = tOrderDetailInfo.getTxnAmt();
//                    BigDecimal servChag = tPartModeType.getPartMdVal();
//                    BigDecimal partAmt = allMoney.subtract(tPartModeType.getPartMdVal());
//                    tOrderDetailInfo.setPartAmt(partAmt);
//                    tOrderDetailInfo.setServChrg(servChag);
//                }
                tOrderDetailInfo.setPartAmt(tOrderDetailInfo.getTxnAmt());
                UpdateWrapper<TOrderDetailInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("ORDER_ID", tOrderDetailInfo.getOrderId());
                tOrderDetailInfo.setTxnDt(now);
                tOrderDetailInfo.setUpdateDt(now);
//                tOrderDetailInfo.setMainOrderId();

                int update1 = baseMapper.update(tOrderDetailInfo, updateWrapper);
                if (update1 == 0) {
                    log.error("更新子订单失败 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_INVENTORY_UPDATE_ERROR.getCode(), ORDER_INVENTORY_UPDATE_ERROR.getMessage());
                }

                // 用数据库最新的值替代当前的数组
                payOrderVo.getOrderList().set(i, tOrderDetailInfo);

            }
            // 生成主订单表
            TOrderInfo tOrderInfo = new TOrderInfo();

            Map map1 = baseMapper.queryMainOrderId();
            Integer orderId1 = (Integer) map1.get("mainOrderId");
            if (null == orderId1) {
                log.error("查不到分布式住订单ＩＤ + " + JSON.toJSONString(payOrderVo));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(MAIN_GET_MAINID_ERROR.getCode(), MAIN_GET_MAINID_ERROR.getMessage());
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime localDateTime = LocalDateTime.now();
            String newLocalDateTime = localDateTime.format(dtf);
            String rightAppend = String.valueOf(orderId1);

            if (rightAppend.length() < 7) {
                rightAppend = String.format("%7d", orderId1).replace(" ", "0");
            } else {
                rightAppend = rightAppend.substring(rightAppend.length() - 7, rightAppend.length());
            }

            String orderId = "M" + newLocalDateTime + rightAppend;
            tOrderInfo.setInitiatorTime(now);
            tOrderInfo.setUpdateDt(now);
            tOrderInfo.setOrderTotAmt(payOrderVo.getAmt());
            tOrderInfo.setTxnTotAmt(payOrderVo.getTxnAmt());
            tOrderInfo.setPaymentMode(payOrderVo.getPaymentMode());
            tOrderInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
            tOrderInfo.setMainOrderId("M" + newLocalDateTime + rightAppend);
            tOrderInfo.setPartModeId(partModeId);

            try {
                int insert = tOrderInfoMapper.insert(tOrderInfo);
                if (1 != insert) {
                    log.error("主订单表新增失败 + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_INSERT_ERROR.getCode(), MAIN_ORDER_INSERT_ERROR.getMessage());
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("数据库主订单新增异常");
            }

            HashMap<String, BigDecimal> hashMap = new HashMap<>();
            for (TOrderDetailInfo tOrderDetailInfo : orderList) {

//                String wuliuyuanProvId = baseMapper.getWuliuyuanProvId();
//                if (null == wuliuyuanProvId) {
//                    log.error("查不到市场方ＩＤ + " + JSON.toJSONString(payOrderVo));
//                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                    return Result.build(GET_WULIUYUANPROV_ERROR.getCode(), GET_WULIUYUANPROV_ERROR.getMessage());
//                }

                for (int i = 0; i < 2; i++) {
                    // 插入关联表
                    // 先生成推送惠市宝订单号

                    Map map2 = baseMapper.queryHSBOrderId();
                    Integer orderId2 = (Integer) map2.get("hsbOrderId");
                    if (null == orderId2) {
                        log.error("获取ＨＳＢＩＤ失败 + " + JSON.toJSONString(payOrderVo));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(MAIN_GET_MAINID_ERROR.getCode(), MAIN_GET_MAINID_ERROR.getMessage());
                    }

                    String rightAppend1 = String.valueOf(orderId2);
                    if (rightAppend1.length() < 7) {
                        rightAppend1 = String.format("%7d", orderId2).replace(" ", "0");
                    } else {
                        rightAppend1 = rightAppend1.substring(rightAppend1.length() - 7, rightAppend1.length());
                    }

                    TOrderRelevancyInfo tOrderRelevancyInfo = new TOrderRelevancyInfo();
                    tOrderRelevancyInfo.setMainOrderId(orderId);
                    tOrderRelevancyInfo.setOrderId(tOrderDetailInfo.getOrderId());
                    tOrderRelevancyInfo.setCustOrderId("H" + newLocalDateTime + rightAppend1);
                    tOrderRelevancyInfo.setUpdateDt(now);
                    tOrderRelevancyInfo.setType(String.valueOf(i));

                    TOrderInfo2 tOrderInfo2 = new TOrderInfo2();
                    tOrderInfo2.setProvId(tOrderDetailInfo.getProvId());
                    DecimalFormat df = new DecimalFormat("0.00");

                    if (i == 0) {

                        // 一条数据都没插入
                        int insert1 = tOrderRelevancyInfoMapper.insert(tOrderRelevancyInfo);
                        if (1 != insert1) {
                            log.error("关联表新增失败 + " + JSON.toJSONString(payOrderVo));
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ORDER_RELE_INSERT_ERROR.getCode(), ORDER_RELE_INSERT_ERROR.getMessage());
                        }

//                        String HSBProvId = baseMapper.getHSBProvId(tOrderDetailInfo.getProvId());
//                        if (StringUtil.isNullOrEmpty(HSBProvId)) {
//                            log.error("商户关联表未查询到数据 + " + JSON.toJSONString(payOrderVo));
//                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                            return Result.build(NOT_FOUND_MERCHANTCODE.getCode(), NOT_FOUND_MERCHANTCODE.getMessage());
//                        }

                        tOrderInfo2.setProvId(wuliuyuanOtherId);
                        tOrderInfo2.setTxnAmt(tOrderDetailInfo.getPartAmt().setScale(2, BigDecimal.ROUND_DOWN));
                        tOrderInfo2.setOrderAmt(tOrderDetailInfo.getPartAmt().setScale(2, BigDecimal.ROUND_DOWN));

                        tOrderInfo2.setSubOrderId(tOrderRelevancyInfo.getCustOrderId());

                        if (tOrderInfo2.getOrderAmt().compareTo(BigDecimal.ZERO) <= 0) {

                        } else {
                            objects.add(tOrderInfo2);
                        }

                    }

//                    if (i == 1) {

//                        if (tOrderDetailInfo.getServChrg().compareTo(BigDecimal.ZERO) > 0) {
//
//                            // 一条数据都没插入
//                            int insert1 = tOrderRelevancyInfoMapper.insert(tOrderRelevancyInfo);
//                            if (1 != insert1) {
//                                log.error("关联表新增失败 + " + JSON.toJSONString(payOrderVo));
//                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                return Result.build(ORDER_RELE_INSERT_ERROR.getCode(), ORDER_RELE_INSERT_ERROR.getMessage());
//                            }
//                        }
//
//                        tOrderInfo2.setProvId(wuliuyuanProvId);
//                        tOrderInfo2.setTxnAmt(tOrderDetailInfo.getServChrg().setScale(2, BigDecimal.ROUND_DOWN));
//                        tOrderInfo2.setOrderAmt(tOrderDetailInfo.getServChrg().setScale(2, BigDecimal.ROUND_DOWN));
//                    }

                }

            }

            LocalDateTime localDate = LocalDateTime.now();
            String yyyyMMdd = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String yyyyMMddHHmmssSSS = localDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            IdWorker idWorker = new IdWorker("1", "1");
            long nextId = idWorker.nextId();

            // 退款
            if (addOrdersByParam.getTxnType().equals(WITHDRAW.getTxnType()) || addOrdersByParam.getTxnType().equals(IN_WITHDAW.getTxnType()) ||
            addOrdersByParam.getTxnType().equals(MCARD_WITHDRAW.getTxnType()) || addOrdersByParam.equals(CARFEE_PRE.getTxnType())) {
                WithDrawOrderByParam withDrawOrderByParam = new WithDrawOrderByParam();

                withDrawOrderByParam.setSndDtTm(String.valueOf(yyyyMMddHHmmssSSS));
                withDrawOrderByParam.setSndTraceId(String.valueOf(nextId));
                withDrawOrderByParam.setMarketId(baseMapper.getMarketId());

                String paymentTraceId = baseMapper.getPaymentTraceId(addOrdersByParam.getRefundMainOrderId());
                if (StringUtil.isNullOrEmpty(paymentTraceId)) {
                    log.error("没有查到paymentTraceId + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_RELE_INSERT_ERROR.getCode(), "没有查到paymentTraceId");
                }

                String ccbOrderDetailId = baseMapper.getSubOrderNo(addOrdersByParam.getRefundMainOrderId());
                if (StringUtil.isNullOrEmpty(ccbOrderDetailId)) {
                    log.error("没有查到ccbOrderId + " + JSON.toJSONString(payOrderVo));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_RELE_INSERT_ERROR.getCode(), "没有查到ccbOrderId");

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
                HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("result", result);
                objectObjectHashMap.put("mainOrderId", tOrderInfo.getMainOrderId());
                objectObjectHashMap.put("orderId", orderIdOut);
                objectObjectHashMap.put("params", addOrdersByParam);
                log.debug("惠市宝返回的map" + JSON.toJSONString(objectObjectHashMap));

                return Result.ok(objectObjectHashMap);

                // 除了退款的以外都是往HSB进钱
            } else {
                GetOrderByParam getOrderByParam = new GetOrderByParam();

                getOrderByParam.setSndDtTm(String.valueOf(yyyyMMddHHmmssSSS));
                getOrderByParam.setSndTraceId(String.valueOf(nextId));
                getOrderByParam.setVersion("4");
                getOrderByParam.setMainOrderId(tOrderInfo.getMainOrderId());
                getOrderByParam.setMarketId(baseMapper.getMarketId());
//            getOrderByParam.setPaymentMode(payOrderVo.getPaymentMode());
                getOrderByParam.setOrderType("04");
                getOrderByParam.setPaymentMode(payOrderVo.getPaymentMode());

                if (payOrderVo.getPaymentMode().equals("05")) {
                    getOrderByParam.setAppId(payOrderVo.getAppId());
                    getOrderByParam.setUserIdent(payOrderVo.getUserIdent());
                }

                if (payOrderVo.getPaymentMode().equals("06")) {
                    // 银行编码
                    getOrderByParam.setBankCd(payOrderVo.getBankCd());
                    getOrderByParam.setPeriodsNum(payOrderVo.getPeriodsNum());
                }

                getOrderByParam.setList(objects);
                getOrderByParam.setCcyCd("156");
                getOrderByParam.setOrderTotAmt(payOrderVo.getTxnAmt());
                getOrderByParam.setTxnTotAmt(payOrderVo.getTxnAmt());

                getOrderByParam.setConfirmDt(yyyyMMdd);
                System.out.println(getOrderByParam.toString());

                log.debug("给外联支付参数:" + JSON.toJSONString(getOrderByParam));
                // 调用外联支付
                // ...
                ResultHSBCallBack result = restTemplate.postForObject(SERVER_URL + "/outHsb/securityCenter/DISP20210096", getOrderByParam, ResultHSBCallBack.class);

                if (null != result) {
                    if (!StringUtil.isNullOrEmpty(getOrderByParam.getAppId())) {
                        result.setAppId(getOrderByParam.getAppId());
                    }

                    if (!StringUtil.isNullOrEmpty(getOrderByParam.getUserIdent())) {
                        result.setUserIdent(getOrderByParam.getUserIdent());
                    }

                    if (!StringUtil.isNullOrEmpty(getOrderByParam.getBankCd())) {
                        result.setBankCd(getOrderByParam.getBankCd());
                    }

                    if (null != getOrderByParam.getPeriodsNum()) {
                        result.setPeriodsNum(getOrderByParam.getPeriodsNum());
                    }
                }

                log.debug("收到的响应报文:" + JSON.toJSONString(result));
                HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("result", result);
                objectObjectHashMap.put("mainOrderId", tOrderInfo.getMainOrderId());
                objectObjectHashMap.put("returnOrderId",orderIdOut);
                objectObjectHashMap.put("params", addOrdersByParam);

                return Result.ok(objectObjectHashMap);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Result<Object> returnToHSB(HashMap hashMap) {
        try {
            ResultHSBCallBack result = (ResultHSBCallBack) hashMap.get("result");
            AddOrdersByParam addOrdersByParam = (AddOrdersByParam) hashMap.get("params");
            System.out.println(result);

            if (result.getTxnSt().equals("00")) {

                // 查主订单表
                QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MAIN_ORDER_ID", result.getMainOrderNo());
                TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
                if (null == tOrderInfo) {
                    log.error("查询不到主订单 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_SELECT_ERROR.getCode(), MAIN_ORDER_SELECT_ERROR.getMessage());
                }

                // 更新主订单
                tOrderInfo.setPaymentUrl(result.getPaymentURL());
                tOrderInfo.setOederTimeout(result.getOrderTimeout());
                tOrderInfo.setPaymentTraceId(result.getPaymentTraceId());
                tOrderInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());

//                if (addOrdersByParam)

                if (!StringUtil.isNullOrEmpty(result.getBankCd())) {
                    tOrderInfo.setBankCd(result.getBankCd());
                }

                if (!StringUtil.isNullOrEmpty(result.getAppId())) {
                    tOrderInfo.setAppId(result.getAppId());
                }

                if (!StringUtil.isNullOrEmpty(result.getUserIdent())) {
                    tOrderInfo.setUserIdent(result.getUserIdent());
                }

                tOrderInfo.setBankCd(result.getBankCd());
                tOrderInfo.setAppId(result.getAppId());
                tOrderInfo.setUserIdent(result.getAppId());


                if (result.getPayQrCode() != null) {
//                    tOrderInfo.set
                }

                if (result.getReturnParmData() != null) {
                    tOrderInfo.setPayemtnParm(result.getReturnParmData());
                }

                if (result.getRefundTraceId() != null) {
                    tOrderInfo.setPaymentTraceId(result.getRefundTraceId());
                }

                //获取支付参数
//                tOrderInfo.setPayemtnParm(result.get);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
//                String tmp = simpleDateFormat.format(result.getSndDtTm());
//                tOrderInfo.setInitiatorTime(simpleDateFormat.parse(result.getSndDtTm()));

                UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("MAIN_ORDER_ID", tOrderInfo.getMainOrderId());
                int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
                if (1 != update) {
                    log.error("更新子订单表失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_UPDATE_ERROR.getCode(), MAIN_ORDER_UPDATE_ERROR.getMessage());
                }

                for (ResultHSBZiOrder resultHSBZiOrder : result.getOrderLists()) {
                    String HSB = resultHSBZiOrder.getSubOrderId();

                    // 更新关联表
                    QueryWrapper<TOrderRelevancyInfo> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("CUST_ORDER_ID", HSB);
                    TOrderRelevancyInfo tOrderRelevancyInfo = tOrderRelevancyInfoMapper.selectOne(queryWrapper1);
                    if (null == tOrderRelevancyInfo) {
                        log.error("查不到关联表数据 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_RELE_SELECT_ERROR.getCode(), ORDER_RELE_SELECT_ERROR.getMessage());
                    }

                    tOrderRelevancyInfo.setCcbOrderDetailId(resultHSBZiOrder.getCcbSubOrderNo());
                    tOrderRelevancyInfo.setCcbOrderId(result.getCcbMainOrderNo());

                    UpdateWrapper<TOrderRelevancyInfo> updateWrapper1 = new UpdateWrapper<>();
                    updateWrapper1.eq("CUST_ORDER_ID", HSB);
                    int update1 = tOrderRelevancyInfoMapper.update(tOrderRelevancyInfo, updateWrapper1);
                    if (1 != update1) {
                        log.error("更新关联表失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_RELE_UPDATE_ERROR.getCode(), ORDER_RELE_UPDATE_ERROR.getMessage());
                    }

                    // 查子订单表
                    QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                    TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
                    if (null == tOrderDetailInfo) {
                        log.error("查不到子订单表 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_SELECT_ERROR.getCode(), ORDER_SELECT_ERROR.getMessage());
                    }

                    // 更新子订单表为处理中
                    UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
                    updateWrapper2.eq("ORDER_ID", tOrderDetailInfo.getOrderId());
                    tOrderDetailInfo.setOrderSt(OrderStatusEnum.FOR_HADNLE.getOrderStatus());
                    tOrderDetailInfo.setMainOrderId(result.getMainOrderNo());
                    int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
                    if (1 != update2) {
                        log.error("更新子订单表失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
                    }

                }

                return Result.ok();
            } else {
                // 查主订单表
                String mainOrderId = (String) hashMap.get("mainOrderId");
                QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MAIN_ORDER_ID", mainOrderId);
                TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
                if (null == tOrderInfo) {
                    log.error("查询不到主订单 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_SELECT_ERROR.getCode(), MAIN_ORDER_SELECT_ERROR.getMessage());
                }

                // 更新主订单
                tOrderInfo.setPaymentUrl(result.getPaymentURL());
                tOrderInfo.setOrderSt(OrderStatusEnum.FAIL.getOrderStatus());

                UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("MAIN_ORDER_ID", tOrderInfo.getMainOrderId());
                int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
                if (1 != update) {
                    log.error("更新主订单失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_UPDATE_ERROR.getCode(), MAIN_ORDER_UPDATE_ERROR.getMessage());
                }

                // 查找关联表
                QueryWrapper<TOrderRelevancyInfo> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("MAIN_ORDER_ID", mainOrderId);
                List<TOrderRelevancyInfo> tOrderRelevancyInfos = tOrderRelevancyInfoMapper.selectList(queryWrapper1);
                if (tOrderRelevancyInfos.size() == 0) {
                    log.error("查询不到关联表 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_RELE_SELECT_ERROR.getCode(), ORDER_RELE_SELECT_ERROR.getMessage());
                }

                for (TOrderRelevancyInfo tOrderRelevancyInfo : tOrderRelevancyInfos) {
                    // 查子订单表
                    QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                    TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
                    if (null == tOrderDetailInfo) {
                        log.error("查询不到子订单 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_SELECT_ERROR.getCode(), ORDER_SELECT_ERROR.getMessage());

                    }

                    // 更新子订单表为未支付
                    UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
                    updateWrapper2.eq("ORDER_ID", tOrderRelevancyInfo.getOrderId());
                    tOrderDetailInfo.setOrderSt(OrderStatusEnum.FAIL.getOrderStatus());
                    tOrderDetailInfo.setMainOrderId(mainOrderId);
                    int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
                    if (1 != update2) {
                        log.error("更新子订单失败 + " + JSON.toJSONString(result));
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
                    }
                }

                return Result.build(FROM_HSB_OUT_ERROR.getCode(), result.getErrCode() + "" + result.getErrMsg());
            }

        } catch (Exception e) {
            log.error("系统错误", e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public Result<Object> thirdStep(HashMap hashMap) {

        ResultHSBCallBack result = (ResultHSBCallBack) hashMap.get("result");
        AddOrdersByParam addOrdersByParam = (AddOrdersByParam) hashMap.get("params");
        String returnOrderId = (String) hashMap.get("returnOrderId");

        // 新增流水记录
        // 惠市宝回来的时候新增
        InsertPayJrnDTO insertPayJrnDTO = new InsertPayJrnDTO();
        BeanUtils.copyProperties(addOrdersByParam, insertPayJrnDTO);
        insertPayJrnDTO.setTxnAmt(addOrdersByParam.getTxnAmt());
//        insertPayJrnDTO.setMainOrderId(result.getMainOrderNo());

        // 如果mainOrderId有值，证明不是退款，是充值或者缴费
        if (StringUtil.isNullOrEmpty(result.getMainOrderNo())) {
            insertPayJrnDTO.setMainOrderId((String) hashMap.get("mainOrderId"));
        } else {
            insertPayJrnDTO.setMainOrderId(result.getMainOrderNo());
        }

        if (!StringUtil.isNullOrEmpty(result.getRefundSt())) {
            insertPayJrnDTO.setStatus(result.getRefundSt());
        }

        ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210275", insertPayJrnDTO, ReturnFromBusiness.class);

        if (result1 != null && result1.getCode() == 200) {
            // 退款订单返回
            if (result.getRefundTraceId() != null) {
                HashMap<String, String> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("refundTraceId", result.getRefundTraceId());
                objectObjectHashMap.put("customRefundTraceId", result.getCustomRefundTraceId());
                return Result.ok(objectObjectHashMap);
            } else {
                HashMap<String, String> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("url", result.getPaymentURL());
                objectObjectHashMap.put("deskUrl", result.getCashDeskURL());
                objectObjectHashMap.put("qrCode", result.getPayQrCode());
                objectObjectHashMap.put("param", result.getReturnParmData());
                objectObjectHashMap.put("orderId",returnOrderId);
                objectObjectHashMap.put("endTime",result.getOrderTimeout());
                log.debug(objectObjectHashMap.toString());
                return Result.ok(objectObjectHashMap);
            }

        } else {
            log.error("调用新增流水失败 + " + JSON.toJSONString(insertPayJrnDTO));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.build(REMOTE_INSERT_PAYJRN.getCode(), REMOTE_INSERT_PAYJRN.getMessage());
        }
    }

    @Transactional
    @Override
    public Result<Object> withDrawToHSB(HashMap hashMap) {
        try {
            ResultHSBCallBack result = (ResultHSBCallBack) hashMap.get("result");
            AddOrdersByParam addOrdersByParam = (AddOrdersByParam) hashMap.get("params");
            String mainOrderId = (String) hashMap.get("mainOrderId");
            String orderId = (String) hashMap.get("orderId");

            if (result.getTxnSt().equals("00")) {

                // 查主订单表
                QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MAIN_ORDER_ID", mainOrderId);
                TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
                if (null == tOrderInfo) {
                    log.error("查询不到主订单 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_SELECT_ERROR.getCode(), MAIN_ORDER_SELECT_ERROR.getMessage());
                }

                // 更新主订单
                tOrderInfo.setPaymentTraceId(result.getRefundTraceId());
                tOrderInfo.setOrderSt(result.getRefundSt());

                UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("MAIN_ORDER_ID", mainOrderId);
                int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
                if (1 != update) {
                    log.error("更新主订单表失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_UPDATE_ERROR.getCode(), MAIN_ORDER_UPDATE_ERROR.getMessage());
                }

                // 查子订单表
                QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("ORDER_ID", orderId);
                TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
                if (null == tOrderDetailInfo) {
                    log.error("查不到子订单表 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_SELECT_ERROR.getCode(), ORDER_SELECT_ERROR.getMessage());
                }

                // 更新子订单表
                UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
                updateWrapper2.eq("ORDER_ID", orderId);
                tOrderDetailInfo.setOrderSt(result.getRefundSt());
                tOrderDetailInfo.setMainOrderId(mainOrderId);
                int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
                if (1 != update2) {
                    log.error("更新子订单表失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
                }

                HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("mainOrderId", mainOrderId);
                objectObjectHashMap.put("params", addOrdersByParam);
                objectObjectHashMap.put("result", result);

                return Result.ok(objectObjectHashMap);
            } else {
                // 查主订单表
                QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MAIN_ORDER_ID", mainOrderId);
                TOrderInfo tOrderInfo = tOrderInfoMapper.selectOne(queryWrapper);
                if (null == tOrderInfo) {
                    log.error("查询不到主订单 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_SELECT_ERROR.getCode(), MAIN_ORDER_SELECT_ERROR.getMessage());
                }

                // 更新主订单
//                tOrderInfo.setPaymentTraceId(result.getRefundTraceId());
                tOrderInfo.setOrderSt(OrderStatusEnum.FAIL.getOrderStatus());

                UpdateWrapper<TOrderInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("MAIN_ORDER_ID", mainOrderId);
                int update = tOrderInfoMapper.update(tOrderInfo, updateWrapper);
                if (1 != update) {
                    log.error("更新主订单表失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(MAIN_ORDER_UPDATE_ERROR.getCode(), MAIN_ORDER_UPDATE_ERROR.getMessage());
                }

                // 查子订单表
                QueryWrapper<TOrderDetailInfo> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("ORDER_ID", orderId);
                TOrderDetailInfo tOrderDetailInfo = baseMapper.selectOne(queryWrapper2);
                if (null == tOrderDetailInfo) {
                    log.error("查不到子订单表 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_SELECT_ERROR.getCode(), ORDER_SELECT_ERROR.getMessage());
                }

                // 更新子订单表
                UpdateWrapper<TOrderDetailInfo> updateWrapper2 = new UpdateWrapper<>();
                updateWrapper2.eq("ORDER_ID", orderId);
                tOrderDetailInfo.setOrderSt(OrderStatusEnum.FAIL.getOrderStatus());
                tOrderDetailInfo.setMainOrderId(mainOrderId);
                int update2 = baseMapper.update(tOrderDetailInfo, updateWrapper2);
                if (1 != update2) {
                    log.error("更新子订单表失败 + " + JSON.toJSONString(result));
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Result.build(ORDER_UPDATE_ERROR.getCode(), ORDER_UPDATE_ERROR.getMessage());
                }

                return Result.build(FROM_HSB_OUT_ERROR.getCode(), result.getErrCode() + "" + result.getErrMsg());
            }

        } catch (Exception e) {
            log.error("系统错误", e);
            throw new RuntimeException(e);
        }
    }
}
