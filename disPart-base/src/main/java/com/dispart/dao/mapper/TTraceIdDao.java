package com.dispart.dao.mapper;

import com.dispart.vo.commons.TTraceId;
import org.apache.ibatis.annotations.Mapper;

/**
 * (TTraceId)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-20 18:32:13
 */
@Mapper
public interface TTraceIdDao {
    /**
     * 新增数据
     *
     * @param tTraceId 实例对象
     * @return 影响行数
     */
    int insert(TTraceId tTraceId);


}