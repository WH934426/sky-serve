package com.wh.service;

import com.wh.dto.EmployeeDTO;
import com.wh.dto.EmployeeEditPasswordDTO;
import com.wh.dto.EmployeeLoginDTO;
import com.wh.dto.EmployeePageQueryDTO;
import com.wh.entity.EmployeeEntity;
import com.wh.result.PageResult;
import com.wh.vo.EmployeeLoginVO;

/**
 * 员工管理接口
 */
public interface EmployeeService {
    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 员工实体信息
     */
    EmployeeLoginVO empLogin(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     *
     * @param employeeDTO 添加员工需要的dto类
     */
    void addEmp(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO 员工分页查询需要的dto
     * @return 分页结果类封装后的员工信息
     */
    PageResult<EmployeeEntity> queryEmpByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用或禁用员工账号
     *
     * @param status 员工账号状态
     * @param id     员工id
     */
    void startOrStopEmpAccount(Integer status, Long id);

    /**
     * 根据员工id查询员工
     *
     * @param id 员工id
     * @return 员工信息
     */
    EmployeeEntity getEmpById(Long id);

    /**
     * 编辑员工信息
     *
     * @param employeeDTO 员工dto类
     */
    void updateEmp(EmployeeDTO employeeDTO);

    /**
     * 修改密码
     *
     * @param employeeEditPasswordDTO 修改密码dto
     */
    void editEmpPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO);
}
