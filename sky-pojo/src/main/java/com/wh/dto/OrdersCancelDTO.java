package com.wh.dto;

import lombok.Data;

/**
 * 取消订单参数
 */
@Data
public class OrdersCancelDTO {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 取消原因
     */
    private String cancelReason;
}
