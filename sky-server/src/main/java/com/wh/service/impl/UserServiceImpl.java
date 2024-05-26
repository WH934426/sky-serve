package com.wh.service.impl;


import com.wh.mapper.UserMapper;
import com.wh.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
}
