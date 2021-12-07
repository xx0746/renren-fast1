package io.renren.modules.ut.VO;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectUTVO {
    private Long id;
    private Double ut;
    private Double usedut;
    private Double unusedut;
    private Double monthut;
    private String code;
    private String name;
    private String excelMonth;
    private Date startTime;
    private Date endTime;
    private Long departmentId;
    private String departmentName;
    private Long userId;
    private String userName;
    private String according;
    private String level;

}
