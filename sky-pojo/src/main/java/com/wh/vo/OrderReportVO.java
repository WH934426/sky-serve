package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单统计vo对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO {
    /**
     * 日期列表，以逗号分隔
     */
    private String dateList;
    /**
     * 订单完成率
     */
    private Double orderCompletionRate;
    /**
     * 订单数列表，以逗号分隔
     */
    private String orderCountList;
    /**
     * 订单总数
     */
    private Integer totalOrderCount;
    /**
     * 有效订单数
     */
    private Integer validOrderCount;
    /**
     * 有效订单数列表，以逗号分隔
     */
    private String validOrderCountList;
}
