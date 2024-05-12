package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 套餐分页查询需要的数据
 */
@Data
public class SetmealPageQueryDTO implements Serializable {

    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 套餐名称
     */
    private String name;
    /**
     * 页码
     */
    private Integer page;
    /**
     * 每页显示条数
     */
    private Integer pageSize;
    /**
     * 套餐起售状态
     */
    private Integer status;
}
