package com.dispart.dao.mapper;

import com.dispart.vo.entrance.TVehicleMonth;
import feign.Param;

/**
 * 车辆月卡信息
 */
public interface TVehicleMonthMapper {
    /**
     * select by vehicleNum
     * @param vehicleNum
     * @return
     */
    public TVehicleMonth selectByVehicleNum(@Param(value = "vehicleNum") String vehicleNum);
}
