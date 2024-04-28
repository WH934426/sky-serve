package com.wh.mapper;

import com.github.pagehelper.Page;
import com.wh.dto.EmployeePageQueryDTO;
import com.wh.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 员工管理接口
 */
@Mapper
public interface EmployeeMapper {
    /**
     * 根据用户名查询员工
     * @param username 用户名
     * @return 员工信息
     */
    @Select("select * from employee where username = #{username}")
    EmployeeEntity getByUsername(String username);

    /**
     * 添加员工
     * @param employee 员工实体
     */
    void addEmp(EmployeeEntity employee);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO 分页查询员工信息需要的dto
     * @return 分页后的员工信息
     */
    Page<EmployeeEntity> queryEmpByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param employee 员工实体
     */
    void updateEmp(EmployeeEntity employee);

    /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 员工信息
     */
    @Select("select * from employee where id = #{id}")
    EmployeeEntity getEmpById(Long id);
}
