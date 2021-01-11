package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceFunctionEntity;

import java.util.Map;

/**
 * 职能部门员工绩效考核评分
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
public interface PerformanceFunctionService extends IService<PerformanceFunctionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

