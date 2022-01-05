package com.java.controller;

import com.java.service.FlashSaleDeductRedisConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlashSaleEntryController {
    @Autowired
    FlashSaleDeductRedisConsumer feignService;

    @RequestMapping(value = "/saleEntry")
    public String entry(@RequestParam("userId") String userId, @RequestParam("saleId") String saleId){
        return feignService.deductRedis(userId, saleId);
    }

}
