package com.wh.dto;

import lombok.Data;

/**
 * 订单支付传递的数据
 */
@Data
public class OrdersPaymentDTO {
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 付款方式
     */
    private Integer payMethod;
}
