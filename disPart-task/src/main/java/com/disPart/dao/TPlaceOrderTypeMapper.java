package com.disPart.dao;
import com.dispart.vo.hsb.PlaceOrderTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TPlaceOrderTypeMapper {
    int deleteByPrimaryKey(@Param("provId") String provId, @Param("placeOrderMd") String placeOrderMd);

    int insert(PlaceOrderTypeVo record);

    int insertSelective(PlaceOrderTypeVo record);

    PlaceOrderTypeVo selectByPrimaryKey(@Param("provId") String provId, @Param("placeOrderMd") String placeOrderMd);

    int updateByPrimaryKeySelective(PlaceOrderTypeVo record);

    int updateByPrimaryKey(PlaceOrderTypeVo record);
}