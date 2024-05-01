package com.wh.dto;

import lombok.Data;

/**
 * 添加菜品分类需要的数据
 */
@Data
public class CategoryDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 排序，按照升序排序
     */
    private Integer sort;
    /**
     * 分类类型：1为菜品分类，2为套餐分类
     */
    private Integer type;
}
