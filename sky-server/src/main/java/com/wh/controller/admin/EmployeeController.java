package com.wh.controller.admin;

import com.wh.constant.JwtClaimsConstant;
import com.wh.dto.EmployeeLoginDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.properties.JwtProperties;
import com.wh.result.Result;
import com.wh.service.EmployeeService;
import com.wh.utils.JwtUtil;
import com.wh.vo.EmployeeLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理控制类
 */
@Slf4j
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
    @Resource
    private JwtProperties jwtProperties;

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

        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }
}
