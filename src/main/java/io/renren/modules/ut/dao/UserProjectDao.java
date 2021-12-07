package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.VO.UserProjectVO;
import io.renren.modules.ut.entity.UserProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserProjectDao extends BaseMapper<UserProject> {
    List<UserProjectVO> personList(@Param(("start")) long start, @Param(("size")) Long size, @Param("projectId") Long projectId);
}
