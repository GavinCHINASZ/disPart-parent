package com.dispart;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author wujie
 * @version 1.0.0:
 * @title OutHsbApplication
 * @Description TODO
 * @dateTime 2021/6/7 14:31
 * @Copyright 2020-2021
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.dispart.dao")
public class DataQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataQueryApplication.class,args);
    }
}
