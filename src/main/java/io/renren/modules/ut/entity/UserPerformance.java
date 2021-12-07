package io.renren.modules.ut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_performance")
public class UserPerformance {
    private Long id;
    private Long userId;
    private Long departmentId;
    private String year;
    private String month;
    private Long yearmonth;
    private Double ut;
    private Double agvNengSalary;
    private Double agvUtSalary;
    private Double performance;
    private Date createTime;
    private Date modifiedTime;


}
