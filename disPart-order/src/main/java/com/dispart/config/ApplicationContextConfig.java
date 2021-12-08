package com.dispart.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean(name = "restTemplate1")
    public RestTemplate restTemplate1() {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setConnectTimeout(30000);
        httpComponentsClientHttpRequestFactory.setReadTimeout(30000);
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(30000);
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

    @Bean(name = "restTemplate2")
    @LoadBalanced
    public RestTemplate restTemplate2() {
        return new RestTemplate();
    }

}
