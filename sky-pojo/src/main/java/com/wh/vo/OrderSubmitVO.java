package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户下单成功后返回的vo数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSubmitVO implements Serializable {
    /**
     * 订单id
     */
    private Long id;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 下单时间
     */
    private LocalDateTime orderTime;
}
