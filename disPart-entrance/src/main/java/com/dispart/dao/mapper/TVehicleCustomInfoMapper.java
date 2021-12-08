package com.dispart.dao.mapper;


import com.dispart.dto.entrance.TVehicleCustomInfoInDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 进出场客户信息修改记录
 */
@Mapper
public interface TVehicleCustomInfoMapper {
    /**
     * 新增进出场客户信息修改记录
     * @param dto
     * @return
     */
    Integer insert(TVehicleCustomInfoInDto dto);



}