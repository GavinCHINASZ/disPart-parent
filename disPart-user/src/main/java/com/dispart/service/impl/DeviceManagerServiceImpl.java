package com.dispart.service.impl;

import com.dispart.dao.DeviceManagerMapper;
import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMa;
import com.dispart.dto.deviceManagerDto.DISP20210116FindDeMaOut;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import com.dispart.result.UserResultCodeEnum;
import com.dispart.service.DeviceManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
@Service
@Slf4j
@Transactional
public class DeviceManagerServiceImpl implements DeviceManagerService {
    @Autowired
    DeviceManagerMapper deviceManagerMapper;

    /**
     * 查询客户设备信息
     * @param inDto
     * @return
     */
    @Override
    public Result<DISP20210116FindDeMaOut> findDeMa(DISP20210116FindDeMa inDto) {
        DISP20210116FindDeMaOut getDISP20210116FindDeMaOut = new DISP20210116FindDeMaOut();
        ArrayList<DISP20210116FindDeMa> getDISP20210116FindDeMa = new ArrayList<>();
        if(inDto.getPageSize()==null || "".equals(inDto.getPageSize()) || inDto.getCurPage()==null || "".equals(inDto.getCurPage())){//分页参数为空，则不需要分页
            inDto.setPageSize(0);
        }else {
            inDto.setCurPage(inDto.getPageSize()*(inDto.getCurPage()-1));
        }
        //查询条件全部为空，则查询全部信息
        try{
            if((inDto.getCertNo()==null || "".equals(inDto.getCertNo()))
                    & (inDto.getDeviceId()==null || "".equals(inDto.getDeviceId()))
                    & (inDto.getLegalTel()==null || "".equals(inDto.getLegalTel()))
                    & (inDto.getCname()==null || "".equals(inDto.getCname()))
                    & (inDto.getMerchantCode()==null || "".equals(inDto.getMerchantCode()))){
                getDISP20210116FindDeMa = deviceManagerMapper.findDeviceManager(inDto);
                getDISP20210116FindDeMaOut.setDeMaList(getDISP20210116FindDeMa);
                if(inDto.getPageSize()>0){
                    getDISP20210116FindDeMaOut.setTolPageNum(deviceManagerMapper.findAllNum(inDto));
                }
            }else {//有查询条件
                if(inDto.getDeviceId()==null || "".equals(inDto.getDeviceId())){ //无设备号
                    getDISP20210116FindDeMa = deviceManagerMapper.findDeMaByMa(inDto);
                    getDISP20210116FindDeMaOut.setDeMaList(getDISP20210116FindDeMa);
                    if(inDto.getPageSize()>0){
                        getDISP20210116FindDeMaOut.setTolPageNum(deviceManagerMapper.findMNumByMa(inDto));
                    }
                }else {//有设备号
                    getDISP20210116FindDeMa = deviceManagerMapper.findDeMaByDe(inDto);
                    getDISP20210116FindDeMaOut.setDeMaList(getDISP20210116FindDeMa);
                    if(inDto.getPageSize()>0){
                        getDISP20210116FindDeMaOut.setTolPageNum(deviceManagerMapper.findMNumByDe(inDto));
                    }
                }

            }
        }catch (Exception e){
            log.info("查询客户设备信息-失败： "+e);
            throw new RuntimeException("查询客户设备信息失败，查询参数："+inDto);
        }
        log.info("查询客户设备信息-成功： "+getDISP20210116FindDeMaOut);
        return Result.build(getDISP20210116FindDeMaOut, ResultCodeBaseEnum.SUCCESS);
    }

    /**
     * 客户绑定语音设备
     * @param inDto
     * @return
     */
    @Override
    public Result addDeMa(DISP20210116FindDeMa inDto) {

        try {
            if(inDto.getCustomerId()==null || "".equals(inDto.getCustomerId())){
                return Result.build(null,1,"客户编号为空");
            }
            if(deviceManagerMapper.findNumByCustomer(inDto)>0){
                return Result.build(null, UserResultCodeEnum.USER_ID_NULL);
            }
        }catch (Exception e){
            log.error("客户绑定语音设备-查询客户已绑定设备失败： ",e);
            throw new RuntimeException("客户绑定语音设备-查询客户已绑定设备失败，参数"+inDto);
        }

        try {
            if(deviceManagerMapper.addDeviceManager(inDto)>0){
                log.info("客户绑定语音设备-成功");
                return Result.build(null,ResultCodeBaseEnum.SUCCESS);
            }
        }catch (Exception e){
            log.error("客户绑定语音设备-失败： ",e);
            throw new RuntimeException("客户绑定语音设备失败，参数: "+inDto);
        }
        return Result.ok();
    }

    /**
     * 修改客户绑定设备的信息
     * @param inDto
     * @return
     */
    @Override
    public Result uPDeMa(DISP20210116FindDeMa inDto) {
        if(inDto.getCustomerId()==null || "".equals(inDto.getCustomerId())){
            log.error("修改客户绑定设备的信息-失败-客户编号为空 ："+inDto);
            return Result.build(null,1,"客户编号不能为空");
        }
        if(inDto.getDeviceId()==null || "".equals(inDto.getDeviceId())){
            log.error("修改客户绑定设备的信息-失败-设备编号为空 ： "+inDto);
            return Result.build(null,1,"设备编号不能为空");
        }
        try {
            if (deviceManagerMapper.upDeviceManager(inDto)>0){
                log.info("修改客户绑定设备的信息-成功");
                return Result.build(null,ResultCodeBaseEnum.SUCCESS);
            }
        }catch (Exception e){
            log.error("修改客户绑定设备的信息-失败： "+inDto,e);
            throw new RuntimeException("修改客户绑定设备的信息，参数: "+inDto);
        }
        return Result.build(null,UserResultCodeEnum.USER_DEVICEMANEGER_NULL);
    }

    /**
     * 删除客户绑定的设备
     * @param inDto
     * @return
     */
    @Override
    public Result deDeMa(DISP20210116FindDeMa inDto) {
        if((inDto.getDeviceId() == null || "".equals(inDto.getDeviceId())) & (inDto.getCustomerId() == null || "".equals(inDto.getCustomerId()))){
            log.error("删除客户绑定的设备-失败-缺少客户编号或设备编号： "+inDto);
            return Result.build(null,1,"客户编号与设备编号必送一个");
        }
        try {
            if (deviceManagerMapper.deDeviceManager(inDto)>0){
                log.info("删除客户绑定的设备-成功");
                return Result.build(null,ResultCodeBaseEnum.SUCCESS);
            }
        }catch (Exception e){
            log.error("删除客户绑定的设备-失败： "+inDto,e);
            throw new RuntimeException("删除客户绑定的设备，参数: "+inDto);
        }
        return Result.build(null,UserResultCodeEnum.USER_DEVICEMANEGER_NULL);
    }
}
