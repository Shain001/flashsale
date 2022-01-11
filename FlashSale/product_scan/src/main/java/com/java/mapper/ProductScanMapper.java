package com.java.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Scheduled Scanner Mapper
 *
 * @author Shain
 * @lastUpdate 2022-1-3
 */
public interface ProductScanMapper {

    /**
     * get all products that are about to start flash sale
     *
     * @return list of product ids and flash sale ids (primary key in db)
     */
    @Select("select id, productId, stock, salePrice from web_seckill where NOW() < startTime")
    List<Map<String, Object>> getToBeStarted();

    /**
     * change status from 0 to 1 for products that are being sale
     *
     * @param
     * @return
     */
    @Update("update `web_seckill` set `status`=1 where startTime <= NOW() and endTime > NOW()")
    int changeStateForBeingSale();

    /**
     * change status from 1 to 2 for products afterSale
     *
     * @param
     * @return
     */
    @Update("update `web_seckill` set `status`=2 where endTime <= NOW()")
    int changeStateForAfterSale();

    /**
     * get products that have started sale to update state in redis
     * @return
     */
    @Select("select id, productId, stock, salePrice from web_seckill where startTime<=NOW() and endTime > NOW()")
    List<Map<String, Object>> getBeingSale();

    /**
     * get products that have end sale to update state in redis
     *
     */
    @Select("select id, productId, stock, salePrice from web_seckill where endTime<=NOW()")
    List<Map<String, Object>> getAfterSale();

    @Select("select id from web_seckill")
    List<Map<String, Object>> getAllSaleId();

}
