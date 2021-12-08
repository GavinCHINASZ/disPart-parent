package com.dispart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * OutGnLoanApplication
 * @author  zhaoshihao
 * @date 2021/10/9
*/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.dispart.dao")
public class OutGnLoanApplication {
    public static void main(String[] args) {
        SpringApplication.run(OutGnLoanApplication.class,args);
    }
}
