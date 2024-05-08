package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜品分页查询
 */
@Data
public class DishPageQueryDTO implements Serializable {

    private int page;
    private int pageSize;
    /**
     * 菜品名称
     */
    private String name;
    /**
     * 菜品分类id
     */
    private Integer categoryId;
    /**
     * 菜品状态 0:停售 1:起售
     */
    private Integer status;
}
