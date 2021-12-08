package com.dispart.dao.mapper;

import com.dispart.model.base.CarTypeAndDetails;
import com.dispart.model.base.CarTypeManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarTypeManageMapper {
    Integer insertCarType(@Param("carTypeManage") CarTypeManage carTypeManage);
    List<CarTypeManage> findCarInfoByVehicleTp(@Param("carTypeManage") CarTypeManage carTypeManage);
    List<CarTypeManage> findCarInfoByVehicleSub(@Param("carTypeManage") CarTypeManage carTypeManage);
    Integer updateCarTypeByVehicle(@Param("carTypeManage") CarTypeManage carTypeManage);
    Integer deleteCarTypeByVehicle(@Param("carTypeManage") CarTypeManage carTypeManage);
    List<CarTypeManage> findCarInfoByVehicleTpa(@Param("vehicleId") String vehicleId,@Param("vehicleTpId") String vehicleTpId);
    List<CarTypeManage> findCarInfoByVehicleForCharge(@Param("vehicleTp") String vehicleTp,
                                                      @Param("vehicleId")String vehicleId,@Param("pageNum") Integer pageNum,@Param("pageSize")Integer pageSize);


   Integer findCarInfoByVehicleForChargeCount(@Param("vehicleTp") String vehicleTp,
                                         @Param("vehicle")String vehicle,@Param("pageNum") Integer pageNum,@Param("pageSize")Integer pageSize);
   Integer selectEmployeeIdSeq();
}
