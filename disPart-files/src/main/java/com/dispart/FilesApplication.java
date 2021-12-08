package com.dispart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

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
@EnableScheduling
public class FilesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilesApplication.class,args);
    }
}
