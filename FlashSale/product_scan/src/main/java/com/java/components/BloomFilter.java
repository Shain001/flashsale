package com.java.components;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BloomFilter {
    @Autowired
    private Redisson redisson;

    @Bean
    public RBloomFilter<Integer> getBloomFilter(){
        RBloomFilter<Integer> rBloomFilter = redisson.getBloomFilter("saleIds");

        return rBloomFilter;
    }
}
