package com.wh.service;

import com.wh.vo.TurnoverReportVO;

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
}
