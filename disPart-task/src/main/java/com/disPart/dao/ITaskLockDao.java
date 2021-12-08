package com.disPart.dao;

import com.dispart.model.businessCommon.TaskLock;

public interface ITaskLockDao {

    TaskLock findByPk(String lockName);

    int updateByPk(TaskLock TaskLock);
}
