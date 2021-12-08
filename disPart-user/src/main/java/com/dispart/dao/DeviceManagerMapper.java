package com.dispart.dao;


import com.dispart.dto.dataquery.Disp20210073InDto;
import com.dispart.dto.dataquery.Disp20210073OutMx;
import com.dispart.dto.dataquery.Disp20210074InDto;
import com.dispart.dto.dataquery.Disp20210074OutMx;
import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMa;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface DeviceManagerMapper {

    //查询全部客户绑定设备的信息
    ArrayList<DISP20210116FindDeMa> findDeviceManager(DISP20210116FindDeMa inDto);
    //查询总客户数量
    Integer findAllNum(DISP20210116FindDeMa inDto);
    //根据设备信息查询绑定设备的数量
    Integer findMNumByDe(DISP20210116FindDeMa inDto);
    //根据设备信息查询客户绑定设备信息
    ArrayList<DISP20210116FindDeMa> findDeMaByDe(DISP20210116FindDeMa inDto);

    //根据客户查询绑定设备的数量
    Integer findMNumByMa(DISP20210116FindDeMa inDto);
    //根据客户查询客户绑定设备信息
    ArrayList<DISP20210116FindDeMa> findDeMaByMa(DISP20210116FindDeMa inDto);


    //添加设备
    Integer addDeviceManager(DISP20210116FindDeMa inDto);
    //检查客户是否已绑定设备
    Integer findNumByCustomer(DISP20210116FindDeMa inDto);
    //修改客户绑定设备信息
    Integer upDeviceManager(DISP20210116FindDeMa inDto);
    //删除客户绑定设备
    Integer deDeviceManager(DISP20210116FindDeMa inDto);



}
