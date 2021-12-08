package com.dispart;

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
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);

    }
}
