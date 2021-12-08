package com.dispart.service.impl;

import cn.hutool.core.date.DateTime;
import com.dispart.dao.mapper.CarBlackListMapper;
import com.dispart.model.base.CarBlackList;
import com.dispart.model.base.CarBlackListAndTotalPage;
import com.dispart.result.Result;
import com.dispart.service.CarBlackListService;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CarBlackListServiceImpl implements CarBlackListService {
    @Resource
    CarBlackListMapper carBlackListMapper;
    @Override
    public Result inserCarBlackList(CarBlackList carBlackList , String operId) {
        carBlackList.setCreatTime(new Date());
        carBlackList.setUpTime(new Date());
        carBlackList.setOperId(operId);
        carBlackList.setModifyId(null);
        Integer integer = carBlackListMapper.inserCarBlackList(carBlackList,operId);
        if (integer>0){
            return Result.ok("车辆黑名单写入成功！");
        }
        else{
            return Result.build(200,"车辆黑名单写入失败");
        }
    }

    @Override
    public Result deteCarBlackList(CarBlackList carBlackList) {
        Integer integer = carBlackListMapper.deteCarBlackList(carBlackList);
        if (integer>0){
            return Result.ok("车辆黑名单删除成功！");
        }
        else{
            return Result.build(200,"车辆黑名单删除失败");
        }
    }

    @Override
    public Result updateCarBlackList(CarBlackList carBlackList, String operId) {
        List<CarBlackList> carBlackLists = carBlackListMapper.selectCarBlackList(carBlackList);
        for(CarBlackList carBlackList1:carBlackLists){
            carBlackList.setCreatTime(carBlackList1.getCreatTime());
            break;
        }
        carBlackList.setUpTime(new Date());
        carBlackList.setModifyId(operId);
        Integer integer = carBlackListMapper.updateCarBlackList(carBlackList,operId);
        if (integer>0){
            return Result.ok("车辆黑名单修改成功！");
        }
        else{
            return Result.build(200,"车辆黑名单修改失败");
        }
    }

    @Override
    public Result selectCarBlackList(CarBlackList carBlackList) {
        if (carBlackList.getPageNum() != null && carBlackList.getPageSize() != null) {
            //分页处理
            carBlackList.setPageNum((carBlackList.getPageNum()-1) * carBlackList.getPageSize());
        }
        CarBlackListAndTotalPage carBlackListAndTotalPage=new CarBlackListAndTotalPage();
        List<CarBlackList> carBlackLists = carBlackListMapper.selectCarBlackList(carBlackList);
        carBlackListAndTotalPage.setCarBlackListList(carBlackLists);
        carBlackListAndTotalPage.setTolPageNum(carBlackListMapper.countCarBlackList(carBlackList));
        if(carBlackLists.size()>0){
            return Result.ok(carBlackListAndTotalPage);
        }
        else {
            return Result.build(200,"车辆黑名单查询失败");
        }
    }
}
