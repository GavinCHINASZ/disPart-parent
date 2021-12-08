package com.dispart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
public class BusineCommonsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusineCommonsApplication.class,args);
    }
}


