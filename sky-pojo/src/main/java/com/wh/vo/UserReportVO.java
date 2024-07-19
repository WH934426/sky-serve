package com.wh.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户统计需要的vo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReportVO implements Serializable {
    /**
     * 日期列表，日期之间以逗号分隔
     */
    private String dateList;
    /**
     * 新增用户数列表，以逗号分隔
     */
    private String newUserList;
    /**
     * 总用户量列表，以逗号分隔
     */
    private String totalUserList;
}
