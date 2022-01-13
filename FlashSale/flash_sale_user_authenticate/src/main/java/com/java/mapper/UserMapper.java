package com.java.mapper;

import com.java.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select passwd from web_user where account = #{param1}")
    public List<User> getPasswdByAccount(String account);
}
