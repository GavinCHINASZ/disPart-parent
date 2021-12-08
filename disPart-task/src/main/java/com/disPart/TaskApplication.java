package com.disPart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wujie
 * @version 1.0.0:
 * @title UserApplication
 * @Description TODO
 * @dateTime 2021/6/3 15:37
 * @Copyright 2020-2021
 */
@SpringBootApplication
@MapperScan("com.disPart.dao")
@EnableDiscoveryClient
@EnableScheduling
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class,args);
    }
}
