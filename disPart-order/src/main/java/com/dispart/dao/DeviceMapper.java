package com.dispart.dao;


import com.dispart.dto.deviceManagerDto.DeviceVo;
import com.dispart.dto.deviceManagerDto.YunInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface DeviceMapper {

    //查询云喇叭参数
    DeviceVo findDevice(@Param("paramType") String paramType, @Param("paramNm") String paramNm);
    //查询设备编号
    String findDeviceIdByCustomer(@Param("customerId") String customerId);
    //获取云喇叭流水号
    Integer findReqId(@Param("seqName") String seqName);
    //云喇叭播报内容查询
    ArrayList<YunInfoVo> findYunInfoVo(@Param("mainOrderId") String mainOrderId);

}
