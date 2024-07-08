package com.wh.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 各个状态的订单数量统计
 */
@Data
@Builder
public class OrderStatisticsVO implements Serializable {
    /**
     * 待派送数量
     */
    private Integer confirmed;
    /**
     * 派送中数量
     */
    private Integer deliveryInProgress;
    /**
     * 待接单数量
     */
    private Integer toBeConfirmed;
}
