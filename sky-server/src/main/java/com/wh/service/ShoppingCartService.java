package com.wh.service;

import com.wh.dto.ShoppingCartDTO;

/**
 * 购物车service层接口
 */
public interface ShoppingCartService {

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO 购物车数据dto
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
