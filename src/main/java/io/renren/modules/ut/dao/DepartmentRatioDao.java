package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.entity.DepartmentRatio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentRatioDao extends BaseMapper<DepartmentRatio> {
    List<Map> ratioList(@Param(("param")) Map param);
}
