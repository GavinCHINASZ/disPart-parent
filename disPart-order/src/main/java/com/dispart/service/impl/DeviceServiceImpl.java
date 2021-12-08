package com.dispart.service.impl;

import com.dispart.dao.DeviceMapper;
import com.dispart.dto.deviceManagerDto.DeviceVo;
import com.dispart.result.Result;
import com.dispart.service.DeviceService;
import com.dispart.service.PlaySignService;
import com.dispart.utils.YunKeyUtils;
import com.dispart.utils.base.AssenbleManager;
import com.dispart.vo.horn.TerminalPlayModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Service("deviceService")
@Slf4j
@Transactional
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceMapper deviceMapper;


    @Override
    public DeviceVo getDevicePar(String pType,String pNm) {
        log.info("正在获取云喇叭参数 ");
        return deviceMapper.findDevice(pType,pNm);
    }
}
