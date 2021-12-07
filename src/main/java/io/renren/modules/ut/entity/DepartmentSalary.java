package io.renren.modules.ut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("department_salary")
@Data
public class DepartmentSalary {

    private Long id;
    private Long departmentId;
    private Double salary;
    private String year;
    private String month;
    private int yearmonth;
    private Date createTime;
    private Date modifiedTime;


}
