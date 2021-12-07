package io.renren.modules.ut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("department_ratio")
@Data
public class DepartmentRatio {

    private Long id;
    private Long departmentId;
    private Double ratio;
    private String year;
    private String month;
    private Date createTime;
    private Date modifiedTime;


}
