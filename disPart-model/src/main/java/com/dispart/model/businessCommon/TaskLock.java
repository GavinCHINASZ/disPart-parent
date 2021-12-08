package com.dispart.model.businessCommon;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TaskLock {
    //任务锁
    private String lockName;

    //任务锁状态
    private String lockStatus;

    //临时变量 更改前锁的状态
    private String lockStatusTmp;

    //任务日期
    private LocalDate taskDate;

    //任务开始时间
    private LocalTime startTime;

    //任务结束时间
    private LocalTime endTime;

    //备注
    private String remark;
}
