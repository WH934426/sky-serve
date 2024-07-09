package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单确认DTO
 */
@Data
public class OrdersConfirmDTO implements Serializable {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 订单状态
     * <p>
     * 1. 待付款
     * 2. 待接单
     * 3. 已接单
     * 4. 派送中
     * 5. 已完成
     * 6. 已取消
     * 7. 退款
     */
    private Integer status;
}
