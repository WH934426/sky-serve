package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜品概览数据vo对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishDataVO {
    /**
     * 已停售菜品数量
     */
    private Integer discontinued;
    /**
     * 已起售菜品数量
     */
    private Integer sold;
}
