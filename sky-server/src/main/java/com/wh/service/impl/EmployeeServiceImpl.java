package com.wh.service.impl;

import com.wh.mapper.EmployeeMapper;
import com.wh.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 员工管理实现类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
}
