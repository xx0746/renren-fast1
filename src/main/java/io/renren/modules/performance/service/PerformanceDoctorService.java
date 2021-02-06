package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceDoctorEntity;

import java.util.List;
import java.util.Map;

/**
 * 专家博士绩效考核
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
public interface PerformanceDoctorService extends IService<PerformanceDoctorEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageByStatus(List<Integer> status);
}

