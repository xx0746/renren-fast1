package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 科研中心员工绩效考核评分表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 10:24:28
 */
@Mapper
public interface PerformanceDao extends BaseMapper<PerformanceEntity> {
	
}
