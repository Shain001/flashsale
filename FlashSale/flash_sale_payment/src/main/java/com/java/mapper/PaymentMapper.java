package com.java.mapper;

import org.apache.ibatis.annotations.Update;

public interface PaymentMapper {
    /**
     * Change order State in DB
     * @param status
     * @return
     */
    @Update("UPDATE `web_order` set `status`= #{param3} where saleId = #{param1} and userId = #{userId}")
    int changOderStatus(String saleId, String userId, int status);
}
