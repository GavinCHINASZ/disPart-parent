package com.dispart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wujie
 * @version 1.0.0:
 * @title UserApplication
 * @Description TODO
 * @dateTime 2021/6/3 15:37
 * @Copyright 2020-2021
 */
@SpringBootApplication
@MapperScan({"com.dispart.mapper","com.dispart.dao.mapper","com.dispart.dao"})
//@ComponentScan(basePackages = {"com.dispart"})
@EnableDiscoveryClient
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
