package com.java.service;

import com.java.entity.User;
import com.java.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceIml implements UserService {
    @Autowired
    UserMapper mapper;

    @Override
    public List<User> getPasswdByAccount(String account){
        return mapper.getPasswdByAccount(account);
    }
}
