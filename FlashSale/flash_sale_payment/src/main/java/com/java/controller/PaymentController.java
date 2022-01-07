package com.java.controller;

import com.java.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService service;

    @RequestMapping(value = "/processPayment", method = RequestMethod.POST)
    public String processPayment(@RequestParam("userId") String userId, @RequestParam("saleId") String saleId){
        // TODO: PaymentAPI
        // Here status is hardwrite as 1, if there is a real payment API, the status  value should depends on
        // the return value of paymentAPI, e.g. 1 -> pay sucess, 0 -> pay fail
        return service.changeOrderState(saleId, userId, 1)+"";
    }
}
