package com.wh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜品口味实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishFlavorEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 菜品id
     */
    private Long dishId;
    /**
     * 口味名称
     */
    private String name;
    /**
     * 口味数据List
     */
    private String value;
}
