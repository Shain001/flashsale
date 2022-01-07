package com.java.application;

import com.java.com.java.service.PaymentConsumerFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
@EnableFeignClients(clients = {PaymentConsumerFeign.class})
public class FlashSalePaymentConsumerStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashSalePaymentConsumerStartApplication.class);
    }
}
