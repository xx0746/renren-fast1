package io.renren.modules.ut.VO;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PerformanceVO  {
    @ExcelProperty(value = "月份", index = 0)
    private String yearMonth;
    @ExcelProperty(value = "部门", index = 1)
    private String departmentName;
    @ExcelProperty(value = "姓名", index = 2)
    private String username;
    @ExcelProperty(value = "工时", index = 3)
    private Double ut;
    @ExcelProperty(value = "系数", index = 4)
    private Double ratio;
    @ExcelProperty(value = "分数", index = 5)
    private Double score;
    @ExcelProperty(value = "绩效", index = 6)
    private Double performance;
    @ExcelProperty(value = "平均能力绩效", index = 7)
    private Double agvNengSalary;
    @ExcelProperty(value = "平均工时绩效", index = 8)
    private Double agvUtSalary;
    @ExcelProperty(value = "绩效标准", index = 9)
    private Double salaryStandard;
}
