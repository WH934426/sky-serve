package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加购物车需要的参数
 */
@Data
public class ShoppingCartDTO implements Serializable {

    /**
     * 菜品口味
     */
    private String dishFlavor;
    /**
     * 菜品id
     */
    private Long dishId;
    /**
     * 套餐id
     */
    private Long setmealId;
}
