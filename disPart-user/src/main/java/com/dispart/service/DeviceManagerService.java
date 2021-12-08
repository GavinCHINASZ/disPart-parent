package com.dispart.service;

import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMa;
import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMaOut;
import com.dispart.result.Result;


public interface DeviceManagerService {
    //查询客户绑定设备的信息
    Result<DISP20210116FindDeMaOut> findDeMa(DISP20210116FindDeMa inDto);
    //给客户添加绑定设备
    Result addDeMa(DISP20210116FindDeMa inDto);
    //给客户修改绑定设备
    Result uPDeMa(DISP20210116FindDeMa inDto);
    //给客户删除绑定设备
    Result deDeMa(DISP20210116FindDeMa inDto);
}
