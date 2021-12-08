package com.dispart.utils;

import com.dispart.dao.ISequenceDao;

import java.time.Instant;

public class SequenceUtils {

    public static String sequence() {

        ISequenceDao sequenceDao = SpringUtil.getBean("ISequenceDao", ISequenceDao.class);

        Integer sn = sequenceDao.getRequestSn();

        return String.format("%d%06d", Instant.now().getEpochSecond(), sn);
    }
}
