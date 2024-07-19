package com.wh.controller.admin;

import com.wh.result.Result;
import com.wh.service.ReportService;
import com.wh.vo.TurnoverReportVO;
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
    public Result<TurnoverReportVO> statisticsTurnover(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("统计营业额的时间:{},{}", begin, end);
        TurnoverReportVO turnover = reportService.getTurnoverByTime(begin, end);
        return Result.success(turnover);
    }
}
