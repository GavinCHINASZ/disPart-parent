package com.disPart.config;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.disPart.dao.TFileInfoMapper;
import com.disPart.dao.TOrderDetailInfoMapper;
import com.disPart.dao.TOrderInfoMapper;
import com.dispart.dto.orderdto.ReturnOrderResultDTO;
import com.dispart.model.order.OrderStatusEnum;
import com.dispart.model.order.TOrderInfo;
import com.dispart.result.ResultToHSBOut;
import com.dispart.util.IdWorker;
import com.dispart.vo.order.QueryOrderResultVo;
import com.dispart.vo.order.ResultOrderCallBackVo;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
public class OrderResultTask extends AbstractTask implements ISchedule{
    @Value("${pushUrl}")
    private String pushUrl;

    @Autowired
    private TFileInfoMapper tFileInfoMapper;

    @Autowired
    private TOrderInfoMapper tOrderInfoMapper;

    @Autowired
    @Qualifier("restTemplate1")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Override
    //@Scheduled(fixedRate = 1000 * 60 * 10)
    @Scheduled(cron = "0 0/10 * * * ?")
    public void doScheduled() {
        super.doScheduled();
    }

    @Override
    protected void task() {
        QueryWrapper<TOrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ORDER_ST", OrderStatusEnum.FOR_HADNLE.getOrderStatus());
        List<TOrderInfo> tOrderInfos = tOrderInfoMapper.selectList(queryWrapper);

        if (tOrderInfos.size() == 0) {
            log.info("查询不到未支付的主订单");
            return;
        }

        ResultOrderCallBackVo resultOrderCallBackVo = new ResultOrderCallBackVo();
        for (TOrderInfo tOrderInfo : tOrderInfos) {
            String oederTimeout = tOrderInfo.getOederTimeout();

            if (null == oederTimeout) {
                continue;
            }

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime orderDateTIme = LocalDateTime.parse(oederTimeout, dateTimeFormatter);

            Duration duration = Duration.between(orderDateTIme, localDateTime);
            if (duration.toMinutes() > 30) {
                QueryOrderResultVo queryOrderResultVo = new QueryOrderResultVo();
                queryOrderResultVo.setMainOrderId(tOrderInfo.getMainOrderId());
                queryOrderResultVo.setMarketId(tFileInfoMapper.getMarketId());
                queryOrderResultVo.setVersion("4");
                queryOrderResultVo.setPaymentTraceId(tOrderInfo.getPaymentTraceId());
                queryOrderResultVo.setSndDtTm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
                queryOrderResultVo.setSndTraceId(String.valueOf(new IdWorker("0", "1").nextId()));

                try {
                    ReturnOrderResultDTO returnOrderResultDTO = restTemplate.postForObject(pushUrl + "/outHsb/securityCenter/DISP20210098", queryOrderResultVo, ReturnOrderResultDTO.class);
                    log.info(JSONObject.toJSONString(returnOrderResultDTO));

                    if (returnOrderResultDTO != null && !"00".equals(returnOrderResultDTO.getTxnSt())) {
                        continue;
                    }

                    resultOrderCallBackVo.setOrderSt(returnOrderResultDTO.getOrderSt());
                    resultOrderCallBackVo.setMainOrderId(tOrderInfo.getMainOrderId());

                    ResultToHSBOut resultToHSBOut = restTemplate2.postForObject("http://" + "disPart-order" + "/securityCenter/DISP20210065", resultOrderCallBackVo, ResultToHSBOut.class);
                    log.debug(JSONObject.toJSONString(resultToHSBOut));

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    protected String getLockName() {
        String lockName = "orderResultTask";
        log.debug("获取任务锁:{}", lockName);
        return lockName;
    }

    @Override
    protected boolean judgeTaskStatus(String lockName) {
        return true;
    }

    @Override
    protected String getServiceName() {
        return "OrderResultTask";
    }
}
