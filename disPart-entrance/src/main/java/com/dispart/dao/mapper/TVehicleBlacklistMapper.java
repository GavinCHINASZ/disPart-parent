package com.dispart.dao.mapper;

import com.dispart.vo.entrance.TVehicleBlacklist;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆黑名单
 */
@Mapper
public interface TVehicleBlacklistMapper {
    /**
     * 根据主键查询车辆黑名单
     * @param vehicleNum
     * @return
     */
    TVehicleBlacklist selectByPk(String vehicleNum);


}