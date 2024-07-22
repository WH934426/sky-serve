package com.wh.service.impl;

import com.wh.dto.GoodsSalesDTO;
import com.wh.entity.OrdersEntity;
import com.wh.mapper.OrderMapper;
import com.wh.mapper.UserMapper;
import com.wh.service.ReportService;
import com.wh.service.WorkspaceService;
import com.wh.vo.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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
    private final WorkspaceService workspaceService;

    public ReportServiceImpl(OrderMapper orderMapper, UserMapper userMapper, WorkspaceService workspaceService) {
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.workspaceService = workspaceService;
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
        List<LocalDate> dateList = generateDateList(begin, end);

        // 初始化营业额列表，用于存储每个日期的营业额
        List<Double> turnoverList = new ArrayList<>();

        // 遍历日期列表，计算每个日期的营业额
        for (LocalDate date : dateList) {
            // 构造每日的开始时间和结束时间，用于查询订单
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 构建查询参数映射，指定查询的订单状态为已完成
            Map<String, Object> map = prepareQueryMap(beginTime, endTime, OrdersEntity.COMPLETED);

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
        List<LocalDate> dateList = generateDateList(begin, end);

        // 初始化新用户数列表和总用户数列表
        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        // 遍历日期列表，统计每天的新用户数和总用户数
        for (LocalDate date : dateList) {
            // 构造每日的开始时间和结束时间，用于查询订单
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 查询当天的新用户数
            Map<String, Object> newUserMap = prepareQueryMap(null, endTime, null);
            Integer newUser = userMapper.sumUserByMap(newUserMap);

            // 查询当天的总用户数
            Map<String, Object> totalUserMap = prepareQueryMap(beginTime, endTime, null);
            Integer totalUser = userMapper.sumUserByMap(totalUserMap);

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

    /**
     * 根据时间区间统计订单数据
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 订单数据
     */
    @Override
    public OrderReportVO getOrderByTime(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = generateDateList(begin, end);

        // 初始化两个列表，分别用于存储每天的订单数量和有效订单数量
        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> validOrderCountList = new ArrayList<>();

        for (LocalDate date : dateList) {
            // 构造每日的开始时间和结束时间，用于查询订单
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // 用于存储查询条件的Map
            Map<String, Object> map = prepareQueryMap(beginTime, endTime, null);

            // 统计当天所有订单的数量
            Integer orderCont = orderMapper.sumOrderByMap(map);
            // 更新查询条件，统计当天已完成的订单数量
            map.put("status", OrdersEntity.COMPLETED);
            Integer validOrderCount = orderMapper.sumOrderByMap(map);

            // 将每天的订单数量和有效订单数量添加到对应的列表中
            orderCountList.add(orderCont);
            validOrderCountList.add(validOrderCount);
        }

        // 计算总订单数量和有效订单数量
        Integer totalOrderCount = orderCountList.stream().reduce(0, Integer::sum);
        Integer validOrderCount = validOrderCountList.stream().reduce(0, Integer::sum);

        // 计算订单完成率，如果总订单数量为0，则订单完成率为0
        double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }


        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 查询指定时间区间内的销量排名top10
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 销量排名top10的商品
     */
    @Override
    public SalesTop10ReportVO getSalesTop10ByTime(LocalDate begin, LocalDate end) {
        // 构造每日的开始时间和结束时间，用于查询订单
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        // 查询商品
        List<GoodsSalesDTO> top10 = orderMapper.getSalesTop10ByTime(beginTime, endTime);
        // 将商品名称列表转换为字符串，以逗号分隔
        List<String> names = top10.stream().map(GoodsSalesDTO::getName).toList();
        String nameList = StringUtils.join(names, ",");
        // 将商品销售数量列表转换为字符串，以逗号分隔
        List<Integer> numbers = top10.stream().map(GoodsSalesDTO::getNumber).toList();
        String numberList = StringUtils.join(numbers, ",");


        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 导出最近30天的运营数据报告。
     * 使用Excel模板，填充数据后生成并返回给客户端。
     *
     * @param response HTTP响应对象，用于向客户端发送生成的Excel文件。
     */
    @Override
    public void exportBusinessData(HttpServletResponse response) {
        // 定义报告起止时间，为过去30天。
        LocalDate beginDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().minusDays(1);

        // 获取最近30天的总体业务数据。
        BusinessDataVO businessDataVO = workspaceService.getBusinessData(LocalDateTime.of(beginDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX));

        // 加载Excel模板文件。
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");

        try {
            // 基于模板初始化Excel工作簿
            XSSFWorkbook excel = new XSSFWorkbook(in);
            // 获取工作表。
            XSSFSheet sheet = excel.getSheet("Sheet1");
            // 填充报告时间范围到指定单元格。单元格从0开始
            sheet.getRow(1).getCell(1).setCellValue("时间：" + beginDate + "至" + endDate);
            // 填充汇总数据到工作表。
            fillSummaryData(sheet, businessDataVO);
            // 填充每日数据到工作表。
            fillDailyData(sheet, businessDataVO, beginDate);

            // 将处理后的Excel写入HTTP响应，供客户端下载。
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            // 关闭资源。
            out.close();
            excel.close();
        } catch (IOException e) {
            log.error("导出运营数据报表失败");
        }
    }


    /**
     * 生成指定开始和结束日期之间的日期列表，包括开始和结束日期。
     *
     * @param begin 开始日期，包含在列表中。
     * @param end   结束日期，包含在列表中。
     * @return 一个LocalDate类型的列表，包含了从开始日期到结束日期之间的所有日期。
     */
    private List<LocalDate> generateDateList(LocalDate begin, LocalDate end) {
        // 初始化一个空的日期列表
        List<LocalDate> dateList = new ArrayList<>();
        // 从开始日期开始循环，直到日期大于结束日期
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            // 将当前日期添加到列表中
            dateList.add(date);
        }
        // 返回包含所有日期的列表
        return dateList;
    }


    /**
     * 构建查询条件
     *
     * @param begin  开始时间
     * @param end    结束时间
     * @param status 订单状态
     * @return 查询条件map集合
     */
    private Map<String, Object> prepareQueryMap(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        return map;
    }


    /**
     * 填充汇总数据到Excel表格中。
     * 此方法负责将业务数据对象中的数据写入到Excel工作表的特定单元格中。
     *
     * @param sheet          Excel工作表对象，数据将被写入此工作表。
     * @param businessDataVO 业务数据视图对象，包含需要写入Excel的汇总数据。
     */
    private void fillSummaryData(XSSFSheet sheet, BusinessDataVO businessDataVO) {
        // 获取第四行（索引从0开始），用于填写营业额、完成率和新用户数
        XSSFRow row = sheet.getRow(3);
        row.getCell(2).setCellValue(businessDataVO.getTurnover());
        row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
        row.getCell(6).setCellValue(businessDataVO.getNewUsers());

        // 获取第五行，用于填写有效订单数量和平均单价
        row = sheet.getRow(4);
        row.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
        row.getCell(4).setCellValue(businessDataVO.getUnitPrice());
    }


    /**
     * 填充每日业务数据到Excel表格中。
     *
     * @param sheet          Excel表格中的工作表对象，用于数据写入。
     * @param businessDataVO 包含业务数据的VO对象，用于读取数据。
     * @param beginDate      填充数据的起始日期。
     * @throws IOException 如果写入Excel时发生IO错误。
     */
    private void fillDailyData(XSSFSheet sheet, BusinessDataVO businessDataVO, LocalDate beginDate) throws IOException {
        // 循环30天，填充每天的业务数据
        for (int i = 0; i < 30; i++) {
            // 计算当前循环的日期
            LocalDate date = beginDate.plusDays(i);
            // 根据日期获取当天的业务数据
            businessDataVO = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

            // 获取Excel表格中对应日期的行
            XSSFRow row = sheet.getRow(7 + i);
            // 填充日期、营业额、有效订单数、完成率、平均单价、新用户数
            row.getCell(1).setCellValue(date.toString());
            row.getCell(2).setCellValue(businessDataVO.getTurnover());
            row.getCell(3).setCellValue(businessDataVO.getValidOrderCount());
            row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
            row.getCell(5).setCellValue(businessDataVO.getUnitPrice());
            row.getCell(6).setCellValue(businessDataVO.getNewUsers());
        }
    }
}
