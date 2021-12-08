package com.dispart.service.impl;

import com.dispart.dao.mapper.TTraceIdDao;
import com.dispart.service.TTraceIdService;
import com.dispart.vo.commons.TTraceId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author:xts
 * @date:Created in 2021/6/23 22:24
 * @description
 * @modified by:
 * @version: 1.0
 */
@Service
public class TTraceIdServiceImpl implements TTraceIdService {
    @Resource
    private TTraceIdDao tTraceIdDao;
    @Override
    public int insert(TTraceId tTraceId) {
        return tTraceIdDao.insert( tTraceId);
    }
}