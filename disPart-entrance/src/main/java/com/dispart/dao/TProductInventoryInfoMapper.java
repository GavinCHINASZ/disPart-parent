package com.dispart.dao;

import com.dispart.dto.entrance.TProductInventoryInfoDto;;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TProductInventoryInfoMapper {
    int deleteByPrimaryKey(@Param("prdctId") String prdctId, @Param("provId") String provId);

    int insert(TProductInventoryInfoDto record);

    int insertSelective(TProductInventoryInfoDto record);

    TProductInventoryInfoDto selectByPrimaryKey(@Param("prdctId") String prdctId, @Param("provId") String provId,@Param("unit") String unit);

    int updateByPrimaryKeySelective(TProductInventoryInfoDto record);

    int updateByPrimaryKey(TProductInventoryInfoDto record);

    int selectCount(TProductInventoryInfoDto record);

    List<TProductInventoryInfoDto> selectWhere(TProductInventoryInfoDto record);

    List<TProductInventoryInfoDto> selectTolWhere(TProductInventoryInfoDto record);

    int selectTolCount(TProductInventoryInfoDto record);

    int  selectWhereByPhoneCount(@Param("vo") TProductInventoryInfoDto record,@Param("userId") String userId);

    List<TProductInventoryInfoDto>  selectWhereByPhone(@Param("vo") TProductInventoryInfoDto record,@Param("userId") String userId);

    /**
     * 根据主键查询条数
     * @param record
     * @return
     */
    int selectCountByPrimaryKey(TProductInventoryInfoDto record);



}