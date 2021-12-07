package io.renren.modules.ut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("user_score")
@Data
public class UserScore {

    private Long id;
    private Long departmentId;
    private Long userId;
    private Double score;
    private String year;
    private String month;
    private Date createTime;
    private Date modifiedTime;
}
