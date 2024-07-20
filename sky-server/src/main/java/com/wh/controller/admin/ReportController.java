package com.wh.controller.admin;

import com.wh.result.Result;
import com.wh.service.ReportService;
import com.wh.vo.OrderReportVO;
import com.wh.vo.SalesTop10ReportVO;
import com.wh.vo.TurnoverReportVO;
import com.wh.vo.UserReportVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 统计报表控制器
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;

    /**
     * 营业额统计
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 某个时间区间的营业额
     */
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> statistics4Turnover(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("统计营业额的时间:{},{}", begin, end);
        TurnoverReportVO turnover = reportService.getTurnoverByTime(begin, end);
        return Result.success(turnover);
    }

    /**
     * 用户统计
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 某个时间区间的用户数量
     */
    @GetMapping("/userStatistics")
    public Result<UserReportVO> statistics4User(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("统计用户量的时间:{},{}", begin, end);
        UserReportVO userReportVO = reportService.getUserByTime(begin, end);
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 某个时间区间的订单数量
     */
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> statistics4Order(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("统计订单量的时间:{},{}", begin, end);
        OrderReportVO orderReportVO = reportService.getOrderByTime(begin, end);
        return Result.success(orderReportVO);
    }

    /**
     * 查询销售排名top10
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 销量排名前10的商品
     */
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> getSalesTop10ByTime(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("查询销量排名前10的时间区间:{},{}", begin, end);
        SalesTop10ReportVO salesTop10ReportVO = reportService.getSalesTop10ByTime(begin, end);
        return Result.success(salesTop10ReportVO);
    }
}
