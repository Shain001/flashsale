package com.java.controller;

import com.java.service.PaymentService;
import com.sun.xml.internal.ws.handler.HandlerException;
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

    //private final static String DATABASE_EXCEPTION = "DATABASE_EXCEPTION";

    @RequestMapping(value = "/processPayment", method = RequestMethod.POST)
    public String processPayment(@RequestParam("userId") String userId, @RequestParam("saleId") String saleId) throws Exception {
        // TODO: PaymentAPI
        // Here status is hardwrite as 1, if there is a real payment API, the status  value should depends on
        // the return value of paymentAPI, e.g. 1 -> pay sucess, 0 -> pay fail
        int state = service.changeOrderState(saleId, userId, 1);

//        // Test Circuit Breaker timeout
//        Thread.sleep(20000);

        // When update failed, throw exception, so that consumer return 降级页面
        // 如果不加该判断， 不会返回异常
        if (state == 0){
            throw new Exception("Not Created Yet");
        }

        return state+" Thread: " + Thread.currentThread().getName();
    }
}
