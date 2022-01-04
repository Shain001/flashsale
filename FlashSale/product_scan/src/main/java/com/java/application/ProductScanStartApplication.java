package com.java.application;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
@MapperScan("com.java.mapper")
@EnableScheduling // open scheduler
@EnableCaching  // open cache in springboot
public class ProductScanStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductScanStartApplication.class);
    }
}
