package com.wh.service;

import com.wh.dto.ShoppingCartDTO;
import com.wh.entity.ShoppingCartEntity;

import java.util.List;

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

    /**
     * 查看购物车列表
     *
     * @return 购物车列表数据
     */
    List<ShoppingCartEntity> showShoppingCart();
}
