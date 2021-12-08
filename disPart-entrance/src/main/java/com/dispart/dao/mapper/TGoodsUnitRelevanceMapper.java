package com.dispart.dao.mapper;

import com.dispart.dto.entrance.TGoodsUnitInDto;
import com.dispart.dto.entrance.TGoodsUnitRelevanceDto;
import com.dispart.vo.entrance.TGoodsUnitRelevance;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TGoodsUnitRelevanceMapper {
    int insert(TGoodsUnitRelevance record);

    TGoodsUnitRelevanceDto selectByUnitId(Integer unitId);

    int selectByUnitIdCount(Integer unitId);

    Integer selectByUnitIdAndWeightCount(TGoodsUnitRelevance record);

    int insertSelective(TGoodsUnitRelevance record);

    int  deleteByUnitIdAndWeight(TGoodsUnitInDto record);
}