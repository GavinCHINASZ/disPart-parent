package com.dispart.controller;

import com.dispart.model.base.CarChargeAndCarType;
import com.dispart.model.base.CarChargeStdManage;
import com.dispart.model.base.PageInfo;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CarChargeStdManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class CarChargeStdManageController {
    @Autowired
    CarChargeStdManageService carChargeStdManageService;
    /**
     * 车辆收费标准写入
     * @param carChargeAndCarType
     * @return
     */
    @PostMapping("/DISP20210163")
    public Result insertCarChargeStd(@RequestBody Request<CarChargeAndCarType> carChargeAndCarType){
        return carChargeStdManageService.insertCarChargeStd(carChargeAndCarType.getBody());
    }
    /**
     * 查询车辆收费标准
     * @param carChargeStdManage
     * @return
     */
    @PostMapping("/DISP20210164")
    public Result selectCarChargeStd(@RequestBody Request<CarChargeStdManage> carChargeStdManage){
        return carChargeStdManageService.selectCarChargeStd(carChargeStdManage.getBody());
    }
    /**
     * 通过chrgId修改车辆收费标准
     * @param carChargeAndCarType
     * @return
     */
    @PostMapping("/DISP20210166")
    public Result updateCarChargeStdByChrgId(@RequestBody Request<CarChargeAndCarType> carChargeAndCarType){
        return carChargeStdManageService.updateCarChargeStdByChrgId(carChargeAndCarType.getBody());
    }
    @PostMapping("/DISP20210165")
    public Result deleteCarChargeStdBy(@RequestBody Request<CarChargeStdManage> carChargeStdManage){
        return carChargeStdManageService.deleteCarChargeStdBy(carChargeStdManage.getBody().getChrgId());
    }
}
