package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工分页查询时需要的数据
 */
@Data
public class EmployeePageQueryDTO implements Serializable {

    // 员工姓名
    private String name;
    // 需要查询的页面
    private int page;
    // 每页显示的条数
    private int pageSize;
}
