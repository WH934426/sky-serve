package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 套餐概览数据vo对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetmealDataVO {
    /**
     * 已停售套餐数量
     */
    private Integer discontinued;
    /**
     * 已起售套餐数量
     */
    private Integer sold;
}
