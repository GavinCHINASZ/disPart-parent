package com.dispart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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
@EnableFeignClients
@MapperScan("com.dispart.dao")
@EnableAsync
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

}
