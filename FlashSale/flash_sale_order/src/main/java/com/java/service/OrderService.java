package com.java.service;

public interface OrderService {
    int generateOrder(String orderNo, String userId, String saleId);

    int changOderStatus(String orderNo, int status);

    int deductStock(String saleId);
}
