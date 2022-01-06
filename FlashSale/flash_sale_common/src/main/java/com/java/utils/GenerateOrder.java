package com.java.utils;

public class GenerateOrder {

    private static final String idPrefix = "00";
    /**
     * return orderNo according to userId, saleId and current system million seconds
     *
     * Note that the length of userId and saleId is assumed as 8 bytes;
     * By now the userId and saleId is designed as 1 byte only for testing purpose;
     * Also in realistic environmnet, using userId and saleId as order number may not safe.
     * @param userId
     * @return
     */
    public static String generateOrder(String userId){
        return System.currentTimeMillis() + idPrefix + userId;
    }
}
