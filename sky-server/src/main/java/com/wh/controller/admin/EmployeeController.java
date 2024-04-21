package com.wh.controller.admin;

import com.wh.dto.EmployeeLoginDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.result.Result;
import com.wh.service.EmployeeService;
import com.wh.vo.EmployeeLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工管理控制类
 */
@Slf4j
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 员工登录后返回的数据格式
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> empLogin(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录:{}", employeeLoginDTO);
        EmployeeEntity employee = employeeService.empLogin(employeeLoginDTO);
        return Result.success();
    }
}
