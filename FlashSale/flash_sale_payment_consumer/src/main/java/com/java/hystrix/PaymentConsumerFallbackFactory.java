package com.java.hystrix;

import com.java.com.java.service.PaymentConsumerFeign;
import com.java.controller.PaymentConsumerController;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumerFallbackFactory implements FallbackFactory<PaymentConsumerFeign> {

    private static final Logger LOG = LoggerFactory.getLogger("logger");

    @Override
    public PaymentConsumerFeign create(Throwable throwable) {
        LOG.error("Exception = {}", throwable.getMessage());
        return new PaymentConsumerWithFallBackFactory() {
            @Override
            public String consumePayment(String userId, String saleId) {
                return "Oops, server is busy";
            }
        };
    }
}
