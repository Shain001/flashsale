package com.java.application;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
@MapperScan("com.java.mapper")
@EnableScheduling // open scheduler
@EnableCaching  // open cache in springboot
public class ProductScanStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductScanStartApplication.class);
    }

    /**
     * Initializing Redisson
     */
    @Bean
    public Redisson redisson() throws IOException {
        Config conf = new Config();
        conf = Config.fromYAML(RedissonConfiguration.class.getClassLoader().getResource("redisson-config.yml"));
        return (Redisson) Redisson.create(conf);
    }
}
