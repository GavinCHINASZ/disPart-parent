package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.TReconciliationDetailsInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TReconciliationDetailsInfoMapper extends BaseMapper<TReconciliationDetailsInfo> {
    /**
     * delete by primary key
     *
     * @param paymentTraceId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String paymentTraceId);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(TReconciliationDetailsInfo record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(TReconciliationDetailsInfo record);

    /**
     * select by primary key
     *
     * @param paymentTraceId primary key
     * @return object by primary key
     */
    TReconciliationDetailsInfo selectByPrimaryKey(String paymentTraceId);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(TReconciliationDetailsInfo record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TReconciliationDetailsInfo record);
}