package com.java.application;

import com.java.filters.Filters;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.java.*")
@EnableDiscoveryClient
@EnableZuulProxy
public class FalshSaleZuulStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(FalshSaleZuulStartApplication.class);
    }

    @Bean
    public Filters filters(){
        return new Filters();
    }

    /**
     * Initializing Redisson
     */
    @Bean
    public RBloomFilter<Integer> rBloomFilter() throws IOException {
        Config conf = new Config();
        conf = Config.fromYAML(RedissonConfiguration.class.getClassLoader().getResource("redisson-config.yml"));
        Redisson redisson = (Redisson) Redisson.create(conf);
        RBloomFilter<Integer> rBloomFilter = redisson.getBloomFilter("saleIds");
        return rBloomFilter;
    }

}
