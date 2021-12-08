package com.dispart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * OutCCBApplication
 * @author  zhaoshihao
 * @date 2021/10/20
*/
@EnableDiscoveryClient
@SpringBootApplication
public class OutCCBApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutCCBApplication.class,args);
    }
}
