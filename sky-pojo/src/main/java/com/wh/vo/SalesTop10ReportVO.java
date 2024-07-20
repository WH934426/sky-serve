package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 销量排名统计vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesTop10ReportVO {
    /**
     * 商品名称列表，以逗号分隔
     */
    private String nameList;
    /**
     * 销售数量列表，以逗号分隔
     */
    private String numberList;
}
