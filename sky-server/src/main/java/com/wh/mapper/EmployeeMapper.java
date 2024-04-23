package com.wh.mapper;

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
}
