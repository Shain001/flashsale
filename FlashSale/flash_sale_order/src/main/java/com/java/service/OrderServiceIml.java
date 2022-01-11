package com.java.service;

import com.java.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceIml implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int generateOrder(String orderNo, String userId, String saleId) {
        return orderMapper.generateOrder(orderNo,userId, saleId);
    }

    @Override
    public int changOderStatus(String orderNo, int status){
        return orderMapper.changOderStatus(orderNo, status);
    }

    public int deductStock(String saleId){
        return orderMapper.deductStock(saleId);
    }

    @Override
    public List<Map<String, Object>> getAllOrder() {
        return orderMapper.getAllOrder();
    }
}
