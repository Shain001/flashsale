package com.java.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FlashSaleDeductRedisService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void putRabbitMQ(String saleId, String userId){
        Map<String, Object> message = new HashMap<>();
        message.put("saleId", saleId);
        message.put("userId", userId);
        rabbitTemplate.convertAndSend("flash_sale", null, message);
    }
}
