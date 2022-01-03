package com.java.scheduleTasks;


import com.java.service.ProductScanService;
import com.java.service.ProductScanServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductScanScheduler {

    @Autowired
    private ProductScanServiceImp service;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * Scan products that are about to start flash sale every 10s
     *
     * when found products, add the sale id and sale stocks to redis, key for sale id, value for sale stock
     *
     * @businesslogic:
     *  scan products first
     *          if has products
     *          check if this product has already saved in redis
     *          if not, save it to redis
     *  otherwise skip
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void scanForProductToBeStarted(){

        // get products
        List<Map<String, Object>> products = service.getToBeStarted();

        if (products.size() != 0){

            HashOperations vo = redisTemplate.opsForHash();

            // iterate products
            for ( int i=0; i < products.size(); i++){
                String saleId = products.get(i).get("id") + "";
                int stock = Integer.parseInt(products.get(i).get("stock") + "");

                // SETNX in redis, for checking exist purpose
                vo.putIfAbsent(saleId, stock, 0);
            }
        }
    }

    /**
     * when the sale begin, change the 'status' column in db from 0 to 1 first
     *
     * Then update state in redis
     *
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void changeStateForBeingSale(){
        System.out.println("changing for being sale");
        service.changeStateForBeingSale();

        List<Map<String, Object>> productsBeingSale = service.getBeingSale();

        if (productsBeingSale.size() != 0){

            HashOperations ho = redisTemplate.opsForHash();

            for (int i = 0; i <  productsBeingSale.size(); i++){
                String id = productsBeingSale.get(i).get("id") + "";
                int stock = (int) productsBeingSale.get(i).get("stock");
                ho.increment(id, stock, 1L);
            }
        }
    }

    /**
     * when the sale ends, change the 'status' column in db from 1 to 2 first
     *
     * Then update state in redis
     *
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void changeStateForAfterSale(){
        System.out.println("changing for after sale");
        service.changeStateForAfterSale();

        List<Map<String, Object>> productsAfterSale = service.getAfterSale();

        if (productsAfterSale.size() != 0){
            HashOperations ho = redisTemplate.opsForHash();

            for (int i = 0; i <  productsAfterSale.size(); i++){
                String id = productsAfterSale.get(i).get("id") + "";
                int stock = (int) productsAfterSale.get(i).get("stock");
                ho.increment(id, stock, 1L);
            }
        }
    }

}
