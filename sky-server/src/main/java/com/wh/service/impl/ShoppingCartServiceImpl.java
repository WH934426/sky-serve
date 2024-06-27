package com.wh.service.impl;

import com.wh.context.BaseContext;
import com.wh.dto.ShoppingCartDTO;
import com.wh.entity.DishEntity;
import com.wh.entity.SetmealEntity;
import com.wh.entity.ShoppingCartEntity;
import com.wh.mapper.DishMapper;
import com.wh.mapper.SetmealMapper;
import com.wh.mapper.ShoppingCartMapper;
import com.wh.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车Service层实现类
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final DishMapper dishMapper;
    private final SetmealMapper setmealMapper;

    public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper, DishMapper dishMapper, SetmealMapper setmealMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
    }

    /**
     * 添加购物车数据
     *
     * @param shoppingCartDTO 购物车数据dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId()); // 设置当前用户id

        // 判断当前商品是否在购物车当中
        List<ShoppingCartEntity> shoppingCartList = shoppingCartMapper.queryShoppingCarts(shoppingCart);
        // 如果存在，则数量+1
        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
            ShoppingCartEntity cart = shoppingCartList.get(0);
            cart.setNumber(cart.getNumber() + 1);
            // 更新数量
            shoppingCartMapper.updateNumberById(cart);
        } else {
            // 如果不存在，则数量为1，需要判断是菜品还是套餐，
            // 当前操作添加到购物车的是菜品
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                DishEntity dish = dishMapper.getDishById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                // 当前操作添加到购物车的是套餐
                Long setmealId = shoppingCart.getSetmealId();
                SetmealEntity setmeal = setmealMapper.getSetmealById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
        }
        // 添加到数据库
        shoppingCartMapper.addShoppingCart(shoppingCart);
    }
}
