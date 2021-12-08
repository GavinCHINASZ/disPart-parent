package com.disPart.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.disPart.dao.*;
//import com.disPart.dao.mapper.auto.dao.BaseMerchantDao;
import com.dispart.model.order.TReconciliationResultInfo;
import com.dispart.dto.orderdto.ReturnOrderResultDTO;
import com.dispart.model.order.*;
import com.dispart.result.ResultToHSBOut;
import com.dispart.util.IdWorker;
import com.dispart.vo.basevo.BaseMerchantVo;
import com.dispart.vo.order.QueryOrderResultVo;
import com.dispart.vo.order.ResultOrderCallBackVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author:xts
 * @date:Created in 2021/6/19 3:53
 * @description 客户信息自动任务
 * @modified by:
 * @version: 1.0
 */
@Component
@Slf4j
public class CustomTask {
    @Value("${pushUrl}")
    private String pushUrl;

//    @Autowired
//    private BaseMerchantDao baseMerchantDao;

    @Autowired
    private TApportionDetailsInfoMapper tApportionDetailsInfoMapper;

    @Autowired
    private TApportionSumInfoMapper tApportionSumInfoMapper;

    @Autowired
    private TWithholdDetailsInfoMapper tWithholdDetailsInfoMapper;

    @Autowired
    private TReconciliationDetailsInfoMapper tReconciliationDetailsInfoMapper;

    @Autowired
    private TFileInfoMapper tFileInfoMapper;

    @Autowired
    private TOrderDetailInfoMapper tOrderDetailInfoMapper;

    @Autowired
    private TOrderInfoMapper tOrderInfoMapper;

    @Autowired
    private TOrderRelevancyInfoMapper tOrderRelevancyInfoMapper;

    @Autowired
    private TProductInventoryInfoMapper tProductInventoryInfoMapper;

    @Autowired
    private TReconciliationResultInfoMapper tReconciliationResultInfoMapper;

    @Autowired
    @Qualifier("restTemplate1")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;


    //上一次执行开始时间点之后30分钟在执行
    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void analysisFileAndInsert() {

//        // 查询数据库未解析的文件
//        QueryWrapper<TFileInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("FILE_STATUS", "0");
//        List<TFileInfo> tFileInfos = null;
//
//        try {
//            tFileInfos = tFileInfoMapper.selectList(queryWrapper);
//            if (null == tFileInfos || 0 == tFileInfos.size()) {
//                log.info("未找到未解析的文件");
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (TFileInfo tFileInfo : tFileInfos) {
//            // 字节转文件
//            byte[] fileData = tFileInfo.getFileData();
//            File newFile;
//            BufferedOutputStream bufferedOutputStream;
//            FileOutputStream fileOutputStream;
//
//            newFile = new File(tFileInfo.getFileName());
//
//            try {
//                fileOutputStream = new FileOutputStream(newFile);
//                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//                bufferedOutputStream.write(fileData);
//                bufferedOutputStream.flush();
//
//                // 关闭流
//                fileOutputStream.close();
//                bufferedOutputStream.close();
//            } catch (IOException e) {
//                // 异常删除本地文件
//                e.printStackTrace();
//                delteTempFile(newFile);
//            }
//
//            // 压缩文件相关
//            ZipInputStream zin;
//            ZipFile zipFile;
//            FileInputStream fileInputStream;
//
//            //创建压缩文件对象
//            try {
//                zipFile = new ZipFile(newFile);
//                fileInputStream = new FileInputStream(newFile);
//
//                //实例化对象，指明要解压的文件
//                zin = new ZipInputStream(fileInputStream);
//                ZipEntry entry;
//
//                while (((entry = zin.getNextEntry()) != null) && !entry.isDirectory()) {
//                    File tmp = new File(entry.getName());
//                    FileOutputStream fos;
//
//                    String fileName = null;
//                    //解压出的文件路径
//                    try {
//                        fileName = tmp.getAbsolutePath();
//                        int tmpNum = -1;
//                        fos = new FileOutputStream(fileName);
//                        while ((tmpNum = zin.read()) != -1) {
//                            fos.write(tmpNum);
//                        }
//                        fos.close();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        delteTempFile(newFile);
//                        delteTempFile(tmp);
//                    }
//
//                    ArrayList<String> strings = null;
//                    try {
//                        strings = readTxtFile(tmp.getAbsolutePath());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        delteTempFile(tmp);
//                        delteTempFile(newFile);
//                    }
//
//                    if (null == strings || 0 == strings.size()) {
//                        delteTempFile(tmp);
//                        delteTempFile(newFile);
//                    }
//
//                    for (int i = 1; i < strings.size(); i++) {
//                        String value = strings.get(i);
//                        String[] fileds = value.split("\\|");
//
//                        if ((fileName.contains("det.txt")) && !(fileName.contains("redet.txt"))) {
//
//                            try {
//                                TApportionDetailsInfoTmp tApportionDetailsInfo = getMappingPo(TApportionDetailsInfoTmp.class, fileds);
//                                tApportionDetailsInfo.setMarketId(tFileInfoMapper.getMarketId());
//                                tApportionDetailsInfo.setPartAmt(new BigDecimal(tApportionDetailsInfo.getTempPartAmt()));
//                                StringBuffer tmpPartDt = new StringBuffer(tApportionDetailsInfo.getTmpPartDt());
//                                tmpPartDt.insert(4, "-");
//                                tmpPartDt.insert(7, "-");
//                                SimpleDateFormat smf = new SimpleDateFormat("yyyy-mm-dd");
//                                Date date = smf.parse(String.valueOf(tmpPartDt));
//                                tApportionDetailsInfo.setPartDt(date);
//                                TApportionDetailsInfo tApportionDetailsInfo1 = new TApportionDetailsInfo();
//                                BeanUtils.copyProperties(tApportionDetailsInfo, tApportionDetailsInfo1);
//                                int insert = tApportionDetailsInfoMapper.insert(tApportionDetailsInfo1);
//                                if (1 != insert) {
//                                    log.info("tApportionDetailsInfoMapper.insert error");
//
//                                    fileInputStream.close();
//                                    zipFile.close();
//                                    zin.close();
//
//                                    delteTempFile(tmp);
//                                    delteTempFile(newFile);
//                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                fileInputStream.close();
//                                zipFile.close();
//                                zin.close();
//
//                                delteTempFile(tmp);
//                                delteTempFile(newFile);
//                                throw new RuntimeException(e.getMessage());
//                            }
//
//                        }
//
//                        if (fileName.contains("redet.txt")) {
//
//                            try {
//                                TApportionDetailsInfoTmp tApportionDetailsInfo = getMappingPo(TApportionDetailsInfoTmp.class, fileds);
//                                tApportionDetailsInfo.setMarketId(tFileInfoMapper.getMarketId());
//                                tApportionDetailsInfo.setPartAmt(new BigDecimal(tApportionDetailsInfo.getTempPartAmt()));
//                                StringBuffer tmpPartDt = new StringBuffer(tApportionDetailsInfo.getTmpPartDt());
//                                tmpPartDt.insert(4, "-");
//                                tmpPartDt.insert(7, "-");
//                                SimpleDateFormat smf = new SimpleDateFormat("yyyy-mm-dd");
//                                Date date = smf.parse(String.valueOf(tmpPartDt));
//                                tApportionDetailsInfo.setPartDt(date);
//                                TApportionDetailsInfo tApportionDetailsInfo1 = new TApportionDetailsInfo();
//                                BeanUtils.copyProperties(tApportionDetailsInfo, tApportionDetailsInfo1);
//                                UpdateWrapper<TApportionDetailsInfo> updateWrapper = new UpdateWrapper<>();
//                                updateWrapper.eq("MARKET_ID", tApportionDetailsInfo1.getMarketId());
//                                updateWrapper.eq("SER_NUM", tApportionDetailsInfo1.getSerNum());
//                                updateWrapper.eq("PAYMENT_TRACE_ID", tApportionDetailsInfo1.getPaymentTraceId());
//
//                                int update = tApportionDetailsInfoMapper.update(tApportionDetailsInfo1, updateWrapper);
//                                if (1 != update) {
//                                    log.info("tApportionDetailsInfoMapper.update error");
//
//                                    fileInputStream.close();
//                                    zipFile.close();
//                                    zin.close();
//
//                                    delteTempFile(tmp);
//                                    delteTempFile(newFile);
//                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                fileInputStream.close();
//                                zipFile.close();
//                                zin.close();
//
//                                delteTempFile(tmp);
//                                delteTempFile(newFile);
//                                throw new RuntimeException(e.getMessage());
//                            }
//
//                        }
//
//                        if ((fileName.contains("sum.txt")) && !(fileName.contains("resum.txt"))) {
//
//                            try {
//                                TApportionSumInfoTmp tApportionSumInfoTmp = getMappingPo(TApportionSumInfoTmp.class, fileds);
//                                tApportionSumInfoTmp.setMarketId(tFileInfoMapper.getMarketId());
//                                tApportionSumInfoTmp.setPartAmt(new BigDecimal(tApportionSumInfoTmp.getPartAmtTmp()));
//                                StringBuffer tmpPartDt = new StringBuffer(tApportionSumInfoTmp.getPartDtTmp());
//                                tmpPartDt.insert(4, "-");
//                                tmpPartDt.insert(7, "-");
//                                SimpleDateFormat smf = new SimpleDateFormat("yyyy-mm-dd");
//                                Date date = smf.parse(String.valueOf(tmpPartDt));
//                                tApportionSumInfoTmp.setPartDt(date);
//                                tApportionSumInfoTmp.setUpdateDt(new Date());
//                                TApportionSumInfo tApportionSumInfo = new TApportionSumInfo();
//                                BeanUtils.copyProperties(tApportionSumInfoTmp, tApportionSumInfo);
//                                int insert = tApportionSumInfoMapper.insert(tApportionSumInfo);
//                                if (1 != insert) {
//                                    log.info("tApportionSumInfoMapper.insert error");
//
//                                    fileInputStream.close();
//                                    zipFile.close();
//                                    zin.close();
//
//                                    delteTempFile(tmp);
//                                    delteTempFile(newFile);
//                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                fileInputStream.close();
//                                zipFile.close();
//                                zin.close();
//
//                                delteTempFile(tmp);
//                                delteTempFile(newFile);
//                                throw new RuntimeException(e.getMessage());
//                            }
//
//                        }
//
//                        if (fileName.contains("resum.txt")) {
//
//                            try {
//                                TApportionSumInfoTmp tApportionSumInfoTmp = getMappingPo(TApportionSumInfoTmp.class, fileds);
//                                tApportionSumInfoTmp.setMarketId(tFileInfoMapper.getMarketId());
//                                tApportionSumInfoTmp.setPartAmt(new BigDecimal(tApportionSumInfoTmp.getPartAmtTmp()));
//                                StringBuffer tmpPartDt = new StringBuffer(tApportionSumInfoTmp.getPartDtTmp());
//                                tmpPartDt.insert(4, "-");
//                                tmpPartDt.insert(7, "-");
//                                SimpleDateFormat smf = new SimpleDateFormat("yyyy-mm-dd");
//                                Date date = smf.parse(String.valueOf(tmpPartDt));
//                                tApportionSumInfoTmp.setPartDt(date);
//                                tApportionSumInfoTmp.setUpdateDt(new Date());
//                                TApportionSumInfo tApportionSumInfo = new TApportionSumInfo();
//                                BeanUtils.copyProperties(tApportionSumInfoTmp, tApportionSumInfo);
//                                UpdateWrapper<TApportionSumInfo> updateWrapper = new UpdateWrapper<>();
//                                updateWrapper.eq("MARKET_ID", tApportionSumInfo.getMarketId());
//                                updateWrapper.eq("PART_DT", tApportionSumInfo.getPartDt());
//                                updateWrapper.eq("PAYEE_ID", tApportionSumInfo.getPayeeId());
//
//                                int update = tApportionSumInfoMapper.update(tApportionSumInfo, updateWrapper);
//                                if (1 != update) {
//                                    log.info("tApportionSumInfoMapper.update error");
//
//                                    fileInputStream.close();
//                                    zipFile.close();
//                                    zin.close();
//
//                                    delteTempFile(tmp);
//                                    delteTempFile(newFile);
//                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                    return;
//                                }
//                            } catch (Exception e) {
//
//                                fileInputStream.close();
//                                zipFile.close();
//                                zin.close();
//
//                                e.printStackTrace();
//                                delteTempFile(tmp);
//                                delteTempFile(newFile);
//                                throw new RuntimeException(e.getMessage());
//                            }
//                        }
//
//                        if (fileName.contains("sub.txt")) {
//
//                            try {
//                                TWithholdDetailsInfoTmp tWithholdDetailsInfoTmp = getMappingPo(TWithholdDetailsInfoTmp.class, fileds);
//                                tWithholdDetailsInfoTmp.setMarketId(tFileInfoMapper.getMarketId());
//                                tWithholdDetailsInfoTmp.setDeduAmt(new BigDecimal(tWithholdDetailsInfoTmp.getDeduAmtTmp()));
//                                StringBuffer tmpPartDt = new StringBuffer(tWithholdDetailsInfoTmp.getDeduStTmp());
//                                tmpPartDt.insert(4, "-");
//                                tmpPartDt.insert(7, "-");
//                                SimpleDateFormat smf = new SimpleDateFormat("yyyy-mm-dd");
//                                Date date = smf.parse(String.valueOf(tmpPartDt));
//                                tWithholdDetailsInfoTmp.setDeduDt(date);
//                                TWithholdDetailsInfo tWithholdDetailsInfo = new TWithholdDetailsInfo();
//                                BeanUtils.copyProperties(tWithholdDetailsInfoTmp, tWithholdDetailsInfo);
//                                int insert = tWithholdDetailsInfoMapper.insert(tWithholdDetailsInfo);
//                                if (1 != insert) {
//                                    log.info("tWithholdDetailsInfoMapper.insert error");
//
//                                    fileInputStream.close();
//                                    zipFile.close();
//                                    zin.close();
//
//                                    delteTempFile(tmp);
//                                    delteTempFile(newFile);
//                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                fileInputStream.close();
//                                zipFile.close();
//                                zin.close();
//
//                                delteTempFile(tmp);
//                                delteTempFile(newFile);
//                                throw new RuntimeException(e.getMessage());
//                            }
//
//                        }
//
//                        if (fileName.contains("chk.txt")) {
//
//                            try {
//                                TReconciliationDetailsInfoTmp tReconciliationDetailsInfoTmp = getMappingPo(TReconciliationDetailsInfoTmp.class, fileds);
//                                tReconciliationDetailsInfoTmp.setMarketId(tFileInfoMapper.getMarketId());
//                                tReconciliationDetailsInfoTmp.setTxnAmt(new BigDecimal(tReconciliationDetailsInfoTmp.getTxnAmtTmp()));
//                                StringBuffer tmpPartDt = new StringBuffer(tReconciliationDetailsInfoTmp.getTxnDtTmp());
//                                tmpPartDt.insert(4, "-");
//                                tmpPartDt.insert(7, "-");
//                                SimpleDateFormat smf = new SimpleDateFormat("yyyy-mm-dd");
//                                Date date = smf.parse(String.valueOf(tmpPartDt));
//                                tReconciliationDetailsInfoTmp.setTxnDt(date);
//                                tReconciliationDetailsInfoTmp.setServChgr(new BigDecimal(tReconciliationDetailsInfoTmp.getServCharTmp()));
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//                                long time = Long.parseLong(tReconciliationDetailsInfoTmp.getUpdateDtTmp());
//                                Date date1 = new Date(time);
//                                tReconciliationDetailsInfoTmp.setTxnTm(date1);
//                                TReconciliationDetailsInfo tReconciliationDetailsInfo = new TReconciliationDetailsInfo();
//                                BeanUtils.copyProperties(tReconciliationDetailsInfoTmp, tReconciliationDetailsInfo);
//                                int insert = tReconciliationDetailsInfoMapper.insert(tReconciliationDetailsInfo);
//
//                                if (1 != insert) {
//                                    log.info("tReconciliationDetailsInfoMapper.insert error");
//
//                                    fileInputStream.close();
//                                    zipFile.close();
//                                    zin.close();
//
//                                    delteTempFile(tmp);
//                                    delteTempFile(newFile);
//                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                fileInputStream.close();
//                                zipFile.close();
//                                zin.close();
//
//                                delteTempFile(tmp);
//                                delteTempFile(newFile);
//                                throw new RuntimeException(e.getMessage());
//                            }
//
//                        }
//                    }
//
//                    delteTempFile(tmp);
//                }
//
//                zin.close();
//                zipFile.close();
//                fileInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                delteTempFile(newFile);
//            }
//
//            delteTempFile(newFile);
//
//            // 更新文件数据库的状态
//
//            tFileInfo.setFileStatus("1");
//            tFileInfo.setUpdateTime(new Date());
//            UpdateWrapper<TFileInfo> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("FILE_NAME", tFileInfo.getFileName());
//            int update = tFileInfoMapper.update(tFileInfo, updateWrapper);
//            if (1 != update) {
//                log.info("更新文件状态失败");
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return;
//            }
//
//            log.info("文件解析成功");
//
//        }
    }

//    @Scheduled(fixedRate = 1000 * 60 * 30)
//    public void updateOrderStatus() {
//        QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("ORDER_ST", OrderStatusEnum.FOR_HADNLE.getOrderStatus());
//        List<TOrderInfo> tOrderInfos = tOrderInfoMapper.selectList(queryWrapper);
//
//        if (tOrderInfos.size() == 0) {
//            log.info("查询不到未支付的主订单");
//            return;
//        }
//
//        ResultOrderCallBackVo resultOrderCallBackVo = new ResultOrderCallBackVo();
//        for (TOrderInfo tOrderInfo : tOrderInfos) {
//            String oederTimeout = tOrderInfo.getOederTimeout();
//
//            if (null == oederTimeout) {
//                continue;
//            }
//
//            LocalDateTime localDateTime = LocalDateTime.now();
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//            LocalDateTime orderDateTIme = LocalDateTime.parse(oederTimeout, dateTimeFormatter);
//
//            Duration duration = Duration.between(orderDateTIme, localDateTime);
//            if (duration.toMinutes() > 30) {
//                QueryOrderResultVo queryOrderResultVo = new QueryOrderResultVo();
//                queryOrderResultVo.setMainOrderId(tOrderInfo.getMainOrderId());
//                queryOrderResultVo.setMarketId(tFileInfoMapper.getMarketId());
//                queryOrderResultVo.setVersion("4");
//                queryOrderResultVo.setPaymentTraceId(tOrderInfo.getPaymentTraceId());
//                queryOrderResultVo.setSndDtTm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
//                queryOrderResultVo.setSndTraceId(String.valueOf(new IdWorker("0", "1").nextId()));
//
//                try {
//                    ReturnOrderResultDTO returnOrderResultDTO = restTemplate.postForObject(pushUrl + "/outHsb/securityCenter/DISP20210098", queryOrderResultVo, ReturnOrderResultDTO.class);
//                    log.info(returnOrderResultDTO.toString());
//
//                    if (!returnOrderResultDTO.getTxnSt().equals("00")) {
//                        continue;
//                    }
//
//                    resultOrderCallBackVo.setOrderSt(returnOrderResultDTO.getOrderSt());
//                    resultOrderCallBackVo.setMainOrderId(tOrderInfo.getMainOrderId());
//
////                    if (returnOrderResultDTO.getOr)
////                    resultOrderCallBackVo.setPaymentDtTm(returnOrderResultDTO.get);
//
//                    ResultToHSBOut resultToHSBOut = restTemplate2.postForObject("http://" + "disPart-order" + "/securityCenter/DISP20210065", resultOrderCallBackVo, ResultToHSBOut.class);
//                    log.info(resultToHSBOut.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//
////                System.out.println(resultToHSBOut);
////                System.out.println(123);
//            }
//        }
//    }

//    @Transactional
//    @Scheduled(fixedRate = 1000 * 60 * 30)
//    public void doCheckApproation() {
//        log.info("开始对账");
//        try{
////            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
////            String now = dateTimeFormatter.format(DateTime.now());
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//            Date today = new Date();
//            Calendar c = Calendar.getInstance();
//            c.setTime(today);
//            c.add(Calendar.DAY_OF_MONTH,-1);
//            Date yesterday = c.getTime();
//            String time = simpleDateFormat.format(yesterday);
//            log.info("获取昨天的时间");
//
//            QueryWrapper<TReconciliationDetailsInfo> queryWrapper1 = new QueryWrapper<>();
//            queryWrapper1.apply("date_format(TXN_DT,'%Y-%m-%d') >= date_format('" + time + "','%Y-%m-%d')");
//            List<TReconciliationDetailsInfo> tReconciliationDetailsInfos = tReconciliationDetailsInfoMapper.selectList(queryWrapper1);
//            if(tReconciliationDetailsInfos.size() == 0){
//                log.info("没有查询到昨日对账明细表的数据返回");
//                return;
//            }
//
//            List<ReconciliationResult> list = tReconciliationResultInfoMapper.queryResult(time);
//            if (list.size() == 0) {
//                log.info("对账全部正确");
//            }
//
//            for (ReconciliationResult reconciliationResult:list) {
//                QueryWrapper<TReconciliationResultInfo> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("MAIN_ORDER_NO",reconciliationResult.getMain_order_id());
//                TReconciliationResultInfo tReconciliationResultInfo1 = tReconciliationResultInfoMapper.selectOne(queryWrapper);
//                if (tReconciliationResultInfo1 != null) {
//                    log.info("已有对账文件" + reconciliationResult.getMain_order_id());
//                    continue;
//                }
//
//                TReconciliationResultInfo tReconciliationResultInfo = new TReconciliationResultInfo();
//                tReconciliationResultInfo.setMainOrderNo(reconciliationResult.getMain_order_id());
//                tReconciliationResultInfo.setMarketId(reconciliationResult.getMarker_id());
//                tReconciliationResultInfo.setPayeeId(reconciliationResult.getProv_id());
//                tReconciliationResultInfo.setPayeeNm(reconciliationResult.getProv_nm());
//                tReconciliationResultInfo.setPaymentTraceId(reconciliationResult.getPayment_trace_id());
//                tReconciliationResultInfo.setTxnAmt(reconciliationResult.getTxt_amt());
//                tReconciliationResultInfo.setTxnDt(reconciliationResult.getTxn_dt());
//                tReconciliationResultInfo.setTxnTm(reconciliationResult.getTxn_tm());
//                tReconciliationResultInfo.setRespSt(reconciliationResult.getPayee_st());
//                tReconciliationResultInfo.setUpdateDt(new Date());
//
//                if (reconciliationResult.getTxt_amt2() == null && reconciliationResult.getTxt_amt() != null) {
//                    tReconciliationResultInfo.setReconRslt("对账明细表主订单金额为空");
//                    tReconciliationResultInfo.setCause("没收到惠市宝推送回来的账单，但我们子订单支付成功并且金额也不为空，原因估计为主子订单编号匹配错误");
//                }
//
//                if (reconciliationResult.getTxt_amt() == null && reconciliationResult.getTxt_amt2() != null) {
//                    tReconciliationResultInfo.setReconRslt("主订单匹配的子订单金额为空");
//                    tReconciliationResultInfo.setCause("受到惠市宝推送回来的账单，但我们主订单匹配的子订单不存在或者金额为空，原因估计为主子订单编号匹配错误");
//                }
//
//                if (reconciliationResult.getTxt_amt2()!= null && reconciliationResult.getTxt_amt()!= null && reconciliationResult.getTxt_amt2().compareTo(reconciliationResult.getTxt_amt()) != 0){
//                    tReconciliationResultInfo.setReconRslt("金额不匹配");
//                    tReconciliationResultInfo.setCause("单纯的订单金额不匹配");
//                }
//
//                tReconciliationResultInfoMapper.insert(tReconciliationResultInfo);
////                tReconciliationResultInfo.setOrderId(reconciliationResult.get);
//                log.info("对账结束");
//            }
//        }catch (Exception e) {
//            log.error("对账异常",e);
//            throw new RuntimeException(e);
//        }
////        List list = tReconciliationResultInfoMapper.queryResult("123");
////        System.out.println(list);
////        System.out.println(123);
//    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            boolean delete = del.delete();
        }
    }

    public static ArrayList<String> readTxtFile(String filePath) throws Exception {

        ArrayList<String> objects = new ArrayList<>();

        String encoding = "UTF8";
        File file = new File(filePath);

        //判断文件是否存在
        if (file.isFile() && file.exists()) {

            //考虑到编码格式
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);
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