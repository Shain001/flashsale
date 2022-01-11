package com.java.service;

import com.java.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceIml implements PaymentService {
    @Autowired
    private PaymentMapper mapper;

    /**
     * change order state
     * @param saleId
     * @param userId
     * @param status
     * @return
     */
    @Override
    public int changeOrderState(int saleId, int userId, int status){
        return mapper.changOderStatus(saleId, userId, status);
    }
}
