package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.VO.UTWriteDayVO;
import io.renren.modules.ut.VO.UTWriteVO;
import io.renren.modules.ut.entity.UTWriteDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WriteDayDao extends BaseMapper<UTWriteDay> {
    List<UTWriteDayVO> writeDayList(
            @Param("userId") Long userId, @Param("start") long l, @Param("size") Long size, @Param("year") String year,
            @Param("month") String month, @Param("day") String day, @Param("projectId") Long projectId
    );
    Long writeDayListCount(
            @Param("userId") Long userId, @Param("year") String year,
            @Param("month") String month, @Param("day") String day, @Param("projectId") Long projectId);
    List<Map> writeProjectList(@Param("userId") Long userId, @Param("start") long l, @Param("size") Long size);

    Long projectCount(@Param("userId") Long userId);
}
