package com.disPart.config;

import com.disPart.dao.ITaskLockDao;
import com.disPart.enums.LockStatusEnum;
import com.dispart.model.businessCommon.TaskLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public abstract class AbstractTask {

    private final Logger logger = LoggerFactory.getLogger(AbstractTask.class);

    @Resource
    ITaskLockDao taskLockDao;

    protected void doScheduled() {
        boolean locked;

        MDC.put("traceId", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        MDC.put("serviceId", this.getServiceName());

        String lockName = this.getLockName();

        // 查看任务是否可以执行 任务日期是否在当天 任务时间是否在时间范围内  任务状态是否是未执行
        if(!getTaskStatus(lockName)) {
            MDC.clear();
            return ;
        }

        boolean status = judgeTaskStatus(lockName);
        if(!status) {
            logger.warn("任务不满足执行条件，暂不执行");
            MDC.clear();
            return;
        }

        try {
            // 数据库加锁 返回是否加锁成功　
            locked = tryLock(lockName);
            if(!locked) {
                logger.warn("未抢到任务, 任务暂不执行");
                return ;
            }

            logger.info("自动任务即将开始执行");

            // 执行任务
            task();

        }catch (Exception e) {
            logger.error("自动任务出现异常", e);
        }finally {
            logger.info("自动任务执行完成");
            // 数据库解锁
            tryUnlock(lockName);
            MDC.clear();
        }
    }

    /**
     * 数据库加锁  t_task_lock
     * @return 加锁成功返回true 否则返回false
     */
    private boolean tryLock(String lockName) {
        TaskLock taskLock = new TaskLock();
        taskLock.setLockName(lockName);
        taskLock.setLockStatus(LockStatusEnum.RUNNING.getValue());
        taskLock.setLockStatusTmp(LockStatusEnum.NOT_RUNNING.getValue());

        int ret = taskLockDao.updateByPk(taskLock);

        return ret == 1;
    }

    /**
     * 数据库解锁  t_task_lock
     */
    private void tryUnlock(String lockName) {
        TaskLock taskLock = new TaskLock();
        taskLock.setLockName(lockName);
        taskLock.setLockStatus(LockStatusEnum.NOT_RUNNING.getValue());
        taskLock.setLockStatusTmp(LockStatusEnum.RUNNING.getValue());
        taskLockDao.updateByPk(taskLock);
    }

    /**
     * 判断任务状态及任务执行时间
     * @param lockName 任务锁
     * @return 任务是否可以执行
     */
    private boolean getTaskStatus(String lockName) {
        TaskLock taskLock = taskLockDao.findByPk(lockName);

        if(taskLock == null) {
            logger.error("任务表中无任务锁:{}", lockName);
            return false;
        }

        if(LockStatusEnum.RUNNING.getValue().equals(taskLock.getLockStatus())) {
            logger.warn("任务正在执行中，不能重复执行");
            return false;
        }

        LocalTime localTime  = LocalTime.now();

        if (localTime.isBefore(taskLock.getStartTime()) || localTime.isAfter(taskLock.getEndTime())) {
            logger.warn("任务不在执行时间范围内,任务时间范围：{}-{}", taskLock.getStartTime(), taskLock.getEndTime());
            return false;
        }

        return true;
    }

    /**
     * 自动任务
     */
    protected abstract void task() ;

    /**
     * @return 获取任务锁
     */
    protected abstract String getLockName() ;

    /**
     * 判断是否有任务可以执行
     */
    protected abstract boolean judgeTaskStatus(String lockName) ;

    /**
     *
     * @return 获取自动任务服务名
     */
    protected abstract String getServiceName();
}
