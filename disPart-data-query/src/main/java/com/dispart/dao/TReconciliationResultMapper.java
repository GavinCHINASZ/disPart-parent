package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210334InDto;
import com.dispart.model.TReconciliationResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TReconciliationResultMapper {

    Integer quryMainCheckResultCount(Disp20210334InDto inDto);

    ArrayList<TReconciliationResult> quryMainCheckResult(Disp20210334InDto inDto);
}