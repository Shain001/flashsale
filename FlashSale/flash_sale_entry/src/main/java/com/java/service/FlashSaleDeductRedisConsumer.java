package com.java.service;

import com.java.fallback.FallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// wait to update Hystrix service
// 注意， 此处的name是要调用的service注册名， 不是consumer的service注册名！
@FeignClient(name="deductRedis", fallback= FallBack.class)
//@RestController
public interface FlashSaleDeductRedisConsumer {

    @RequestMapping(value = "/deductRedis", method = RequestMethod.POST)
    String deductRedis(@RequestParam("userId") String userId, @RequestParam("saleId") String saleId);
}
