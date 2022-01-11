package com.java.controller;

import com.java.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestSelectController {
    @Autowired
    OrderService service;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){

        String to_return = "";

        List<Map<String, Object>> temp = service.getAllOrder();

        for (Map<String, Object> t : temp){
            to_return += t.get("userId");
            to_return += " ";
        }

        return to_return;
    }

}
