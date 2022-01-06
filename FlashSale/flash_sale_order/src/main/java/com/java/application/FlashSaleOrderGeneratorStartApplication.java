package com.java.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
@MapperScan("com.java.mapper")
public class FlashSaleOrderGeneratorStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashSaleOrderGeneratorStartApplication.class);
    }
}
