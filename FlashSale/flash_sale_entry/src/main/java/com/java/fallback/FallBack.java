package com.java.fallback;

import com.java.service.FlashSaleDeductRedisConsumer;
import org.springframework.stereotype.Component;

// Dont forget @Component
@Component
public class FallBack implements FlashSaleDeductRedisConsumer {
    @Override
    public String deductRedis(String userId, String saleId) {
        return "server is busy, try later";
    }
}
