package com.wh.service;

import com.wh.vo.OrderReportVO;
import com.wh.vo.SalesTop10ReportVO;
import com.wh.vo.TurnoverReportVO;
import com.wh.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;

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

    /**
     * 查询指定时间区间内的销量排名top10
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 销量排名top10的商品
     */
    SalesTop10ReportVO getSalesTop10ByTime(LocalDate begin, LocalDate end);

    /**
     * 导出最近30天的运营数据报告。
     * 使用Excel模板，填充数据后生成并返回给客户端。
     *
     * @param response HTTP响应对象，用于向客户端发送生成的Excel文件。
     */
    void exportBusinessData(HttpServletResponse response);
}
