package io.renren.modules.performance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.performance.entity.PerformanceEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 科研中心员工绩效考核评分表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 10:24:28
 */
public interface PerformanceService extends IService<PerformanceEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageByIds(List<Long> useIds);
    PageUtils queryPageByIdsandStatus(List<Long> useIds,Integer status);

    void autorange();

}

