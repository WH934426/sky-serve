package com.wh.dto;

import com.wh.entity.DishFlavorEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增菜品需要的dto类
 */
@Data
public class DishDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 菜品名称
     */
    private String name;
    /**
     * 菜品分类id
     */
    private Long categoryId;
    /**
     * 菜品价格
     */
    private BigDecimal price;
    /**
     * 菜品图片
     */
    private String image;
    /**
     * 菜品描述信息
     */
    private String description;
    /**
     * 菜品状态 0，停售 1起售
     */
    private Integer status;
    /**
     * 菜品口味列表
     */
    private List<DishFlavorEntity> flavors = new ArrayList<>();
}
