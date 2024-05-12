package com.wh.dto;

import com.wh.entity.SetmealDishEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增套餐需要的数据
 */
@Data
public class SetmealDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 菜品分类id
     */
    private Integer categoryId;
    /**
     * 套餐名称
     */
    private String name;
    /**
     * 套餐价格
     */
    private String price;
    /**
     * 套餐状态 0:停用 1:启用
     */
    private Integer status;
    /**
     * 套餐描述
     */
    private String description;
    /**
     * 套餐图片
     */
    private String image;
    /**
     * 套餐包含的菜品及数量
     */
    private List<SetmealDishEntity> setmealDishes = new ArrayList<>();

}
