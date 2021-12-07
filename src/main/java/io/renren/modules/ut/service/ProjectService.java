package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.ut.entity.Project;

import java.util.Map;

public interface ProjectService extends IService<Project> {
    Map<String, Object> getProjectByMap(Map<String,Object> queryMap);

}
