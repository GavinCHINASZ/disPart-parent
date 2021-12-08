package com.dispart.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//@Component
public class RestTemplateConfig {
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return  new RestTemplate();
//    }
}
