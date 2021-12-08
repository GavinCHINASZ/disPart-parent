package com.dispart.config;

import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author 黄贵川
 * @date 2021-09-27
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = null;
        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (Exception e) {
            log.error("MinIOConfig new MinioClient异常", e);
            return minioClient;
        }
        return minioClient;
    }
}
