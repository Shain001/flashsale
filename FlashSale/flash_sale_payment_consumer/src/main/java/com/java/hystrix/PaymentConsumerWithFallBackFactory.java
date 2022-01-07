package com.java.hystrix;

import com.java.com.java.service.PaymentConsumerFeign;
import com.java.controller.PaymentConsumerController;

public interface PaymentConsumerWithFallBackFactory extends PaymentConsumerFeign {
}
