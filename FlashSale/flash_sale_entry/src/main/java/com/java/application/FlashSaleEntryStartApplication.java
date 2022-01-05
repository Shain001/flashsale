package com.java.application;

import com.java.service.FlashSaleDeductRedisConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.swing.*;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
// explicitly specify the Feign interface class becasue not in same package, otherwise @Autowired cannot find Feign Interface's bean
@EnableFeignClients(clients = {FlashSaleDeductRedisConsumer.class})
public class FlashSaleEntryStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashSaleEntryStartApplication.class);
    }
}
