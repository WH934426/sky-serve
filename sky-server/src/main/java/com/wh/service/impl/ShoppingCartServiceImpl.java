package com.wh.service.impl;

import com.wh.mapper.ShoppingCartMapper;
import com.wh.service.ShoppingCartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 购物车Service层实现类
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
}
