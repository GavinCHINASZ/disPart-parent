package com.disPart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.ReconciliationResult;
import com.dispart.model.order.TReconciliationResultInfo;
import org.apache.ibatis.annotations.Mapper;import java.util.List;

@Mapper
public interface TReconciliationResultInfoMapper extends BaseMapper<TReconciliationResultInfo> {
    List<ReconciliationResult> queryResult(String date);
}