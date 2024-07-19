package com.wh.controller.admin;

import com.wh.service.ReportService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计报表控制器
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;
}
