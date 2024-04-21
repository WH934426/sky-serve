package com.wh.controller.admin;

import com.wh.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工管理控制类
 */
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
}
