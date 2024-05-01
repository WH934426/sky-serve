package com.wh.dto;

import lombok.Data;

/**
 * 菜品分类分页查询需要的dto
 */
@Data
public class CategoryPageQueryDTO {
    // 菜品分类名称
    private String name;
    // 页码
    private int page;
    // 每页记录数
    private Integer pageSize;
    // 分类类型
    private Integer type;
}
