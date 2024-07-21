package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 今日运营数据vo对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDataVO {
    /**
     * 新增用户数量
     */
    private Integer newUsers;
    /**
     * 订单完成率
     */
    private Double orderCompletionRate;
    /**
     * 营业额
     */
    private Double turnover;
    /**
     * 平均客单价
     */
    private Double unitPrice;
    /**
     * 有效订单数量
     */
    private Integer validOrderCount;
}
