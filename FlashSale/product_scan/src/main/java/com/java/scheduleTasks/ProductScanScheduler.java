package com.java.scheduleTasks;


import com.java.service.ProductScanService;
import com.java.service.ProductScanServiceImp;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ProductScanScheduler {

    @Autowired
    private ProductScanServiceImp service;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RBloomFilter<Integer> rBloomFilter;

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
        // refresh bloom filter
        // rBloomFilter doesn't have method to remove a elements, so if do not refresh, then with time increase
        // all bits in the bloom filter will be filled out, which means all retrieve will be returned true, the bloom
        // filter will lose its meanning.
        // so here every time scanning the flash sale DB, we refresh the bloom filter to maintain the precision.
        // Although this may cause a problem that during this task is refreshing the bloom filter,
        // there might be a short period that bloom filter lacks some elements, so some impossible requests may not be
        // filtered by the gateway, the number of such requests should not too much.
        // More importantly by now I don't see other solutions.
        rBloomFilter.tryInit(100000, 0.03);


        // get products
        List<Map<String, Object>> products = service.getToBeStarted();

        if (products.size() != 0){

            HashOperations ho = redisTemplate.opsForHash();

            // iterate products
            for (Map<String, Object> product : products) {
                String saleId = product.get("id") + "";
                int stock = Integer.parseInt(product.get("stock") + "");
                int price = Integer.parseInt(product.get("salePrice") + "");
                int productId = Integer.parseInt(product.get("productId") + "");
                // SETNX in redis, for checking exist purpose
                ho.putIfAbsent(saleId, "stock", stock);
                ho.putIfAbsent(saleId, "status", 0);
                ho.putIfAbsent(saleId, "productId", productId);
                ho.putIfAbsent(saleId, "price", price);

                // adding value to bloom filter for gateway filter
                rBloomFilter.add(Integer.parseInt(saleId));

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
        //System.out.println("changing for being sale");
        service.changeStateForBeingSale();

        List<Map<String, Object>> productsBeingSale = service.getBeingSale();

        if (productsBeingSale.size() != 0){

            HashOperations ho = redisTemplate.opsForHash();

            for (int i = 0; i <  productsBeingSale.size(); i++){
                String saleId = productsBeingSale.get(i).get("id") + "";
                int stock = (int) productsBeingSale.get(i).get("stock");
                int price = (int) productsBeingSale.get(i).get("salePrice");
                int productId = Integer.parseInt(productsBeingSale.get(i).get("productId")+"");
                ho.put(saleId, "status", 1);
                ho.putIfAbsent(saleId, "stock", stock);
                ho.putIfAbsent(saleId, "productId", productId);
                ho.putIfAbsent(saleId, "price", price);
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
        //System.out.println("changing for after sale");
        service.changeStateForAfterSale();

        List<Map<String, Object>> productsAfterSale = service.getAfterSale();

        if (productsAfterSale.size() != 0){
            HashOperations ho = redisTemplate.opsForHash();

            for (Map<String, Object> stringObjectMap : productsAfterSale) {
                String saleId = stringObjectMap.get("id") + "";
                int stock = (int) stringObjectMap.get("stock");
                int price = Integer.parseInt(stringObjectMap.get("salePrice") + "");
                int productId = Integer.parseInt(stringObjectMap.get("productId") + "");
                ho.put(saleId, "status", 2);
                ho.putIfAbsent(saleId, "stock", stock);
                ho.putIfAbsent(saleId, "productId", productId);
                ho.putIfAbsent(saleId, "price", price);
            }
        }
    }

    /**
     *  Check status and stock in redis
     */
    @Scheduled(cron = "0/10 * * * * *")
    public void checkStatus(){
        HashOperations ho = redisTemplate.opsForHash();

        Map<String, Object> key1 = ho.entries("1");
        Map<String, Object> key2 = ho.entries("2");
        Map<String, Object> key3 = ho.entries("3");


    }

}
