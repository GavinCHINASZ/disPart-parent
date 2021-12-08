package com.dispart.service;


import com.dispart.dto.deviceManagerDto.DeviceVo;
import com.dispart.result.Result;
import org.springframework.stereotype.Component;

@Component
public interface DeviceService {
    //获取云喇叭参数
    DeviceVo getDevicePar(String pType,String pNm);
}
