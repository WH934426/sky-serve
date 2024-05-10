package com.wh.service.impl;

import com.wh.mapper.SetmealDishMapper;
import com.wh.mapper.SetmealMapper;
import com.wh.service.SetmealService;
import org.springframework.stereotype.Service;

/**
 * 套餐 服务实现类
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper, SetmealDishMapper setmealDishMapper) {
        this.setmealMapper = setmealMapper;
        this.setmealDishMapper = setmealDishMapper;
    }
}
