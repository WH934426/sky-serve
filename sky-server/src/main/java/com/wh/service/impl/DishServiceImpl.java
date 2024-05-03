package com.wh.service.impl;

import com.wh.mapper.DishMapper;
import com.wh.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    // TODO 存在其他Mapper层，采用构造器注入
    private final DishMapper dishMapper;

    public DishServiceImpl(DishMapper dishMapper) {
        this.dishMapper = dishMapper;
    }
}
