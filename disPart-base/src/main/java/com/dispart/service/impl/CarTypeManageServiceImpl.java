package com.dispart.service.impl;

import com.dispart.dao.mapper.CarTypeManageMapper;
import com.dispart.model.base.CarTypeAndDetails;
import com.dispart.model.base.CarTypeEnum;
import com.dispart.model.base.CarTypeManage;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CarTypeManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CarTypeManageServiceImpl implements CarTypeManageService {
    @Resource
    CarTypeManageMapper carTypeManageMapper;

    /**
     * 车辆类型数据成功写入
     * @param carTypeManage
     * @return
     */
    @Override
    public Result insertCarType(CarTypeManage carTypeManage) {
        log.info("开始插入数据====");
        log.info(carTypeManage.getVehicleTpId());
        DecimalFormat decimalFormat=new DecimalFormat("000000");
        carTypeManage.setCreatTime(new Date());
        carTypeManage.setUpTime(new Date());
        log.info("开始打印==="+CarTypeEnum.VEHICEL_TPFU.getCode());
        if(carTypeManage.getVehicleTpId().equals(CarTypeEnum.VEHICEL_TPF.getCode())){
            carTypeManage.setVehicleTp(CarTypeEnum.VEHICEL_TPF.getType());
        }
        else if(carTypeManage.getVehicleTpId().equals(CarTypeEnum.VEHICEL_TPS.getCode())){
            carTypeManage.setVehicleTp(CarTypeEnum.VEHICEL_TPS.getType());
        }
        else if(carTypeManage.getVehicleTpId().equals(CarTypeEnum.VEHICEL_TPT.getCode())){
            carTypeManage.setVehicleTp(CarTypeEnum.VEHICEL_TPT.getType());
        }
        else {
            carTypeManage.setVehicleTp(CarTypeEnum.VEHICEL_TPFU.getType());
        }
        String formatVehicleId = decimalFormat.format(carTypeManageMapper.selectEmployeeIdSeq());
        carTypeManage.setVehicleId(formatVehicleId);
        log.info(formatVehicleId);
        Integer row = carTypeManageMapper.insertCarType(carTypeManage);
        if(!StringUtils.isEmpty(row)){
            log.info("车辆类型数据成功写入!");
            return Result.ok("车辆类型数据成功写入!");
        }
        else{
            log.error("车辆类型数据写入失败!");
            return Result.build(200,"车辆类型数据写入失败!");
        }
    }

    /**
     * 查找车辆信息
     * @param carTypeManages
     * @return
     */
    @Override
    public Result findCarInfoByVehicleTp(CarTypeManage carTypeManages) {
        List<CarTypeAndDetails> carTypeAndDetailsa=new ArrayList<>();
        List<CarTypeManage> carInfoByVehicle = carTypeManageMapper.findCarInfoByVehicleTp(carTypeManages);
        int count=carInfoByVehicle.size();
        int i=0;
        CarTypeAndDetails carTypeAndDetails1=new CarTypeAndDetails();
        List<CarTypeAndDetails> sortList=new ArrayList<>();
        List<CarTypeManage> carInfoByVehiclelist2=new ArrayList<>();
        List<CarTypeManage> carInfoByVehiclelist=new ArrayList<>();
        CarTypeAndDetails carTypeAndDetails2=new CarTypeAndDetails();
        if(carInfoByVehicle.size()>0){
            for(CarTypeManage carTypeManageZ:carInfoByVehicle) {
                i++;
                if(carTypeAndDetails1.getVehicleTp()==null){
                    carTypeAndDetails1.setVehicleTp(carTypeManageZ.getVehicleTp());
                    carTypeAndDetails1.setVehicleTpId(carTypeManageZ.getVehicleTpId());
                }
                if(carTypeAndDetails1.getVehicleTp().equals(carTypeManageZ.getVehicleTp())){
                    carInfoByVehiclelist.add(carTypeManageZ);
                    carTypeAndDetails1.setCarTypeManage(carInfoByVehiclelist);
                }
                 else{
                    if(!Objects.equals(carTypeManageZ.getVehicleTp(),carTypeAndDetails2.getVehicleTp())){
                        //判断什么时候才能新开一个carTypeAndDetails2对象
                        carTypeAndDetails2=new CarTypeAndDetails();
                        carTypeAndDetails2.setVehicleTp(carTypeManageZ.getVehicleTp());
                        carTypeAndDetails2.setVehicleTpId(carTypeManageZ.getVehicleTpId());
                        carInfoByVehiclelist2=new ArrayList<>();
                        //控制最后一次不添加数据
                        if(i<count-1){
                            carTypeAndDetailsa.add(carTypeAndDetails2);
                        }
                    }

                    if(Objects.equals(carTypeAndDetails2.getVehicleTp(),carTypeManageZ.getVehicleTp())){
                        carInfoByVehiclelist2.add(carTypeManageZ);
                        carTypeAndDetails2.setCarTypeManage(carInfoByVehiclelist2);
                    }
                }

            }
            carTypeAndDetailsa.add(carTypeAndDetails2);
            carTypeAndDetailsa.add(carTypeAndDetails1);
            for(CarTypeAndDetails carInfo:carTypeAndDetailsa) {
                if (Objects.equals(carInfo.getVehicleTpId(), CarTypeEnum.VEHICEL_TPF.getCode())) {
                    sortList.add(carInfo);
                    break;
                }
            }
            for(CarTypeAndDetails carInfo:carTypeAndDetailsa) {
                if (Objects.equals(carInfo.getVehicleTpId(), CarTypeEnum.VEHICEL_TPS.getCode())) {
                    sortList.add(carInfo);
                    break;
                }
            }
            for(CarTypeAndDetails carInfo:carTypeAndDetailsa) {
                if (Objects.equals(carInfo.getVehicleTpId(), CarTypeEnum.VEHICEL_TPT.getCode())) {
                    sortList.add(carInfo);
                    break;
                }
            }
            for(CarTypeAndDetails carInfo:carTypeAndDetailsa) {
                if (Objects.equals(carInfo.getVehicleTpId(), CarTypeEnum.VEHICEL_TPFU.getCode())) {
                    sortList.add(carInfo);
                    break;
                }
            }
            log.info("查找车辆信息成功！"+ sortList);
            return Result.ok(sortList);
            }
        else{
            log.error("未查询到相关数据！");
            //return Result.fail("未查询到相关数据！");
            return Result.build(200,"未查询到相关数据！");
        }
    }

    /**
     * 车辆信息修改
     * @param carTypeManage
     * @param carTypeManage
     * @return
     */
    @Override
    public Result updateCarTypeByVehicle(CarTypeManage carTypeManage) {
        List<CarTypeManage> carInfoByVehicles = carTypeManageMapper.findCarInfoByVehicleTpa(carTypeManage.getVehicleId(),carTypeManage.getVehicleTpId());
        for (CarTypeManage carInfoByVehicle : carInfoByVehicles) {
//            if ((!carInfoByVehicle.getVehicleTp().equals(carTypeManage.getVehicleTp())) == true) {
//                log.error("车辆类别不可修改");
//                return Result.fail("车辆类别不可修改");
//            } else {
                carTypeManage.setUpTime(new Date());
                carTypeManage.setCreatTime(carInfoByVehicle.getCreatTime());
               // carTypeManage.setVehicleId(String.format("%040d",new BigInteger(UUID.randomUUID().toString()
            // .replace("-",""),16)).substring(2,5));
                Integer row = carTypeManageMapper.updateCarTypeByVehicle(carTypeManage);

                if (row != null) {
                    log.info("车辆信息修改成功！");
                    return Result.ok("车辆信息修改成功！");
                } else {
                    log.error("车辆信息修改失败！");
                    return Result.build(200,"车辆信息修改失败！");
                }
            }
       // }
        return null;
    }
    /**
     * 车辆信息删除
     * @param carTypeManage
     * @return
     */
    @Override
    public Result deleteCarTypeByVehicle(CarTypeManage carTypeManage) {
        Integer row = carTypeManageMapper.deleteCarTypeByVehicle(carTypeManage);
        if(row!=null){
            log.info("车辆信息删除成功！");
            return Result.ok("车辆信息删除成功！");
        }
        else {
            log.error("车辆信息删除失败！");
            return Result.build(200,"车辆信息删除失败！");
        }
    }
    /**
     * 根据车型信息查找车辆信息
     * @param request
     * @return
     */
    public Result findCarInfo(Request<CarTypeManage> request){
        CarTypeManage body = request.getBody();
        List<CarTypeManage> list;
        try {
            list = carTypeManageMapper.findCarInfoByVehicleTp(body);
        } catch (Exception e) {
           log.error("查找车辆信息失败",e);
           throw new RuntimeException("查找车辆信息失败",e);
        }
        if(list!=null&&list.size()>20){
            list=list.subList(0,21);
        }
        return Result.ok(list);
    }
}
