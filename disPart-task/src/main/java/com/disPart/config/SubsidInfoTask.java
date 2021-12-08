package com.disPart.config;

import com.disPart.service.TSubsidInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 补贴申请 定时任务
 *
 * @author 黄贵川
 * @date 2021/08/25
 */
@Component
public class SubsidInfoTask extends AbstractTask implements ISchedule {
    private final Logger logger = LoggerFactory.getLogger(SubsidInfoTask.class);

    @Autowired
    TSubsidInfoService tSubsidInfoService;

    /**
     * 秒 分 时 日 月 周几
     * 每天上午12点晚上23点
     */
    @Override
    @Scheduled(cron = "0 0 12,23 * * ?")
    public void doScheduled() {
        super.doScheduled();
    }

    /**
     * 日终任务
     */
    @Override
    protected void task() {
        tSubsidInfoService.updateSubsidInfo();
    }

    /**
     * @return 获取任务锁
     */
    @Override
    protected String getLockName() {
        String lockName = "subsidInfoTask";
        logger.debug("获取任务锁:{}", lockName);
        return lockName;
    }

    /**
     * 判断是否有任务可以执行
     */
    @Override
    protected boolean judgeTaskStatus(String lockName) {
        return tSubsidInfoService.judgeTaskStatus();
    }

    /**
     * @return 获取自动任务服务名
     */
    @Override
    protected String getServiceName() {
        return "subsidInfoTask";
    }
}
