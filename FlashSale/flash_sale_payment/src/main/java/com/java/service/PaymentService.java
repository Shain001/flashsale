package com.java.service;

public interface PaymentService {
    int changeOrderState(int saleId, int userId, int status);
}
