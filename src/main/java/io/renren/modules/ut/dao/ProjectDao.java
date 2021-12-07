package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.VO.UTWriteVO;
import io.renren.modules.ut.VO.UserUTVO;
import io.renren.modules.ut.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectDao extends BaseMapper<Project> {
}
