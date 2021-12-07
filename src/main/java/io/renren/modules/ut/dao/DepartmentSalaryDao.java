package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.entity.DepartmentSalary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentSalaryDao extends BaseMapper<DepartmentSalary> {
    List<Map> salaryList(@Param(("param")) Map param);
}
