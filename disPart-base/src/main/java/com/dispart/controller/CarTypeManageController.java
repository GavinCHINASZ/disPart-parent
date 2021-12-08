package com.dispart.controller;

import com.dispart.model.base.CarTypeManage;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CarTypeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class CarTypeManageController {
    @Autowired
    CarTypeManageService carTypeManageService;
    /**
     * 车辆类型数据成功写入
     * @param carTypeManage
     * @return
     */
    @PostMapping("/DISP20210159")
    public Result insertCarType(@RequestBody Request<CarTypeManage> carTypeManage){
        return carTypeManageService.insertCarType(carTypeManage.getBody());
    }
    /**
     * 查找车辆信息
     * @param carTypeManage
     * @return
     */
    @PostMapping("/DISP20210160")
    public Result findCarInfoByVehicle(@RequestBody Request<CarTypeManage> carTypeManage){
        return carTypeManageService.findCarInfoByVehicleTp(carTypeManage.getBody());
    }
    /**
     * 车辆信息修改
     * @param carTypeManage
     * @return
     */
    @PostMapping("/DISP20210161")
    public  Result updateCarTypeByVehicle(@RequestBody Request<CarTypeManage> carTypeManage){
       return carTypeManageService.updateCarTypeByVehicle(carTypeManage.getBody());
    }
    /**
     * 车辆信息删除
     * @param carTypeManage
     * @return
     */
    @PostMapping("/DISP20210162")
    public Result deleteCarTypeByVehicle(@RequestBody Request<CarTypeManage> carTypeManage){
        return carTypeManageService.deleteCarTypeByVehicle(carTypeManage.getBody());
    }

    /**
     * 查找车辆信息
     * @param request
     * @return
     */
    @PostMapping("/DISP20210377")
    public Result findCarInfo(@RequestBody Request<CarTypeManage> request){
        return carTypeManageService.findCarInfo(request);
    }
}
