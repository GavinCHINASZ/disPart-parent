package com.dispart.dao.mapper;

import com.dispart.model.base.CarChargeStdManage;
import com.dispart.vo.entrance.TVechicleProcurer;

import java.util.List;

/**
 * 车辆收费标准管理
 */
public interface TvechiclePayManagerMapper {
    /**
     * select by vehicleId
     * @param tVechicleProcurer
     * @return
     */
    CarChargeStdManage selectByVehicleId(TVechicleProcurer tVechicleProcurer);

    /**
     * select by vehicleId
     * @param tVechicleProcurer
     * @return
     */
    List<CarChargeStdManage> selectListByVehicleId(TVechicleProcurer tVechicleProcurer);


}
