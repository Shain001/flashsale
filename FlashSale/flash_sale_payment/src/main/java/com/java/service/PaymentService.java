package com.java.service;

public interface PaymentService {
    int changeOrderState(String saleId, String userId, int status);
}
