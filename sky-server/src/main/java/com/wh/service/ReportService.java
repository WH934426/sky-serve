package com.wh.service;

import com.wh.vo.OrderReportVO;
import com.wh.vo.TurnoverReportVO;
import com.wh.vo.UserReportVO;

import java.time.LocalDate;

/**
 * 统计报表接口
 */
public interface ReportService {

    /**
     * 根据时间区间统计营业额
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 营业额报表数据
     */
    TurnoverReportVO getTurnoverByTime(LocalDate begin, LocalDate end);

    /**
     * 根据时间区间统计用户数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 用户数据
     */
    UserReportVO getUserByTime(LocalDate begin, LocalDate end);

    /**
     * 根据时间区间统计订单数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 订单数据
     */
    OrderReportVO getOrderByTime(LocalDate begin, LocalDate end);
}
