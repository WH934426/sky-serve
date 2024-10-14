package com.wh.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码时需要的数据格式
 */
@Data
public class EmployeeEditPasswordDTO implements Serializable {

    /**
     * 员工id
     */
    private Long empId;
    /**
     * 新密码
     */
    private String newPassword;
    /**
     * 旧密码
     */
    private String oldPassword;
}
