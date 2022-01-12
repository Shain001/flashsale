package com.java.application;

import com.java.controller.FlashSaleDeductRedis;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.io.File;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableCaching
@EnableDiscoveryClient
public class FlashSaleDeductRedisStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashSaleDeductRedisStartApplication.class);
    }

    /**
     * Initializing Redisson
     */
    @Bean
    public Redisson redisson() throws IOException {
        Config conf = new Config();
        //conf = Config.fromYAML(RedissonConfiguration.class.getClassLoader().getResource("redisson-config.yml"));
        conf.useSentinelServers().addSentinelAddress("redis://192.168.100.128:26379")
                .addSentinelAddress("redis://192.168.100.128:26380")
                .addSentinelAddress("redis://192.168.100.128:26381")
                .setMasterName("mymaster")
//                .setCheckSentinelsList(false)
                .setReadMode(ReadMode.SLAVE);
        return (Redisson) Redisson.create(conf);
    }
}

