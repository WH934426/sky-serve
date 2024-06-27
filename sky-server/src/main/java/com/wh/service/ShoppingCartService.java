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

    /**
     * 清空购物车
     */
    void cleanShoppingCart();

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO 购物车数据dto
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
