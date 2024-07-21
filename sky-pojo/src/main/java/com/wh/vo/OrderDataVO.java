package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单概览数据vo对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDataVO {
    /**
     * 订单总数
     */
    private Integer allOrders;
    /**
     * 已取消的订单数量
     */
    private Integer cancelledOrders;
    /**
     * 已完成的订单数量
     */
    private Integer completedOrders;
    /**
     * 待派送的订单数量
     */
    private Integer deliveredOrders;
    /**
     * 待接单的订单数量
     */
    private Integer waitingOrders;
}
