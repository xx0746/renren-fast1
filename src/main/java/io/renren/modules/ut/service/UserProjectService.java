package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ut.entity.UserProject;

import java.util.List;
import java.util.Map;

public interface UserProjectService extends IService<UserProject> {
    Map<String, Object> personList(Long current, Long size, Long projectId);
}
