package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceFunctionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 职能部门员工绩效考核评分
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
@Mapper
public interface PerformanceFunctionDao extends BaseMapper<PerformanceFunctionEntity> {
	
}
