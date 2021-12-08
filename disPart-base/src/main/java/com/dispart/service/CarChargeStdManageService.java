package com.dispart.service;

import com.dispart.model.base.CarChargeAndCarType;
import com.dispart.model.base.CarChargeStdManage;
import com.dispart.model.base.PageInfo;
import com.dispart.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarChargeStdManageService {
    /**
     * 车辆收费标准写入
     * @param carChargeAndCarType
     * @return
     */
    Result insertCarChargeStd(CarChargeAndCarType carChargeAndCarType);
    /**
     * 查询车辆收费标准
     * @param carChargeStdManage
     * @return
     */
    Result selectCarChargeStd(CarChargeStdManage carChargeStdManage);

    Result updateCarChargeStdByChrgId(CarChargeAndCarType carChargeAndCarType);
    Result deleteCarChargeStdBy(String chrgId);
}
