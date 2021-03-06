package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.entity.UserPerformance;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserPerformanceDao extends BaseMapper<UserPerformance> {

}
