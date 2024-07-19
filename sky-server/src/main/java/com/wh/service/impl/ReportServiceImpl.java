package com.wh.service.impl;

import com.wh.entity.OrdersEntity;
import com.wh.mapper.OrderMapper;
import com.wh.mapper.UserMapper;
import com.wh.service.ReportService;
import com.wh.vo.TurnoverReportVO;
import com.wh.vo.UserReportVO;
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
    private final UserMapper userMapper;

    public ReportServiceImpl(OrderMapper orderMapper, UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
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

    /**
     * 根据指定的时间范围，统计每天的新用户数和总用户数。
     *
     * @param begin 统计的起始日期
     * @param end   统计的结束日期
     * @return 包含每天新用户数、总用户数和日期的UserReportVO对象
     */
    @Override
    public UserReportVO getUserByTime(LocalDate begin, LocalDate end) {
        // 初始化日期列表，用于存储从开始日期到结束日期的所有日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        // 通过循环将起始日期到结束日期之间的所有日期添加到日期列表中
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 初始化新用户数列表和总用户数列表
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        // 遍历日期列表，统计每天的新用户数和总用户数
        for (LocalDate date : dateList) {
            // 构造每日的开始时间和结束时间，用于查询订单
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 使用Map存储查询条件，先查询当天的新用户数
            Map<String, Object> map = new HashMap<>();
            map.put("end", endTime);
            Integer newUser = userMapper.sumUserByMap(map);

            // 更新Map中的查询条件，查询当天的总用户数
            map.put("begin", beginTime);
            Integer totalUser = userMapper.sumUserByMap(map);

            // 将新用户数和总用户数添加到对应的列表中
            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }

        // 使用StringUtils的join方法将日期列表、新用户数列表和总用户数列表转换为字符串，然后构建并返回UserReportVO对象
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }
}
