package com.wh.service.impl;


import com.wh.entity.OrdersEntity;
import com.wh.mapper.OrderMapper;
import com.wh.service.ReportService;
import com.wh.vo.TurnoverReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计报表服务实现类
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final OrderMapper orderMapper;

    public ReportServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }


    /**
     * 根据指定的时间范围统计每日营业额。
     * 该方法通过查询每个日期内的已完成订单的总金额来计算每日营业额。
     *
     * @param begin 起始日期，包含此日期的营业额。
     * @param end   结束日期，包含此日期的营业额。
     * @return TurnoverReportVO 对象，包含每日日期列表和对应的营业额列表。
     */
    @Override
    public TurnoverReportVO getTurnoverByTime(LocalDate begin, LocalDate end) {
        // 初始化日期列表，用于存储查询范围内的所有日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        // 通过循环将起始日期到结束日期之间的所有日期添加到日期列表中
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 初始化营业额列表，用于存储每个日期的营业额
        List<Double> turnoverList = new ArrayList<>();

        // 遍历日期列表，计算每个日期的营业额
        for (LocalDate date : dateList) {
            // 构造每日的开始时间和结束时间，用于查询订单
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 构建查询参数映射，指定查询的订单状态为已完成
            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", OrdersEntity.COMPLETED);

            // 查询指定日期内的订单总金额
            Double turnover = orderMapper.sumTurnoverByMap(map);
            // 如果查询结果为null，则默认为0.0
            turnover = turnover == null ? 0.0 : turnover;

            // 将每日营业额添加到营业额列表中
            turnoverList.add(turnover);
        }

        // 构建并返回营业额报告对象，包含日期列表和营业额列表
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

}
