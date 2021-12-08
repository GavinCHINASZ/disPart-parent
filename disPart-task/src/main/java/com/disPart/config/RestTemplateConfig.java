package com.disPart.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplate1")
    public RestTemplate restTemplate1() {
        return new RestTemplate();
    }

    @Bean(name = "restTemplate2")
    @LoadBalanced
    public RestTemplate restTemplate2() {
        return new RestTemplate();
    }
}
