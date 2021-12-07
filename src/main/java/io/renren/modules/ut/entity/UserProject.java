package io.renren.modules.ut.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("user_project")
@Data
public class UserProject {
    private Long id;
    private Long userId;
    private Long projectId;
    private Double ut;
    private Date createTime;
    private Date modifiedTime;
    public UserProject(){}

    public UserProject(Long projectId, Long userId) {
        this.projectId = projectId;
        this.userId = userId;
        this.createTime = new Date();
        this.modifiedTime = new Date();
    }

    public UserProject(Long projectId, Long userId, Double ut) {
        this(projectId, userId);
        this.ut = ut;
    }

}
