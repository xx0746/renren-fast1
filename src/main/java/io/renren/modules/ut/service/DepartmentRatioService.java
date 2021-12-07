package io.renren.modules.ut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.ut.entity.DepartmentRatio;

import java.util.Map;

public interface DepartmentRatioService extends IService<DepartmentRatio> {
    Map<String, Object> ratioList(Map<String,Object> queryMap);
}
