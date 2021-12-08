package com.disPart.config;

import com.disPart.dao.TAccnoDailyDetailMapper;
import com.disPart.dao.TAccnoDailyMapper;
import com.disPart.dao.TAccnoInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class DayTask extends AbstractTask implements ISchedule {
    private final Logger logger = LoggerFactory.getLogger(DayTask.class);

    @Resource
    private TAccnoDailyMapper tAccnoDailyMapper;

    @Resource
    private TAccnoInfoMapper tAccnoInfoMapper;

    @Resource
    private TAccnoDailyDetailMapper tAccnoDailyDetailMapper;

    @Override
    @Scheduled(cron = "0 0 * * * ?")
    public void doScheduled() {
        super.doScheduled();
    }

    /**
     * 日终任务
     */
    @Override
    @Transactional
    protected void task() {

        logger.debug("日终任务开始执行");

        tAccnoInfoMapper.lock();

        try {
            //插入数据到日终表
            tAccnoDailyMapper.add();

            //日终明细
            tAccnoDailyDetailMapper.addDetails();

            //更新账户信息
            tAccnoInfoMapper.updateAccnoInfo();
        } catch (Exception e) {
            logger.error("日终任务异常", e);
        } finally {
            tAccnoInfoMapper.unlock();
        }


    }

    /**
     * @return 获取任务锁
     */
    @Override
    protected String getLockName() {
        String lockName = "accnoDayTask";
        logger.debug("获取任务锁:{}", lockName);
        return lockName;
    }

    /**
     * 判断是否有任务可以执行
     */
    @Override
    protected boolean judgeTaskStatus(String lockName) {

        int count = tAccnoInfoMapper.count();
        if(count > 0 ) {
            logger.info("有任务需要执行，查找到的条数:{}", count);
            return true;
        }

        return false;
    }

    /**
     * @return 获取自动任务服务名
     */
    @Override
    protected String getServiceName() {
        return "AccnoDayTask";
    }


}
