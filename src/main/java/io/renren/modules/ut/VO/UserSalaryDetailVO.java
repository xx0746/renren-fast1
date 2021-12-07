package io.renren.modules.ut.VO;

import lombok.Data;

@Data
public class UserSalaryDetailVO {
    private Long userId;
    private Long departmentId;
    private String level;
    private Double ratio;
    private Double salaryStandard;
    private Double score;
    private Double ut;
}
