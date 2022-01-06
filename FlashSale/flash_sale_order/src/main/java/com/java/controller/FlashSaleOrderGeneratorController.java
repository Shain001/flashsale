package com.java.controller;

import com.java.service.OrderService;
import com.java.utils.GenerateOrder;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class FlashSaleOrderGeneratorController {

    @Autowired
    private OrderService service;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "flash_sale_queue1"),
            exchange = @Exchange(value = "flash_sale",type = "fanout")
    ))
    public void consumeMQ(@Payload Map<String,Object> message, Channel channel, @Headers Map<String,Object> headers){
        try{
            // get salId, userId
            String saleId = message.get("saleId") + "";
            String userId = message.get("userId") + "";

            //Generate OrderNo
            String orderNo = GenerateOrder.generateOrder(userId);

            // TODO:
            //  Call Payment API Here, then program runs to another Moduel, that model return orderOrder to front page,
            //  Then the user can process Payment, By this way when the user complete payment, the order will definetlly
            //  be written in the DB, so the stauts of the order raw in DB can be changed. The situation where cannot find
            //  order in DB should be avoid. Becasue payment process takes at least 10s or more. After user compelete payment
            //  and click the button, we change state for the order in DB.

            // save order to DB
            int generateState = service.generateOrder(orderNo, userId, saleId);

            // deduct stock in DB

            int updateState = service.deductStock(saleId);

            // Here, how to handle the situation where insertion is failed? Since not returning State here.
            // Shouldn't let user know the order created failed since they already got the eligibility to buy the product
            // and also is doing the payment process

            if (generateState == 1 && updateState == 1){
                channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG),false);
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        //

    }
}
