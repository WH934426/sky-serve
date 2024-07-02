package com.wh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户下单需要提交的数据
 */
@Data
public class OrderSubmitDTO {
    /**
     * 地址id
     */
    private Integer addressBookId;
    /**
     * \
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 配送状态： 1 立即派送 2 预约配送
     */
    private int deliveryStatus;
    /**
     * 预计送达时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    /**
     * 打包方式
     */
    private int packAmount;
    /**
     * 支付方式
     */
    private int payMethod;
    /**
     * 备注
     */
    private String remark;
    /**
     * 餐具数量
     */
    private Integer tablewareNumber;
    /**
     * 餐具数量状态：1按餐具提供，2 选择具体数量
     */
    private Integer tablewareStatus;
}
