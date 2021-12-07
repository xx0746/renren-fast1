package io.renren.modules.ut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ut_write")
public class UTWrite {
    private Long id;
    private String year;
    private String month;
    private String week;
    private Long projectId;
    private Long oneId;
    private Long twoId;
    private Long userId;
    private Long departmentId;
    private Double ut;
    private Integer status;
    private Long yearmonth;
    private Long yearMonthWeek;
    private Date createTime;
    private Date modifiedTime;
}
