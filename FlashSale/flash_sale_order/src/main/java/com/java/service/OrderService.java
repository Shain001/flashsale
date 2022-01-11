package com.java.service;

import java.util.List;
import java.util.Map;

public interface OrderService {
    int generateOrder(String orderNo, String userId, String saleId);

    int changOderStatus(String orderNo, int status);

    int deductStock(String saleId);

    List<Map<String, Object>> getAllOrder();
}
