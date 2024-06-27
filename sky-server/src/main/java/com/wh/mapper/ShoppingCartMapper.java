package com.wh.mapper;

import com.wh.entity.ShoppingCartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 购物车Mapper接口
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据条件动态查询购物车数据
     *
     * @param shoppingCartEntity 购物车实体
     * @return 购物车列表数据
     */
    List<ShoppingCartEntity> queryShoppingCarts(ShoppingCartEntity shoppingCartEntity);

    /**
     * 根据id更新购物车数量
     *
     * @param shoppingCart 购物车数据
     */
    @Update("update shopping_cart set number = #{number} where id=#{id}")
    void updateNumberById(ShoppingCartEntity shoppingCart);

    /**
     * 添加购物车
     *
     * @param shoppingCart 购物车数据
     */
    void addShoppingCart(ShoppingCartEntity shoppingCart);
}
