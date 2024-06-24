package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 根据套餐id查询包含的菜品数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishItemVO implements Serializable {
    /**
     * 菜品数量 (份数)
     */
    private Integer copies;
    /**
     * 菜品描述
     */
    private String description;
    /**
     * 菜品图片路径
     */
    private String image;
    /**
     * 菜品名称
     */
    private String name;
}
