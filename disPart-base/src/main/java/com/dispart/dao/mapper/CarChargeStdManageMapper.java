package com.dispart.dao.mapper;

import com.dispart.model.base.CarChargeAndCarType;
import com.dispart.model.base.CarChargeStdManage;
import com.dispart.model.base.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarChargeStdManageMapper {
    Integer insertCarChargeStd(@Param("carChargeStdManage") CarChargeStdManage carChargeStdManage);
    //List<CarChargeStdManage> selectCarChargeStd(@Param("carChargeStdManage") CarChargeStdManage carChargeStdManage);
    Integer updateCarChargeStd(@Param("carChargeStdManage") CarChargeStdManage carChargeStdManage, @Param("carChargeAndCarType") CarChargeAndCarType carChargeAndCarType);
    CarChargeStdManage selectCarChargeStdBychrgId(@Param("chrgId") String chrgId);
    Integer deleteCarChargeStdByChrgId(@Param("chrgId") String chrgId);
    Integer selectCarChargeStdCount(@Param("carChargeStdManage") CarChargeStdManage carChargeStdManage);
    List<CarChargeStdManage> selectCarChargeStdByVehicleId(@Param("vehicleId") String vehicleId,@Param("vehicleTp") String vehicleTp);
    Integer selectEmployeeIdSeq();
}
