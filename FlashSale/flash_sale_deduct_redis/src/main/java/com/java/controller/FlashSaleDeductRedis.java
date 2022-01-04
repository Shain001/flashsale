package com.java.controller;

import com.java.util.State;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FlashSaleDeductRedis {

    @Autowired
    private Redisson redisson;

    @Autowired
    private RedisTemplate redisTemplate;

    // keys in redis for sale info query
    final private List<String> hashKeys = new ArrayList<String>(Arrays.asList("stock", "productId", "price"));

    @RequestMapping(value = "/deductRedis", method = RequestMethod.POST)
    public String deduct(String userId, String saleId){

        // initializing redisTemplates
        HashOperations ho = redisTemplate.opsForHash();
        SetOperations lo = redisTemplate.opsForSet();

        // Initialize redisson lock
        String lockKey = "lock_" + saleId;
        RLock lock = redisson.getLock(lockKey);

        // check the status of saleId
        int state = (int) ho.get(saleId, "status");

        if (State.NOT_START.getValue() == state){
            return "Sale will start soon";
        }

        if (State.END.getValue() == state){
            return "Sale has ended";
        }

        // check if current user has bought before
        String checkUserKey = "has_" + userId;
        if (lo.isMember(checkUserKey, userId)){
            return "Don't be greedy";
        }

        try{
            // Adding Distributed Lock
            lock.lock();

            // Get sale info
            List<Object> sale_info = ho.multiGet(saleId, hashKeys);
            int stock = (int) sale_info.get(0);
            int productId = (int) sale_info.get(1);
            int price = (int) sale_info.get(2);

            // check stock
            if (stock <= 0){
                return "oops, out of stock";
            }

            // All good, buy success, update stock in redis, then put message into MQ
            ho.put(saleId, "stock", stock-1);
            return "Congratulations, you got it";

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


        return "1";
    }

    /**
     * Check redis stock method
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(String saleId){
        HashOperations ho = redisTemplate.opsForHash();

        List keys = new ArrayList<String>(Arrays.asList("1", "2", "3"));

        String toReturn = "";
        for (Object k : keys){
            int stock = (int) ho.get(k, "stock");
            toReturn += " sale id: " + k + " stock: " + stock;
        }

        return toReturn;
    }


}
