package com.wh.controller.admin;

import com.wh.constant.JwtClaimsConstant;
import com.wh.dto.EmployeeDTO;
import com.wh.dto.EmployeeLoginDTO;
import com.wh.dto.EmployeePageQueryDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.properties.JwtProperties;
import com.wh.result.PageResult;
import com.wh.result.Result;
import com.wh.service.EmployeeService;
import com.wh.utils.JwtUtil;
import com.wh.vo.EmployeeLoginVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 员工退出登录
     * @return 提示消息
     */
    @PostMapping("/logout")
    public Result<String> empLogout() {
        return Result.success();
    }

    /**
     * 添加员工
     * @param employeeDTO 添加员工需要的dto类
     * @return 提示信息
     */
    @PostMapping
    public Result<Void> addEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工: {}",employeeDTO);
        employeeService.addEmp(employeeDTO);
        return Result.success();
    }

    /**
     * 员工分页查询
     * @param employeePageQueryDTO 员工分页查询需要的dto
     * @return 员工分页查询结果
     */
    @GetMapping("/page")
    public Result<PageResult<EmployeeEntity>> queryEmpByPage(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询，参数为:{}",employeePageQueryDTO);
        PageResult<EmployeeEntity> pageResult = employeeService.queryEmpByPage(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     * @param status 员工账号状态，1正常，0禁用
     * @param id 员工id
     * @return 状态信息
     */
    @PostMapping("/status/{status}")
    public Result<Void> updateEmpAccount(@PathVariable Integer status, Long id) {
        log.info("启用禁用员工账号: {}", status);
        employeeService.startOrStopEmpAccount(status, id);
        return Result.success();
    }
}
