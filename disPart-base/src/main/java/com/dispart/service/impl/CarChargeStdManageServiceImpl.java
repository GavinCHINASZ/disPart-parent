package com.dispart.service.impl;

import com.dispart.dao.mapper.CarChargeStdManageMapper;
import com.dispart.dao.mapper.CarTypeManageMapper;
import com.dispart.model.base.*;
import com.dispart.result.Result;
import com.dispart.service.CarChargeStdManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class CarChargeStdManageServiceImpl implements CarChargeStdManageService {
    @Resource
    CarChargeStdManageMapper carChargeStdManageMapper;
    @Resource
    CarTypeManageMapper carTypeManageMapper;

    /**
     * 车辆收费标准写入
     *
     * @param carChargeAndCarType
     * @return
     */
    @Override
    public Result insertCarChargeStd(CarChargeAndCarType carChargeAndCarType) {
        Integer row=null;
        DecimalFormat decimalFormat=new DecimalFormat("000000");
        List<CarChargeStdManage> carChargeStdManages= carChargeAndCarType.getCarChargeStdManage();
        for(CarChargeStdManage carChargeStdManage:carChargeStdManages)
        {
            List<CarTypeManage> carInfoByVehicleTpa = carTypeManageMapper.findCarInfoByVehicleTpa(carChargeAndCarType.getVehicleId(),carChargeAndCarType.getVehicleTpId());
            for (CarTypeManage carTypeManage : carInfoByVehicleTpa) {
                carChargeStdManage.setVehicleTp(carTypeManage.getVehicleTp());
                carChargeStdManage.setVehicle(carTypeManage.getVehicle());
                carChargeStdManage.setVehicleId(carChargeAndCarType.getVehicleId());
            }
            String formatChrgId = decimalFormat.format(carChargeStdManageMapper.selectEmployeeIdSeq());
            log.info(formatChrgId);
            carChargeStdManage.setCreatTime(new Date());
            carChargeStdManage.setUpTime(new Date());
            carChargeStdManage.setChrgId(formatChrgId);
           /* if(Objects.equals(carChargeStdManage.getChrgTp(), ChrgTyEnum.EMPTY_CAR.getCarType()))
            {
                carChargeStdManage.setChrgTp(ChrgTyEnum.EMPTY_CAR.getCode());
            }
            else if(Objects.equals(carChargeStdManage.getChrgTp(), ChrgTyEnum.SUPPLY_CAR.getCarType()))
            {
                carChargeStdManage.setChrgTp(ChrgTyEnum.SUPPLY_CAR.getCode());
            }
            else
            {
                carChargeStdManage.setChrgTp(ChrgTyEnum.FREE_MON_CAR.getCode());
            }
            if(Objects.equals(carChargeStdManage.getChrgMd(), ChrgMdEnum.PER_HOUR.getChrgType()))
            {
                carChargeStdManage.setChrgMd(ChrgMdEnum.PER_HOUR.getCode());
            }
            else
            {
                carChargeStdManage.setChrgMd(ChrgMdEnum.FIX_FEE.getCode());
            }*/
             row = carChargeStdManageMapper.insertCarChargeStd(carChargeStdManage);
        }

        if (row != null) {
            log.info("车辆收费标准成功写入!");
            return Result.ok("车辆收费标准成功写入!");
        } else {
            log.error("车辆收费标准写入失败!");
            return Result.build(200,"车辆收费标准写入失败!");
        }
    }

    /**
     * 查询车辆收费标准
     *
     * @param carChargeStdManage
     */
    @Override
    public Result selectCarChargeStd(CarChargeStdManage carChargeStdManage) {
       // List<CarChargeAndManage> carChargeAndManages = new ArrayList<>();
        CarChargeAndManage carChargeAndManage = new CarChargeAndManage();
        //carChargeStdManage.setTolPageNum(carTypeManageMapper.findCarInfoByVehicleForChargeCount(carChargeStdManage));
        if (carChargeStdManage.getPageNum() != null && carChargeStdManage.getPageSize() != null) {
            //分页处理
            carChargeStdManage.setPageNum((carChargeStdManage.getPageNum()-1) * carChargeStdManage.getPageSize());
        }
        List<CarTypeManage> carInfoByVehicleForCharge = carTypeManageMapper.findCarInfoByVehicleForCharge(carChargeStdManage.getVehicleTp(),
                carChargeStdManage.getVehicleId(),carChargeStdManage.getPageNum(),carChargeStdManage.getPageSize());
        carChargeAndManage.setTolPageNum(carTypeManageMapper.findCarInfoByVehicleForChargeCount(carChargeStdManage.getVehicleTp(),
                carChargeStdManage.getVehicle(),0,carChargeStdManage.getPageSize()));
        for(CarTypeManage carTypeManage:carInfoByVehicleForCharge){
            List<CarChargeStdManage> carChargeStdManages = carChargeStdManageMapper.selectCarChargeStdByVehicleId(carTypeManage.getVehicleId(),carTypeManage.getVehicleTp());
            for(CarChargeStdManage chargeStdManage:carChargeStdManages){

                carTypeManage.getCarChargeStdManage().add(chargeStdManage);
            }
            carChargeAndManage.getCarTypeManage().add(carTypeManage);
        }
        return Result.ok(carChargeAndManage);
    }

    /**
     * 通过chrgId修改车辆收费标准
     * @param carChargeAndCarType
     * @return
     */
    @Override
    public Result updateCarChargeStdByChrgId(CarChargeAndCarType carChargeAndCarType) {
        Integer row=null;
        List<CarChargeStdManage> carChargeStdManages= carChargeAndCarType.getCarChargeStdManage();
        for(CarChargeStdManage carChargeStdManage:carChargeStdManages)
        {
            //List<CarTypeManage> carInfoByVehicleTpa = carTypeManageMapper.findCarInfoByVehicleTpa(carChargeAndCarType.getVehicleId(),carChargeAndCarType.getVehicleTpId());
//            for (CarTypeManage carTypeManage : carInfoByVehicleTpa) {
//                carChargeStdManage.setVehicleTp(carTypeManage.getVehicleTp());
//                carChargeStdManage.setVehicle(carTypeManage.getVehicle());
//                carChargeStdManage.setVehicleId(carChargeAndCarType.getVehicleId());
//            }s

            //carChargeStdManage.setCreatTime(new Date());
            carChargeStdManage.setUpTime(new Date());
            //carChargeStdManage.setChrgId(String.format("%040d", new BigInteger(UUID.randomUUID().toString()
                  //  .replace("-", ""), 16)).substring(2, 5));
            row = carChargeStdManageMapper.updateCarChargeStd(carChargeStdManage,carChargeAndCarType);
        }
      /* CarChargeStdManage carChargeStdManageResult = carChargeStdManageMapper.selectCarChargeStdBychrgId(carChargeStdManage.getChrgId());
       carChargeStdManage.setUpTime(new Date());
       carChargeStdManage.setCreatTime(carChargeStdManageResult.getCreatTime());
       log.info("开始打印====");
       log.info(carChargeStdManage.getChrgTp().toString());*/
       /* if(Objects.equals(carChargeStdManage.getChrgTp(), ChrgTyEnum.EMPTY_CAR.getCarType()))
        {
            carChargeStdManage.setChrgTp(ChrgTyEnum.EMPTY_CAR.getCode());
        }
        if(Objects.equals(carChargeStdManage.getChrgTp(), ChrgTyEnum.SUPPLY_CAR.getCarType()))
        {
            carChargeStdManage.setChrgTp(ChrgTyEnum.SUPPLY_CAR.getCode());
        }
        if(Objects.equals(carChargeStdManage.getChrgTp(), ChrgTyEnum.FREE_MON_CAR.getCarType()))
        {
            carChargeStdManage.setChrgTp(ChrgTyEnum.FREE_MON_CAR.getCode());
        }
        if(Objects.equals(carChargeStdManage.getChrgMd(), ChrgMdEnum.PER_HOUR.getChrgType()))
        {
            carChargeStdManage.setChrgMd(ChrgMdEnum.PER_HOUR.getCode());
        }
        if(Objects.equals(carChargeStdManage.getChrgMd(), ChrgMdEnum.FIX_FEE.getChrgType()))
        {
            carChargeStdManage.setChrgMd(ChrgMdEnum.FIX_FEE.getCode());
        }*/
        if(row!=null){
            log.info("车辆收费标准修改成功！");
            return Result.ok("车辆收费标准修改成功！");
        }
        else{
            log.error("车辆收费标准修改失败！");
            return Result.build(200,"车辆收费标准修改失败！");
        }
    }

    @Override
    public Result deleteCarChargeStdBy(String chrgId) {
        Integer row = carChargeStdManageMapper.deleteCarChargeStdByChrgId(chrgId);
        if(row!=null){
            log.info("车辆收费标准删除成功!");
            return Result.ok("车辆收费标准删除成功!");
        }
        else{
            log.error("车辆收费标准删除失败!");
            return Result.build(200,"车辆收费标准删除失败!");
        }
    }
}
