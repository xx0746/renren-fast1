package io.renren.modules.performance.dao;

import io.renren.modules.performance.entity.PerformanceDoctorEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 专家博士绩效考核
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-12-24 17:48:16
 */
@Mapper
public interface PerformanceDoctorDao extends BaseMapper<PerformanceDoctorEntity> {
	
}
