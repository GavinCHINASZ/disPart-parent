package com.dispart.utils;

import com.dispart.dao.DeviceMapper;
import com.dispart.service.DeviceService;
import com.dispart.service.impl.DeviceServiceImpl;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 获取云喇叭参数
 */
@Slf4j
public class YunKeyUtils {
//    @Autowired
//    private DeviceService deviceService1;

    private static final
    DeviceService deviceService = SpringUtil.getBean("deviceService", DeviceServiceImpl.class);

//    @PostConstruct
//    public void init(){
//        deviceService = deviceService1;
//    }
    //云喇叭账号
    private static String yunAccessId = null;
    //云喇叭密码
    private static String yunAccessKey = null;
    //云喇叭产品Key
    private static String yunProductKey = null;

    private YunKeyUtils() {}

    /**
     * 云喇叭账号
     */
    public static String getYunAccessId() {
        log.info("开始获取云喇叭账号:{}", yunAccessId);
        if(yunAccessId == null) {
            synchronized (YunKeyUtils.class) {
                if(yunAccessId == null) {
                    //获取云喇叭账号
                    yunAccessId = deviceService.getDevicePar("05","yun.accessId").getParamVal();
                    log.info("获取云喇叭账号:{}", yunAccessId);
                }
            }
        }
        return yunAccessId;
    }

    /**
     * 云喇叭密码
     */
    public static String getYunAccessKey() {
        log.info("开始获取云喇叭密码:{}", yunAccessKey);
        if(yunAccessKey == null) {
            synchronized (YunKeyUtils.class) {
                if(yunAccessKey == null) {
                    //云喇叭密码
                    yunAccessKey = deviceService.getDevicePar("05","yun.accessKey").getParamVal();
                    log.info("获取云喇叭密码:{}", yunAccessKey);
                }
            }
        }
        return yunAccessKey;
    }

    /**
     * 云喇叭产品Key
     */
    public static String getYunProductKey() {
        log.info("开始获取云喇叭产品Key:{}", yunProductKey);
        if(yunProductKey == null) {
            synchronized (YunKeyUtils.class) {
                if(yunProductKey == null) {
                    //云喇叭产品Key
                    yunProductKey = deviceService.getDevicePar("05","yun.productKey").getParamVal();
                    log.info("获取云喇叭产品Key:{}", yunProductKey);
                }
            }
        }
        return yunProductKey;
    }


}
