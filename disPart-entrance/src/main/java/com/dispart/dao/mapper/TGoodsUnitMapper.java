package com.dispart.dao.mapper;

import com.dispart.dto.entrance.TGoodsUnitInDto;
import com.dispart.dto.entrance.TGoodsUnitOutDto;
import com.dispart.dto.entrance.TGoodsUnitParamOutDto;
import com.dispart.vo.entrance.TGoodsUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TGoodsUnitMapper {
    int deleteByPrimaryKey(Integer unitId);

    int insert(TGoodsUnit record);

    int insertSelective(TGoodsUnit record);

    List<TGoodsUnitParamOutDto> selectByProvIdPrDctIdOrAndUnit(TGoodsUnitInDto inDto);

    TGoodsUnitParamOutDto selectByProvIdPrDctIdUnit(TGoodsUnitInDto inDto);

    TGoodsUnitParamOutDto selectByPrimaryKey(Integer unitId);

    int updateByPrimaryKeySelective(TGoodsUnit record);

    int updateByPrimaryKey(TGoodsUnit record);
}