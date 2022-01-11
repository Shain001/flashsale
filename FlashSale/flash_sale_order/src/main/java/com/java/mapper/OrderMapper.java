package com.java.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    @Insert("INSERT INTO `web_order_` (`id`, `userId`, `saleId`, `status`, `createDate`) values (${param1},${param2},${param3},0,NOW())")
    int generateOrder(String orderNo, String userId, String saleId);

    /**
     * Not used in this module
     * @param orderNo
     * @param status
     * @return
     */
    @Update("UPDATE `web_order_` set `status`= #{param2}")
    int changOderStatus(String orderNo, int status);

    @Update("UPDATE `web_seckill` set `stock`=`stock`-1 where id=#{param1}")
    int deductStock(String saleId);

    @Select("SELECT * from `web_order_`")
    List<Map<String, Object>> getAllOrder();

}
