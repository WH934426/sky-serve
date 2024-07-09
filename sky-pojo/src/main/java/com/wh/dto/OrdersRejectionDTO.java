package com.wh.dto;

import lombok.Data;

/**
 * 拒绝订单参数
 */
@Data
public class OrdersRejectionDTO {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 拒单原因
     */
    private String rejectionReason;
}
