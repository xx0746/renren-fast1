package io.renren.modules.ut.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ut.entity.UTWrite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApprovalDao extends BaseMapper<UTWrite> {
    List<Map> managerProjectApprovalList(@Param(("param")) Map param);
    List<Map> managerProjectDayApprovalList(@Param(("param")) Map param);
    Long projectApprovalCount(@Param(("param")) Map param);
    List<Map> managerApprovalWorkTimeList(@Param(("param")) Map param);
    List<Map> managerDayApprovalWorkTimeList(@Param(("param")) Map param);
    List<Map> leaderProjectApprovalList(@Param(("param")) Map param);
    List<Map> leaderApprovalWorkTimeList(@Param(("param")) Map param);

}
