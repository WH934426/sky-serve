package com.wh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 销量排名top10的商品名和销量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsSalesDTO implements Serializable {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 销售数量
     */
    private Integer number;
}
