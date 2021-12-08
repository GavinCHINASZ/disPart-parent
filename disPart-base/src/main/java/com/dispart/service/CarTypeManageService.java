package com.dispart.service;

import com.dispart.model.base.CarTypeManage;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface CarTypeManageService {
    /**
     * 车辆类型数据成功写入
     * @param carTypeManage
     * @return
     */
    Result insertCarType(CarTypeManage carTypeManage);
    /**
     * 查找车辆信息
     * @param carTypeManage
     * @return
     */
    Result findCarInfoByVehicleTp(CarTypeManage carTypeManage);
    /**
     * 车辆信息修改
     * @param carTypeManage
     * @return
     */
    Result updateCarTypeByVehicle(CarTypeManage carTypeManage);
    /**
     * 车辆信息删除
     * @param carTypeManage
     * @return
     */
    Result deleteCarTypeByVehicle(CarTypeManage carTypeManage);

    /**
     * 根据车型信息查找车辆信息
     * @param request
     * @return
     */
    Result findCarInfo(Request<CarTypeManage> request);

}
