package com.wh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片路径
     */
    private String image;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 菜品id
     */
    private Long dishId;
    /**
     * 套餐id
     */
    private Long setmealId;
    /**
     * 菜品口味
     */
    private String dishFlavor;
    /**
     * 商品数量
     */
    private Integer number;
    /**
     * 商品单价
     */
    private BigDecimal amount;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
