package com.java.controller;

import com.java.com.java.service.PaymentConsumerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentConsumerController {

    @Autowired
    private PaymentConsumerFeign feign;

    @RequestMapping(value = "/paymentEntry", method = RequestMethod.POST)
    public String transferPaymentRequest(@RequestParam("userId") String userId, @RequestParam("saleId") String saleId){
        return feign.consumePayment(userId, saleId);
    }

}
