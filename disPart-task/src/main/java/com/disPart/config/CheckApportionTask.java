package com.disPart.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.disPart.dao.*;
import com.disPart.entity.TReconciliationResult;
import com.dispart.dto.orderdto.ReturnOrderResultDTO;
import com.dispart.model.order.*;
import com.dispart.result.ResultToHSBOut;
import com.dispart.util.IdWorker;
import com.dispart.vo.order.QueryOrderResultVo;
import com.dispart.vo.order.ResultOrderCallBackVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class CheckApportionTask extends AbstractTask implements ISchedule {

    @Autowired
    private TReconciliationResultInfoMapper tReconciliationResultInfoMapper;

    @Autowired
    private TReconciliationDetailsInfoMapper tReconciliationDetailsInfoMapper;

    @Autowired
    private TReconciliationResultMapper tReconciliationResultMapper;

    @Autowired
    private TFileInfoMapper tFileInfoMapper;

    @Override
    @Scheduled(cron = "0 0/10 * * * ?")
    //@Scheduled(fixedRate = 1000 * 60 * 10)
    public void doScheduled() {
        super.doScheduled();
    }

    @Override
    protected void task() {
        log.info("开始对账");
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = c.getTime();
            String time = simpleDateFormat.format(yesterday);
            log.info("获取昨天的时间");

            QueryWrapper<TReconciliationDetailsInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.apply("date_format(TXN_DT,'%Y-%m-%d') >= date_format('" + time + "','%Y-%m-%d')");
            List<TReconciliationDetailsInfo> tReconciliationDetailsInfos = tReconciliationDetailsInfoMapper.selectList(queryWrapper1);
            if (tReconciliationDetailsInfos.size() == 0) {
                log.info("没有查询到昨日对账明细表的数据返回");
                return;
            }

            List<ReconciliationResult> list = tReconciliationResultInfoMapper.queryResult(time);

            TReconciliationResult tReconciliationResult1 = new TReconciliationResult();

            tReconciliationResult1.setCreatTime(new Date());
            tReconciliationResult1.setUpTime(new Date());
            tReconciliationResult1.setMarketId(tFileInfoMapper.getMarketId());
            tReconciliationResult1.setTxnDt(new Date());

            if (list.size() == 0) {
                log.info("对账全部正确");
                tReconciliationResult1.setReconRslt("0");
                tReconciliationResultMapper.insert(tReconciliationResult1);
            } else {
                log.info("对账不正确");
                tReconciliationResult1.setReconRslt("1");
                tReconciliationResultMapper.insert(tReconciliationResult1);

                for (ReconciliationResult reconciliationResult : list) {
                    QueryWrapper<TReconciliationResultInfo> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("MAIN_ORDER_NO", reconciliationResult.getMain_order_id());
                    TReconciliationResultInfo tReconciliationResultInfo1 = tReconciliationResultInfoMapper.selectOne(queryWrapper);
                    if (tReconciliationResultInfo1 != null) {
                        log.info("已有对账文件" + reconciliationResult.getMain_order_id());
                        continue;
                    }

                    TReconciliationResultInfo tReconciliationResultInfo = new TReconciliationResultInfo();
                    tReconciliationResultInfo.setMainOrderNo(reconciliationResult.getMain_order_id());
                    tReconciliationResultInfo.setMarketId(reconciliationResult.getMarker_id());
                    tReconciliationResultInfo.setPayeeId(reconciliationResult.getProv_id());
                    tReconciliationResultInfo.setPayeeNm(reconciliationResult.getProv_nm());
                    tReconciliationResultInfo.setPaymentTraceId(reconciliationResult.getPayment_trace_id());
                    tReconciliationResultInfo.setTxnAmt(reconciliationResult.getTxt_amt());
                    tReconciliationResultInfo.setTxnDt(reconciliationResult.getTxn_dt());
                    tReconciliationResultInfo.setTxnTm(reconciliationResult.getTxn_tm());
                    tReconciliationResultInfo.setRespSt(reconciliationResult.getPayee_st());
                    tReconciliationResultInfo.setUpdateDt(new Date());

                    if (reconciliationResult.getTxt_amt2() == null && reconciliationResult.getTxt_amt() != null) {
                        tReconciliationResultInfo.setReconRslt("对账明细表主订单金额为空");
                        tReconciliationResultInfo.setCause("没收到惠市宝推送回来的账单，但我们子订单支付成功并且金额也不为空，原因估计为主子订单编号匹配错误");
                    }

                    if (reconciliationResult.getTxt_amt() == null && reconciliationResult.getTxt_amt2() != null) {
                        tReconciliationResultInfo.setReconRslt("主订单匹配的子订单金额为空");
                        tReconciliationResultInfo.setCause("受到惠市宝推送回来的账单，但我们主订单匹配的子订单不存在或者金额为空，原因估计为主子订单编号匹配错误");
                    }

                    if (reconciliationResult.getTxt_amt2() != null && reconciliationResult.getTxt_amt() != null && reconciliationResult.getTxt_amt2().compareTo(reconciliationResult.getTxt_amt()) != 0) {
                        tReconciliationResultInfo.setReconRslt("金额不匹配");
                        tReconciliationResultInfo.setCause("单纯的订单金额不匹配");
                    }

                    tReconciliationResultInfoMapper.insert(tReconciliationResultInfo);
                    log.info("对账结束");
                }
            }

        } catch (
                Exception e) {
            log.error("对账异常", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    protected String getLockName() {
        String lockName = "checkApportionTask";
        log.debug("获取任务锁:{}", lockName);
        return lockName;
    }

    @Override
    protected boolean judgeTaskStatus(String lockName) {
        // 直接返回成功
        QueryWrapper<TReconciliationResult> queryWrapper2 = new QueryWrapper<>();

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = localDate.format(dateTimeFormatter);

        queryWrapper2.eq("TXN_DT", dateString);
        TReconciliationResult tReconciliationResult = tReconciliationResultMapper.selectOne(queryWrapper2);

        return tReconciliationResult == null;
    }

    @Override
    protected String getServiceName() {
        return "CheckApportionTask";
    }
}
