package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetmealVO implements Serializable {

    private Long id;
    /**
     * 分类id
     */
    private Integer categoryId;
    /**
     * 套餐名称
     */
    private String name;
    /**
     * 套餐价格
     */
    private BigDecimal price;
    /**
     * 状态 0:停用 1:启用
     */
    private Integer status;
    /**
     * 套餐描述
     */
    private String description;
    /**
     * 图片
     */
    private String image;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 分类名称
     */
    private String categoryName;
}
