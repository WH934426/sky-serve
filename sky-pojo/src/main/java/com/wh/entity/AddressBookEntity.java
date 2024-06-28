package com.wh.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 地址簿实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressBookEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 性别
     */
    private String sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 省级区划编号
     */
    private String provinceCode;
    /**
     * 省级名称
     */
    private String provinceName;
    /**
     * 市级区划编号
     */
    private String cityCode;
    /**
     * 市级名称
     */
    private String cityName;
    /**
     * 区级区划编号
     */
    private String districtCode;
    /**
     * 区级名称
     */
    private String districtName;
    /**
     * 详细地址
     */
    private String detail;
    /**
     * 标签
     */
    private String label;
    /**
     * 是否默认 1是 0否
     */
    private Integer isDefault;
}
