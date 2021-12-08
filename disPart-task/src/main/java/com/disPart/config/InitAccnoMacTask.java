package com.disPart.config;

import com.disPart.dao.TAccnoInfoMapper;
import com.dispart.dto.AccnoInfoVo;
import com.dispart.utils.MacUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 初始化账户mac
 */
@Component
public class InitAccnoMacTask extends AbstractTask implements ISchedule{

    private final Logger logger = LoggerFactory.getLogger(InitAccnoMacTask.class);

    @Resource
    private TAccnoInfoMapper tAccnoInfoMapper;

    @Override
    @Scheduled(cron = "0 0/10 * * * ?")
    public void doScheduled() {
        logger.debug("初始化账户mac任务开始");
        super.doScheduled();
    }

    /**
     * 自动任务
     */
    @Override
    protected void task() {
        List<AccnoInfoVo> list = tAccnoInfoMapper.findByMac();

        if(list != null && list.size() > 0) {
            for (AccnoInfoVo accnoInfoVo : list) {
                String mac = MacUtil.getMac(accnoInfoVo.getProvId(), accnoInfoVo.getAccount(), accnoInfoVo.getAcctBal(),
                        accnoInfoVo.getAvailBal(), accnoInfoVo.getFreezeAmt());

                accnoInfoVo.setMac(mac);

                tAccnoInfoMapper.updateAccnoMac(accnoInfoVo);
            }
        }

    }

    /**
     * @return 获取任务锁
     */
    @Override
    protected String getLockName() {
        return "initMacTask";
    }

    /**
     * 判断是否有任务可以执行
     *
     * @param lockName 自动任务锁名
     */
    @Override
    protected boolean judgeTaskStatus(String lockName) {
        int count = tAccnoInfoMapper.countMac();

        logger.info("查询账户未初始化的条数：{}", count);

        return count > 0;
    }

    /**
     * @return 获取自动任务服务名
     */
    @Override
    protected String getServiceName() {
        return "initMacTask";
    }


}
